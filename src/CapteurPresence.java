import java.util.Scanner;

public class CapteurPresence extends AbstractCapteur {

    Scanner input;
    String choix;

    public CapteurPresence (String centraleAddr) throws Exception{
        //15 est la valeur de base
        //4000 est le sleep time
        super(centraleAddr, 27, 100);
        input = new Scanner(System.in);
    }

    protected double genererValeur() {
        clearScreen();
        System.out.print("Y a quelqu'un ? (oui/non) : ");
        choix = input.nextLine();
        if (choix.equalsIgnoreCase("oui")) {
            return 1;
        } else {
            return 0;
        }
    }

    private static void clearScreen() {
        String ANSI_CLS = "\u001b[2J";
        String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}