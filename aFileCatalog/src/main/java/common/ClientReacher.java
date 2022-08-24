package common;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface ClientReacher extends Remote {
    void recvMsg(String msg) throws RemoteException;
}
