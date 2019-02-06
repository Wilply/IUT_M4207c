import java.util.Random ;

public class CapteurTemperature extends AbstractCapteur {

    private Random random;
    private double max;
    private double min;

    public CapteurTemperature(String centraleAddr) throws Exception{
        //15 est la valeur de base
        //4000 est le sleep time
        super(centraleAddr, 20, 4000);
        //generateur aleatoire
        this.random = new Random();
        //valeur min et max
        this.min = 10;
        this.max = 35;
    }

    protected double genererValeur() {
        //genere une valeur de type double
        // 5 est le "pas", la diffenrence max entre 2 valeur successive 
        double returnedValue;
        double rmdValeur = 2 * this.random.nextDouble();
        int addOrMinus = this.random.nextInt(2);
        if (this.valeur - rmdValeur < this.min) {
            //si on va passer sous le minimum on ajoute la valeur
            returnedValue = this.valeur + rmdValeur;
        } else if (this.valeur + rmdValeur > this.max) {
            //si on va passer au dessus du max on soustrait la valeur
            returnedValue = this.valeur - rmdValeur;
        } else {
            //on reste dans les limites
            if (addOrMinus == 0) {
                //si == 0 on ajoute la valeur
                returnedValue = this.valeur + rmdValeur;
            } else {
                //sinon on soustrait la valeur
                returnedValue = this.valeur - rmdValeur;
            }
        }
        return returnedValue;
    }
}