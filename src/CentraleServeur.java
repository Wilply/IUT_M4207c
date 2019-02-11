import java.rmi.registry.*;
public class CentraleServeur {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : java CentraleServeur @ip_de_la_machine");
            System.exit(2);
        }
        String ip_machine = args[0];
        try {
            //sur quel adresse la machine ecoute
            System.setProperty("java.rmi.server.hostname",ip_machine);
            //on creer le registre
            Registry registre = LocateRegistry.createRegistry(1099);
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