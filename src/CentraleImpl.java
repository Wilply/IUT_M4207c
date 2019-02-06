import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        //System.out.println("Nouvelle valeur recu : "+ valeur + " depuis le capteur " + data.getCapteur(id).getName());
        for (String tabBordId : data.getTabBordList().keySet()) {
            if (data.getTabBord(tabBordId).isSubscribedToCapteur(id)) {
                try {
                    TabBordInterface tab = (TabBordInterface)LocateRegistry.getRegistry(1099).lookup(tabBordId);
                    tab.getValeur(id, valeur);
                } catch (java.rmi.ConnectException e) {
                    System.out.println("Impossible de joindre le tableau de bord");
                    System.out.println("Delete tableau de bord : " + data.getTabBord(tabBordId).getName());
                    data.deleteTabBord(tabBordId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        }
    }

    public void setCapteurName(String id, String name) throws RemoteException {
        data.getCapteur(id).setName(name);
        System.out.println("Set name : \"" + name + "\" to capteur " + id);
    }

    public String getCapteurName(String id) {
        return data.getCapteur(id).getName();
    }

    public void setTabBordName(String id, String name) throws RemoteException {
        data.getTabBord(id).setName(name);
        System.out.println("Set name : " + name + " to tab de bord "+ id);
    }

    public String getTabBordName(String id) {
        return data.getTabBord(id).getName();
    }

    public String ajouterCapteur() throws RemoteException {
        String id = data.addCapteur();
        System.out.println("Nouveau capteur ajouté dans storage avec l'id " + id);
        return id;
    }

    public String ajouterTableauBord(String[] capteurList) throws RemoteException {
        String id = data.addTabBord();
        for (int i = 0; i < capteurList.length; i++) {
            if (data.getCapteur(capteurList[i]) != null) {
                data.getTabBord(id).subscribeToCapteur(capteurList[i]);   
            }
        }
        return id;
    }

    public String[] getSubscribedCapteur(String id) {
        return data.getTabBord(id).getSubscribedCapteur();
    }

    public LinkedHashMap<String,String> getCapteurList() {
        LinkedHashMap<String,String> copy = new LinkedHashMap<String,String>();
        for (String key : data.getCapteurList().keySet()) {
            copy.put(key, data.getCapteur(key).getName());
        }
        return copy;
    }
}
