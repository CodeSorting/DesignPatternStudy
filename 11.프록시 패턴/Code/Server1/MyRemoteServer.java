package Code.Server1;

import java.rmi.Naming;

public class MyRemoteServer {
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
