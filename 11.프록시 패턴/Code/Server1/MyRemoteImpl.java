package Code.Server1;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    private String message;

    public MyRemoteImpl() throws java.rmi.RemoteException {
        super();
        this.message = "Hello, World!";
    }

    @Override
    public String sayHello() throws java.rmi.RemoteException {
        return "Hello from MyRemoteImpl!";
    }

    @Override
    public String getMessage() throws java.rmi.RemoteException {
        return message;
    }

    @Override
    public void setMessage(String message) throws java.rmi.RemoteException {
        this.message = message;
    }
    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("rmi://localhost/MyRemoteService", service);
            System.out.println("MyRemoteService is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
