package client.view;

import common.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.stream.Stream;

public class View implements Runnable{
    private ServerReacher server;
    private boolean receiving = false;
    private final ClientReacher remoteObject;
    private LogInDetails userLID;

    private final String HELP_MESSAGE = "help";
    private final String EXIT_MESSAGE = "exit";
    private final String LOGIN_MESSAGE = "login";
    private final String LOGOUT_MESSAGE = "logout";
    private final String REGISTER_MESSAGE = "register";
    private final String UNREGISTER_MESSAGE = "unregister";
    private final String UPLOAD_MESSAGE = "upload";
    private final String DOWNLOAD_MESSAGE = "download";
    private final String LIST_MESSAGE = "list";
    private final String DELETE_MESSAGE = "delete";
    private final String UPDATE_MESSAGE = "update";
    private final String NOTIFY_MESSAGE = "notify";

    public View() throws RemoteException {
        remoteObject = new Output();
    }

    public void start() {
        if (receiving) {
            return;
        }
        receiving = true;
        new Thread(this).start();
    }

    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Host?");
        lookupServer(sc.next());
        System.out.println("receiving commands...\nNow ready for input, for help write: " + HELP_MESSAGE);
        String username;
        while(receiving){
            try{
                String input = sc.nextLine().toLowerCase();
                if(input.isEmpty())
                    continue;
                switch (input) {
                    case HELP_MESSAGE:
                        System.out.println("Possible commands: " + HELP_MESSAGE + ", " + EXIT_MESSAGE + ", " + LOGIN_MESSAGE + ", " + LOGOUT_MESSAGE + ", " + REGISTER_MESSAGE + ", " + UNREGISTER_MESSAGE + ", " + UPLOAD_MESSAGE + ", " + DOWNLOAD_MESSAGE + ", " + LIST_MESSAGE + ", " + DELETE_MESSAGE + ", " + UPDATE_MESSAGE + ", " + NOTIFY_MESSAGE + " ");
                        break;
                    case EXIT_MESSAGE:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    case LOGIN_MESSAGE:
                        System.out.println("Insert username and password.");
                        LogInDetails lid = new LogInDetails(sc.next(), sc.next());
                        username = server.logIn(remoteObject, lid);
                        userLID = lid;
                        System.out.println("Logged in as " + username);
                        break;
                    case LOGOUT_MESSAGE:
                        if(userLID == null){
                            System.out.println("Unable to log out.");
                        }else{
                            System.out.print("Logging out...");
                            server.logOut(userLID);
                            userLID = null;
                            System.out.println("done");
                        }
                        break;
                    case REGISTER_MESSAGE:
                        try{
                            System.out.println("Insert wanted username and password.");
                            LogInDetails lidR = new LogInDetails(sc.next(), sc.next());
                            username = server.register(lidR, remoteObject);
                            userLID = lidR;
                            System.out.println("Logged in as " + username);
                        }catch(UserError ue){
                            System.out.println("Username already taken");
                        }
                        break;
                    case UNREGISTER_MESSAGE:
                        if(userLID != null){
                            System.out.println("Are you sure you want to unregister?");
                            if(sc.next().equals("yes")){
                                server.unRegister(userLID);
                                userLID = null;
                            }
                            System.out.println("You are now  no longer registered");
                        }
                        else{
                            System.out.println("You have to log in first.");
                        }
                        break;
                    case UPLOAD_MESSAGE:
                        String currentDir = System.getProperty("user.dir");
                        System.out.println("Enter file to upload, you are in \"" + currentDir + "\" \nFiles available to upload are:");
                        printFilesInDir(currentDir);
                        File file = new File(sc.next());
                        server.fileUpload(file, userLID);
                        System.out.println("Do you want the file to be public?");
                        if(sc.next().equals("yes")){
                            server.togglePrivate(file.getName(), userLID);
                        }
                        System.out.println("File uploaded");

                        break;
                    case DOWNLOAD_MESSAGE:
                        System.out.println("Enter file to download");
                        FileDTO downloadedFile = server.fileDownload(sc.next(), userLID);
                        System.out.println(downloadedFile);
                        System.out.println("File \"" + downloadedFile.getName() + "\".");
                        break;
                    case LIST_MESSAGE:
                        server.listFiles(userLID);
                        break;
                    case DELETE_MESSAGE:
                        System.out.println("Enter file to delete");
                        server.deleteFile(sc.next(), userLID);
                        System.out.println("File deleted");
                        break;
                    case UPDATE_MESSAGE:
                        System.out.println("Enter file to update");
                        String update = sc.next();
                        File updateFile = new File(update);
                        server.deleteFile(update, userLID);
                        server.fileUpload(updateFile, userLID);
                        System.out.println("File updated");
                        break;
                    case NOTIFY_MESSAGE:
                        System.out.println("Enter file to enable notification for");
                        server.setNotification(true, sc.next(), userLID);
                        System.out.println("Notification enabled");
                        break;
                    default:
                        System.out.println("Wrong message syntax: " + input + ". Type help for help.");
                        break;
                }

            }catch (RemoteException e) {
                e.printStackTrace();
            } catch (UserError userError) {
                System.out.println(userError.getErrorMsg());
                if(userError.getException() != null)
                    userError.getException().printStackTrace();
            } catch (FileError fileError) {
                System.out.println(fileError.getErrorMsg());
                if(fileError.getException() != null)
                    fileError.getException().printStackTrace();
            }
        }
    }



    private class Output extends UnicastRemoteObject implements ClientReacher{
        public Output() throws RemoteException{

        }
        @Override
        public void recvMsg(String message) {
            System.out.println(message);
        }
    }

    private void lookupServer(String host) /*throws NotBoundException, MalformedURLException, RemoteException */{
        try {
            server = (ServerReacher) Naming.lookup("//" + host + "/" + ServerReacher.NAME_OF_SERVER);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printFilesInDir(String path) {
        try (Stream<Path> paths = Files.list(Paths.get(path))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(this::printTabbed);
        } catch (IOException e) {
            System.out.println("Failed to print working dir.");
        }
    }

    private void printTabbed(Path path) {
        System.out.println("\t" + path.getFileName());
    }
}


