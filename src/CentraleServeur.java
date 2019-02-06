import java.rmi.registry.*;
public class CentraleServeur {
    public static void main(String[] args) {
        try {
            //on creer le registre
            Registry registre = LocateRegistry.createRegistry(1099);
            //System.setProperty("java.rmi.server.hostname","10.2.13.203");
            System.out.println("Registre cree");
            //on creer la centrale
            CentraleImpl centrale = new CentraleImpl("centrale");
            System.out.println("Centrale creer");
            //on declare la centrale dans le registre
            registre.rebind(centrale.getAddr(), centrale);
            System.out.println("bind ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}