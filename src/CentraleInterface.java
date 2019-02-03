import java.rmi.*;
import java.util.LinkedHashMap;

interface CentraleInterface extends Remote {
    void getValeur(String id, double valeur) throws RemoteException;
    //renvoie l'id
    String ajouterCapteur() throws RemoteException;
    String ajouterTableauBord() throws RemoteException;
    //a implementer plus tard
    //String ajouterTableauBord(String addr, String[] listCapteur) throws RemoteException;
    //LinkedHashMap<String, String> getCapteurList() throws RemoteException;
    //pas necessaire
    //LinkedHashMap<String, String> getTabBordList() throws RemoteException;
    //a implementer plus tard : subscribe to capteur; getCapteur list
    void setCapteurName(String id, String name) throws RemoteException;
    void setTabBordName(String id, String name) throws RemoteException;
}