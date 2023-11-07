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
            s = Validate.scanner().nextLine();
            if (s.trim().isEmpty()) {
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
            email = Validate.scanner().nextLine();
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
            phoneNumber = Validate.scanner().nextLine();
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
            currency = Validate.scanner().nextLine();
            if (currency.matches("^[1-9]\\d{0,2}(,\\d{3})*?(\\.\\d{2})?$")) {
                break;
            } else {
                System.out.println(RED + "Số tiền không đúng định dạng, mời nhập lại" + RESET);
            }
        }
        return currency;
    }

    public static int validatePositiveDouble() {
        Scanner scanner = new Scanner(System.in);
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= 0) {
                    break;
                } else {
                    System.out.println(RED + "Giá trị không hợp lệ, mời nhập lại" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Giá trị không hợp lệ, mời nhập lại" + RESET);
            }
        }
        return value;
    }

    public static int validatePositiveInt() {
        Scanner scanner = new Scanner(System.in);
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= 0) {
                    break;
                } else {
                    System.out.println(RED + "Giá trị không hợp lệ, mời nhập lại" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Giá trị không hợp lệ, mời nhập lại" + RESET);
            }
        }
        return value;
    }
}
