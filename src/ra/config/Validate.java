package ra.config;

import static ra.config.Config.scanner;
import static ra.config.Color.*;

public class Validate {
    public static int validateInt() {
        int n;
        System.out.println("Mời nhập: ");
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
}
