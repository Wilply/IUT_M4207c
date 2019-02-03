public class CentraleServeur {
    public static void main(String[] args) {
        try {
            CentraleImpl centrale = new CentraleImpl();
            while (true) {
                centrale.afficherValue();
                Thread.sleep(2500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}