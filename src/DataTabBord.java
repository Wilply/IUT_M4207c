import java.util.ArrayList;

public class DataTabBord {
    //id du capteur
    private String id;
    //nom du capteur, pas forcement utile
    private String name;
    private ArrayList<String> capteurId;

    public DataTabBord(String id) {
        this.id = id;
        this.name = "Table de bord " + id;
        capteurId = new ArrayList<String>();
    }

    public DataTabBord(String id, String name) {
        this.id = id;
        this.name = name;
        capteurId = new ArrayList<String>();
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

    public void subscribeToCapteur(String id) {
        capteurId.add(id);
    }

    public void unsubscribeToCapteur(String id) {
        capteurId.remove(id);
    }

    public String[] getSubscribedCapteur() {
        return (String[])capteurId.toArray();
    }

    public boolean isSubscribedToCapteur(String id) {
        return capteurId.contains(id);
    }
}