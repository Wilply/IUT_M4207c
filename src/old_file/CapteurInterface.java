import java.rmi.*;

interface CapteurInterface extends Remote {
    double envoyerValeur() throws RemoteException;
    //String envoyerValeur() throws RemoteException;
}