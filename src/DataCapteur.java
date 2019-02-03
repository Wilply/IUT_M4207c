import java.util.ArrayList;

public class DataCapteur {
    //id du capteur
    private String id;
    //nom du capteur, pas forcement utile
    private String name;
    //le changer en linkedhashmap<string, double> avec la date et l'heure comme clé
    private ArrayList<Double> valeurs;

    public DataCapteur(String id) {
        this.id = id;
        this.name = "Capteur " + id;
        valeurs = new ArrayList<Double>();
    }

    public DataCapteur(String id, String name) {
        this.id = id;
        this.name = name;
        valeurs = new ArrayList<Double>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public void addValeur(double v) {
        valeurs.add(v);
    }

    public double getLastValeur() {
        return valeurs.get(valeurs.size() - 1);
    }
}