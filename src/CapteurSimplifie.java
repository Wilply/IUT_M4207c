import java.rmi.registry.*;
import java.util.Random ;

public class CapteurSimplifie {
    //controle de la boucle principale du capteur
    private boolean start;
    //id du capteur
    private String id;
    //centrale
    private CentraleInterface centrale;
    //random generator
    private Random random;
    //borne min et max de generation des nombre
    private double max;
    private double min;
    //dernière valeur généré
    private double valeur;
    //nom du capteur
    private String name;

    public CapteurSimplifie(String centraleAddr) throws Exception{
        //on recupère la centrale;
        this.centrale = (CentraleInterface)LocateRegistry.getRegistry(centraleAddr).lookup("centrale");
        //on declare la centrale
        this.id = centrale.ajouterCapteur();
        System.out.println("Capteur initialize avec l'id : " + id);
        //on creer le random generator
        this.random = new Random();
        //on initialise a true pour que la boucle principale se lance
        this.start = true;
        //on definis les limite de generation
        this.max = 35;
        this.min = 10;
        //on donne la valeur initiale du capteur
        this.valeur = 20;
    }

    private void genererValeur() {
        //genere une valeur de type double
        // 5 est le "pas", la diffenrence max entre 2 valeur successive 
        double rmdValeur = 2 * this.random.nextDouble();
        int addOrMinus = this.random.nextInt(2);
        if (this.valeur - rmdValeur < this.min) {
            //si on va passer sous le minimum on ajoute la valeur
            this.valeur = this.valeur + rmdValeur;
        } else if (this.valeur + rmdValeur > this.max) {
            //si on va passer au dessus du max on soustrait la valeur
            this.valeur = this.valeur - rmdValeur;
        } else {
            //on reste dans les limites
            if (addOrMinus == 0) {
                //si == 0 on ajoute la valeur
                this.valeur = this.valeur + rmdValeur;
            } else {
                //sinon on soustrait la valeur
                this.valeur = this.valeur - rmdValeur;
            }
        }
    }

    public void stop() {
        this.start = false;
    }

    private void newName(String name) {
        if (this.name != name) {
            this.name = name;
            System.out.println("New capteur name is : "+this.name);
        }
    }

    public void start() throws Exception {
        while (this.start) {
            //on genere un nouvelle valeur
            genererValeur();
            //on envoie la valeur a la centrale
            this.centrale.getValeur(this.id, this.valeur);
            //pas encore implementé coté centrale PAS TOUCHER
            //this.newName(this.centrale.getName(this.id));
            //on attend 5s avant de recommencer
            Thread.sleep(5000);
        }
    }
}