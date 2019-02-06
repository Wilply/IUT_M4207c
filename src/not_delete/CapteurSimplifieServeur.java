public class CapteurSimplifieServeur {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing arguments");
            System.out.println("Usage : java CapteurSimplifieServeur @centrale name @ip");
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