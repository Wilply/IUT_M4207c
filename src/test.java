import java.rmi.registry.*;
import java.util.*;
import java.util.Random ;
public class test {
    public static void main(String[] args) {
        final Random rdm = new Random();
        while (true) {
            System.out.println(rdm.nextInt(5));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}