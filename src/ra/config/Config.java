package ra.config;

import java.io.*;
import java.util.Scanner;

public class Config {
    public static final String PATH_CATALOG = "src/ra/data/catalog.dat";
    public static final String PATH_ORDER = "src/ra/data/order.dat";
    public static final String PATH_ORDERSDETAIL = "src/ra/data/ordersDetail.dat";
    public static final String PATH_PRODUCT = "src/ra/data/product.dat";
    public static final String PATH_USER = "src/ra/data/user.dat";
    public static final String PATH_LOGIN = "src/ra/data/login.dat";

    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static int validateInt() {
        int n;
        System.out.println("Mời nhập: ");
        while (true) {
            try {
                n = Integer.parseInt(scanner().nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("___ Sai định dạng, mời nhập lại ___");
            }
        }
        return n;
    }

    public static String validateString() {
        String s;
        while (true) {
            s = Config.scanner().nextLine();
            if (s.isEmpty()) {
                System.out.println("___ Không được để trống, mời nhập lại ___");
            } else {
                break;
            }
        }
        return s;
    }

    public static String validateEmail() {
        String email;
        while (true) {
            email = Config.scanner().nextLine();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println("___ Email không đúng định dạng, mời nhập lại ___");
            }
        }
        return email;
    }

    public static String validatePhone() {
        String phoneNumber;
        while (true) {
            phoneNumber = Config.scanner().nextLine();
            if (phoneNumber.matches("(0|\\+84)\\d{9}")) {
                break;
            } else {
                System.out.println("___ Số điện thoại không đúng định dạng, mời nhập lại ___");
            }
        }
        return phoneNumber;
    }

    public static String validateCurrency() {
        String currency;
        while (true) {
            currency = Config.scanner().nextLine();
            if (currency.matches("^[1-9]\\d{0,2}(,\\d{3})*?(\\.\\d{2})?$")) {
                break;
            } else {
                System.out.println("___ Số tiền không đúng định dạng, mời nhập lại ___");
            }
        }
        return currency;
    }
}
