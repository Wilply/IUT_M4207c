import java.rmi.registry.*;

abstract class AbstractCapteur {
    //id du capteur
    protected final String id;
    //centrale
    protected final CentraleInterface centrale;
    //dernière valeur généré
    protected double valeur;
    //nom du capteur
    protected String name;
    //temps de pause entre 2 envoie
    protected int sleepTime;

    protected AbstractCapteur(String centraleAddr) throws Exception{
        //on recupère la centrale;
        this.centrale = (CentraleInterface)LocateRegistry.getRegistry(centraleAddr, 1099).lookup("centrale");
        //on declare la centrale
        this.id = centrale.ajouterCapteur();
        System.out.println("Capteur initialize avec l'id : " + id);
        //on donne la valeur initiale du capteur
        this.valeur = 20;
        //on definie le temps de pause entre 2 envoie
        this.sleepTime = 5000;
    }

    protected AbstractCapteur(String centraleAddr, double startValue, int sleepTime) throws Exception{
        //on recupère la centrale;
        this.centrale = (CentraleInterface)LocateRegistry.getRegistry(centraleAddr, 1099).lookup("centrale");
        //on declare la centrale
        this.id = centrale.ajouterCapteur();
        System.out.println("Capteur initialize avec l'id : " + id);
        //on donne la valeur initiale du capteur
        this.valeur = startValue;
        //on definie le temps de pause entre 2 envoie
        this.sleepTime = sleepTime;
    }

    abstract double genererValeur();

    public final void setName(String name) {
        try {
            if(this.name == null) {
                System.out.println("Setup name");
                this.name = this.centrale.getCapteurName(this.id);
            } 
            if (!this.name.equals(name)) {
                this.name = name;
                System.out.println("New capteur name is : \"" + this.name + "\"");
                if (!this.name.equals(this.centrale.getCapteurName(this.id))) {
                    System.out.println("Set new capteur name in centrale");
                    this.centrale.setCapteurName(this.id, this.name);
                }
            }
        } catch (java.rmi.ConnectException e) {
            System.out.println("Impossible de joindre la centrale");
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void start() throws Exception {
        while (true) {
            try {
                //on genere un nouvelle valeur
                this.valeur = genererValeur();
                //on envoie la valeur a la centrale
                this.centrale.getValeur(this.id, this.valeur);
                //on recupère le nom sur la centrale et on verifie que il n'a pas changé
                this.setName(this.centrale.getCapteurName(this.id));
                //on attend 5s avant de recommencer
            } catch (java.rmi.ConnectException e) {
                System.out.println("Impossible de joindre la centrale");
                System.exit(2);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(this.sleepTime);
        }
    }
}