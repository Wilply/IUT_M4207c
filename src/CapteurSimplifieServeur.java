public class CapteurSimplifieServeur {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Le capteur a besoin de au moins 1 argument qui est l'adresse de la centrale");
            System.exit(1);
        }
        try {
            CapteurSimplifie capteur = new CapteurSimplifie(args[0]);
            capteur.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}