
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class CentraleImpl extends UnicastRemoteObject implements CentraleInterface {
    //attributs
    private String adress;

    //constructeurs
    public CentraleImpl(String adress) throws RemoteException {
        this.adress = adress;
    }

    public CentraleImpl() throws RemoteException {
        this.adress = "//localhost:1090/centrale";
    }

    //methodes local
    public String getAdress() {
        return this.adress;
    }

    private CapteurInterface getCapteur(String addr) throws Exception {
        return (CapteurInterface)Naming.lookup(addr);
    }

    public void afficherValue() {
        try {
            System.out.println(getCapteur("//localhost:1099/capteur").envoyerValeur());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //methode public
    /*public void askCapteur(String CapteurAdress) {}
    public double getValeur(String CapteurAdress) {};
    public String getValeur(String CapteurAdress) {};
    public boolean ajouterCapteur(String name, adress, type) {};
    public void ajouterTableauBord(String name, adress) {};*/
}