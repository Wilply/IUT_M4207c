import java.rmi.*;

public interface CapteurInterface extends Remote {
    double envoyerValeur() throws RemoteException;
    void setName(String name) throws RemoteException;
    String getId() throws RemoteException;
}