import java.rmi.*;
import java.rmi.registry.*;

public class CapteurServeur {
    public static void main(String[] args) {
        try {
            String centraleAddr = "127.0.0.1";
            String name = "test capteur 1";
            //on recupere le registre sur la machine ou ya la centrale
            Registry registre = LocateRegistry.getRegistry(centraleAddr, 1099);
            //on recupère l'interface centrale
            CentraleInterface centrale = (CentraleInterface)registre.lookup("centrale");
            //on declare le capteur a la centrale et on recupere l'id genéré
            String id = centrale.ajouterCapteur();
            //on change le nom du capteur
            centrale.setCapteurName(id, name);
            //on creer le capteur avec l'id
            CapteurImpl capteur = new CapteurImpl(id, centraleAddr);
            //on declare le capteur dans le registre
            Naming.rebind(id, capteur);
            System.out.println("Le serveur est pret");
            //on lance le capteur
            System.out.println("Demarage du capteur");
            capteur.start();

        } catch (Exception e) {
            if (e.getClass() == ConnectException.class) {
                System.out.println("Impossible de se connecter a la centreale ou la connection a été interrompue");
            } else {
                e.printStackTrace();
            }
        }
    }
}