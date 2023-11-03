package ra.config;

import java.io.*;
import java.util.Scanner;

import static ra.config.Color.*;

public class WriteReadFile<T> {
    public static final String PATH_CATALOG = "src/ra/data/catalog.dat";
    public static final String PATH_CART = "src/ra/data/cart.dat";
    public static final String PATH_ORDER = "src/ra/data/order.dat";
    public static final String PATH_ORDERSDETAIL = "src/ra/data/ordersDetail.dat";
    public static final String PATH_PRODUCT = "src/ra/data/product.dat";
    public static final String PATH_USER = "src/ra/data/user.dat";
    public static final String PATH_LOGIN = "src/ra/data/login.dat";
    public static final String PATH_USER_LOGIN = "src/ra/data/userLogin.dat";


    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println();
        } catch (IOException e) {
            System.out.println();
        }
    }

    public T readFile(String PATH_FILE) {
        File file = new File(PATH_FILE);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        T t = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null) {
                fis.close();
            }
            if (ois != null) {
                ois.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println();
        } catch (IOException e) {
            System.out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }
}
