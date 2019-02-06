import java.rmi.*;
import java.rmi.registry.*;

public class TabBordServeur {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing arguments");
            System.out.println("Usage : java TabBordServeur @centrale name @ip");
            System.exit(1);
        }
        if (args.length >= 3) {
            System.setProperty("java.rmi.server.hostname",args[2]);
        }
        String centraleAddr = args[0];
        try {
            //on creer le tab de bord
            TabBordImpl tab = new TabBordImpl(centraleAddr);
            if (args.length >= 2) {
                tab.setName(args[1]);
            }
            //on le bind
            LocateRegistry.getRegistry(centraleAddr, 1099).rebind(tab.getId(), tab);
            System.out.println("Le serveur est pret");
            //on lance le capteur
        } catch (Exception e) {
            if (e.getClass() == ConnectException.class) {
                System.out.println("Impossible de se connecter a la centreale ou la connection a été interrompue");
            } else {
                e.printStackTrace();
            }
        }
    }
}