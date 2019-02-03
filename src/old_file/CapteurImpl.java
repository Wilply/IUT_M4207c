
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random ;

public class CapteurImpl extends UnicastRemoteObject implements CapteurInterface {
    //attributs
    private String adress;
    private double valeur;
    private boolean stop;

    //constructeurs
    public CapteurImpl(String adress) throws RemoteException {
        this.adress = adress;
    }

    public CapteurImpl() throws RemoteException {
        this.adress = "//localhost:1090/capteur";
    }

    //methodes local
    public String envoyerAdress() {
        return this.adress;
    }

    public double envoyerValeur() {
        return this.valeur;
    };

    private void genererValeur() {
        Random rdm = new Random();
        this.valeur = rdm.nextDouble();
    }

    public void start() throws InterruptedException {
        this.stop = true;
        while (this.stop) {
            genererValeur();
            Thread.sleep(2000);
        }
    }

    public void stop() {
        this.stop = false;
    }
}