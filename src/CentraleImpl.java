import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

/*void getValeur(String id, double valeur) throws RemoteException;
String ajouterCapteur(String addr) throws RemoteException;
String ajouterTableauBord(String addr) throws RemoteException;
LinkedHashMap<String, String> getCapteurList() throws RemoteException;
void setCapteurName(String id, String name) throws RemoteException;
void setTabBordName(String id, String name) throws RemoteException;*/

public class CentraleImpl extends UnicastRemoteObject implements CentraleInterface {

    private DataStorage data;
    private String addr;

    public CentraleImpl(String addr) throws RemoteException {
        data = new DataStorage();
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public void getValeur(String id, double valeur) throws RemoteException {
        data.getCapteur(id).addValeur(valeur);
        System.out.println("Nouvelle valeur recu : "+ valeur + " depuis le capteur " + data.getCapteur(id).getName());
        for (String tabBordId : data.getTabBordList().keySet()) {
            if (data.getTabBord(tabBordId).isSubscribedToCapteur(id)) {
                System.out.println("Envoyer la valeur a : " + data.getTabBord(id).getName());
            }
        }
    }

    public void setCapteurName(String id, String name) throws RemoteException {
        data.getCapteur(id).setName(name);
        System.out.println("Set name : " + name + " to capteur "+ id);
    }

    public void setTabBordName(String id, String name) throws RemoteException {
        data.getTabBord(id).setName(name);
        System.out.println("Set name : " + name + " to tab de bord "+ id);
    }

    public String ajouterCapteur() throws RemoteException {
        String id = data.addCapteur();
        System.out.println("Nouveau capteur ajouté dans storage avec l'id " + id);
        return id;
    }

    public String ajouterTableauBord() throws RemoteException {
        Scanner input = new Scanner(System.in);
        String id = data.addTabBord();
        ArrayList<String> capteur = new ArrayList<String>();
        ArrayList<String> keylist = new ArrayList<>(data.getCapteurList().keySet());
        String choix;
        while(true) {
            System.out.println("A quel(s) capteur voulez vous vous abonner ?");
            System.out.println();
            for (String key : keylist) {
                System.out.printf(" %-2d - %-15s %-1s\n", keylist.indexOf(key), data.getCapteurList().get(key) , key);
            }
            System.out.print("Entrer le numero du capteur ou rien pour valider : ");
            choix = input.nextLine();
            if (choix.isEmpty()) {
                break;
            }
            if (!isInteger(choix)) {
                System.out.println("****************************************");
                System.out.println("Entrer un nombre svp");
            } else if ((Integer.parseInt(choix) > 0)||(Integer.parseInt(choix) < (keylist.size() - 1))) {
                System.out.println("****************************************");
                System.out.print("Choix invalide");
            } else {
                capteur.add(keylist.get(Integer.parseInt(choix)));
                keylist.remove(Integer.parseInt(choix));
            }
        }
        for (String capteurid : capteur) {
            data.getTabBord(id).subscribeToCapteur(capteurid);
        }
        return id;
    }

    /* TOOLS */

    private static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
