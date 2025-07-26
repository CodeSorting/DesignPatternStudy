package Code.Server1;

import java.rmi.*;

public class MyRemoteClient {
    public static void main(String[] args) {
        try {
            MyRemote service = (MyRemote) Naming.lookup("rmi://localhost/MyRemoteService");
            String s = service.sayHello();
            System.out.println("Service Response: " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
