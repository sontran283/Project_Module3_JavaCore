package ra.config;

import java.util.Scanner;

import static ra.config.Color.*;

public class Validate {
    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static int validateInt() {
        int n;
        System.out.print("Mời nhập: ");
        while (true) {
            try {
                n = Integer.parseInt(scanner().nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Sai định dạng, mời nhập lại" + RESET);
            }
        }
        return n;
    }

    public static String validateString() {
        String s;
        while (true) {
            s = Config.scanner().nextLine();
            if (s.isEmpty()) {
                System.out.println(RED + "Không được để trống, mời nhập lại" + RESET);
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
                System.out.println(RED + "Email không đúng định dạng, mời nhập lại" + RESET);
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
                System.out.println(RED + "Số điện thoại không đúng định dạng, mời nhập lại" + RESET);
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
                System.out.println(RED + "Số tiền không đúng định dạng, mời nhập lại" + RESET);
            }
        }
        return currency;
    }
}
