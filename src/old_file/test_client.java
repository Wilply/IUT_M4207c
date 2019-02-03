import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class test_client {
    public static void main(String[] args) {

        String addr = "//localhost:1099/capteur";

        try {
            System.out.println(Naming.lookup(addr).getClass());

            CapteurInterface capteur = (CapteurInterface)Naming.lookup(addr);

            System.out.println(capteur.envoyerValeur());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}