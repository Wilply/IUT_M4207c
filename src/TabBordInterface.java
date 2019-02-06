import java.rmi.*;

interface TabBordInterface extends Remote {
    //get valeur du capteur id.
    void getValeur(String id, double valeur) throws RemoteException;
}