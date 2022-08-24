package server.startup;

import server.controller.Controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Main {
    public static void main(String[] args) {
        try {
            new Main().startRegistry();
            Naming.rebind(Controller.NAME_OF_SERVER, new Controller());
            System.out.println("Server is a go");
        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
            System.out.println("Unable to start server");
        }
    }

    private void startRegistry() throws RemoteException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException NoRegistryRunner) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }

    }
}
