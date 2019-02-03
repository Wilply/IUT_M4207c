import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class CapteurServeur {
    public static void main(String[] args) {
        try {
            CapteurImpl capteur = new CapteurImpl();
            System.out.println("Capteur Cree");

            LocateRegistry.createRegistry(1099);
            /*
            Object reg = LocateRegistry.getRegistry();    
            // reg == null
            if (reg == null) {
                System.out.println("Registry Cree");
                LocateRegistry.createRegistry(1099);
            }*/

            Naming.rebind("//localhost:1099/capteur", capteur);
            System.out.println("Le serveur est pret");

            System.out.println("Demmarage du capteur");
            capteur.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}