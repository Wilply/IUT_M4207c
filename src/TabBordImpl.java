import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.text.*;

public class TabBordImpl extends UnicastRemoteObject implements TabBordInterface {

    CentraleInterface centrale;
    String id;
    String name;
    ArrayList<String> capteurs;
    LinkedHashMap<String, Double> valeurs;

    public TabBordImpl(String centralAddr) throws Exception {
        this.centrale = (CentraleInterface)LocateRegistry.getRegistry(centralAddr, 1099).lookup("centrale");
        //on choisie les capteur auxquels on s'abonne
        String[] capteurs = interactiveSubscribe(centrale);
        this.id = centrale.ajouterTableauBord(capteurs);
        this.capteurs = new ArrayList<String>(Arrays.asList(this.centrale.getSubscribedCapteur(this.id)));
        this.valeurs = new LinkedHashMap<String, Double>();
    }

    public void getValeur(String id, double valeur) {
        if (capteurs.contains(id)) {
            valeurs.put(id, valeur);
            System.out.println("new valeur");
        }
        try {
            this.afficher();
        } catch (java.rmi.ConnectException e) {
            System.out.println("Impossible de joindre la centrale");
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return this.id;
    }

    private void afficher() throws Exception {
        NumberFormat format = new DecimalFormat("#0.00");
        String value;
        clearScreen();
        for (String key : valeurs.keySet()) {
            value = format.format(valeurs.get(key));
            System.out.println("temperature du capteur \"" + this.centrale.getCapteurName(key) + "\" : " + value);
        }
    }

    public void setName(String name) {
        this.name = name;
        try {
            this.centrale.setTabBordName(this.id, this.name);
        } catch (java.rmi.ConnectException e) {
            System.out.println("Impossible de joindre la centrale");
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* TOOLS */

    private static String[] interactiveSubscribe(CentraleInterface centrale) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> capteur = new ArrayList<String>();
        LinkedHashMap<String,String> capteurList;
        ArrayList<String> keylist;
        String choix;
        boolean premierChoix = true;
        try {
            capteurList = centrale.getCapteurList();
            keylist = new ArrayList<>(capteurList.keySet());
            while(true) {
                if (keylist.size() == 0) {
                    break;
                }
                if (premierChoix) {
                    System.out.println("A quel(s) capteur voulez vous vous abonner ?");
                } else {
                    System.out.println("A quel AUTRE capteur voulez vous vous abonner ?");
                }
                System.out.println();
                for (String key : keylist) {
                    System.out.printf(" %-2d - %-15s \" %-1s\n", keylist.indexOf(key), "\" " + capteurList.get(key) + " \"" , key);
                }
                System.out.print("Entrer le numero du capteur ou rien pour valider : ");
                choix = input.nextLine();
                if (choix.isEmpty()) {
                    break;
                }
                if (!isInteger(choix)) {
                    System.out.println("****************************************");
                    System.out.println("Entrer un nombre svp");
                } else if ((Integer.parseInt(choix) < 0)||(Integer.parseInt(choix) > (keylist.size() - 1))) {
                    System.out.println("****************************************");
                    System.out.println("Choix invalide");
                    premierChoix = false;
                } else {
                    capteur.add(keylist.get(Integer.parseInt(choix)));
                    keylist.remove(Integer.parseInt(choix));
                    premierChoix = false;
                }
                clearScreen();
            }
        } catch (java.rmi.ConnectException e) {
            System.out.println("Impossible de joindre la centrale");
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return capteur.toArray(new String[capteur.size()]);
    }

    private static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // si ya pas d'erreur on revoir true
        return true;
    }

    private static void clearScreen() {
        String ANSI_CLS = "\u001b[2J";
        String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}