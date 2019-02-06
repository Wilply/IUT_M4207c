import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random ;

public class CapteurImpl extends UnicastRemoteObject implements CapteurInterface {
    //attributs
    private boolean stop;
    private String id;
    private Registry registre;
    private double valeur;
    private String name;

    //constructeurs

    public CapteurImpl(String id, String centraleAddr) throws RemoteException {
        this.id = id;
        //on recupere le registre
        this.registre = LocateRegistry.getRegistry(centraleAddr);
    }

    //methodes local

    public String getId() {
        return this.id;
    }

    public double envoyerValeur() {
        return this.valeur;
    };

    public void setName(String name) {
        this.name = name;
    }

    private void genererValeur() {
        Random rdm = new Random();
        this.valeur = rdm.nextDouble();
    }

    public void start() throws InterruptedException {
        this.stop = true;
        while (this.stop) {
            genererValeur();
            try {
                //on recupere la centrale
                CentraleInterface centrale = (CentraleInterface)registre.lookup("centrale");
                centrale.getValeur(this.id, this.valeur);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(2000);
        }
    }

    public void stop() {
        this.stop = false;
    }
}