package ra.config;

import static ra.config.Config.scanner;

public class Validate {
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
}
