import java.rmi.registry.*;
import java.util.*;
public class test {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("192.168.1.34", 1099);
            String[] ls = reg.list();
            for (int i = 0; i < ls.length; i++) {
                System.out.println(ls[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}