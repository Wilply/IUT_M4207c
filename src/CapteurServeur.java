import java.util.Scanner;

public class CapteurServeur {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing arguments");
            System.out.println("Usage : java CapteurSimplifieServeur @centrale name");
            System.exit(1);
        }
        try {
            Scanner input = new Scanner(System.in);
            boolean isValide = true;
            String choix;
            while (true) {
                System.out.println("**********************************************");
                if (!isValide) {
                    System.out.println("Choix invalide, reassayer");
                }
                System.out.println("Quel type de capteur voulez-vous creer ?");
                System.out.println();
                System.out.printf(" %-2d - %-15s\n", 1, "Capteur de Temperature");
                System.out.printf(" %-2d - %-15s\n", 2, "Capteur de Luminosité");
                System.out.printf(" %-2d - %-15s\n", 3, "Capteur de Presence");
                System.out.println();
                System.out.print("Entrez le numero du type de capteur : ");
                choix = input.nextLine();
                System.out.println();
                if (choix.isEmpty()) {
                    System.exit(2);
                    break;
                } else if (isInteger(choix)) {
                    //si le choix est compris entre 1 et 2 IL FAUDRA LE MODIFIER
                    if ((Integer.parseInt(choix) >= 1) && (Integer.parseInt(choix) <= 3)) {
                        break;
                    }
                } else {
                    isValide = false;
                }
            }
            AbstractCapteur capteur;
            switch (Integer.parseInt(choix)) {
                case 1:
                    capteur = new CapteurTemperature(args[0]);
                    System.out.println("Capteur de Temperature instancié");
                    if (args.length >= 2) {
                        capteur.setName(args[1]);   
                    }
                    capteur.start();
                    break;
                case 2:
                    capteur = new CapteurLuminosite(args[0]);
                    System.out.println("Capteur de Luminosité instancié");
                    if (args.length >= 2) {
                        capteur.setName(args[1]);   
                    }
                    capteur.start();
                    break;
                case 3:
                    capteur = new CapteurPresence(args[0]);
                    System.out.println("Capteur de Presence instancié");
                    if (args.length >= 2) {
                        capteur.setName(args[1]);   
                    }
                    capteur.start();
                    break;
                default:
                    System.exit(2);
                    break;
            }
        } catch (java.rmi.ConnectException e) {
            System.out.println("Impossible de joindre la centrale");
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* TOOLS */

    private static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // si on a pas return false on return true
        return true;
    }

    private static void clearScreen() {
        String ANSI_CLS = "\u001b[2J";
        String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}