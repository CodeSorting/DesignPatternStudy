package Code.Server1;

import java.rmi.Remote;

public interface MyRemote extends Remote {
    String sayHello() throws java.rmi.RemoteException;
    String getMessage() throws java.rmi.RemoteException;
    void setMessage(String message) throws java.rmi.RemoteException;
}
