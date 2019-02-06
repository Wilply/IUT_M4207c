import java.util.LinkedHashMap;
import java.util.UUID;

public class DataStorage {

    //String == id
    private LinkedHashMap<String, DataCapteur> listCapteur;
    private LinkedHashMap<String, DataTabBord> listTabBord;

    public DataStorage() {
        listCapteur = new LinkedHashMap<String, DataCapteur>();
        listTabBord = new LinkedHashMap<String, DataTabBord>();
    }

    //return the generated id
    public String addCapteur() {
        String id = newCapteurId();
        DataCapteur capteur = new DataCapteur(id);
        listCapteur.put(id, capteur);
        return id;
    }

    public String addTabBord() {
        String id = newTabBordId();
        DataTabBord tabBord = new DataTabBord(id);
        listTabBord.put(id, tabBord);
        return id;
    }

    public void deleteTabBord(String id) {
        listTabBord.remove(id);
    }

    public DataCapteur getCapteur(String id) {
        return listCapteur.get(id);
    }

    public DataTabBord getTabBord(String id) {
        return listTabBord.get(id);
    }

    public LinkedHashMap<String, String> getCapteurList() {
        LinkedHashMap<String, String>  lightCapteurList = new LinkedHashMap<String, String>();
        for (String key : listCapteur.keySet()) {
            lightCapteurList.put(key, listCapteur.get(key).getName());
        }
        return lightCapteurList;
    }

    public LinkedHashMap<String, String> getTabBordList() {
        LinkedHashMap<String, String>  lightTabBordList = new LinkedHashMap<String, String>();
        for (String key : listTabBord.keySet()) {
            lightTabBordList.put(key, listTabBord.get(key).getName());
        }
        return lightTabBordList;
    }

    public String newCapteurId() {
        while (true) {
            String id = UUID.randomUUID().toString();
            if (!listCapteur.containsKey(id)) {
                return id;
            }
        }
    }

    public String newTabBordId() {
        while (true) {
            String id = UUID.randomUUID().toString();
            if (!listTabBord.containsKey(id)) {
                return id;
            }
        }
    }
}