package ra.config;

import java.util.Scanner;
import java.util.regex.Pattern;

import static ra.config.Color.*;

public class Validate {
    public static Scanner scanner() {
        return new Scanner(System.in);
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
        String emailPattern = "^[a-zA-Z0-9]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailPattern);

        while (true) {
            email = Validate.scanner().nextLine();
            if (pattern.matcher(email).matches()) {
                break;
            } else {
                System.out.println(RED + "Email không đúng định dạng, mời nhập lại" + RESET);
            }
        }

        return email;
    }

    public static String validatePassword() {
        String password;
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{7,}$");

        while (true) {
            password = Validate.scanner().nextLine();
            if (pattern.matcher(password).matches() && password.length() >= 4) {
                break;
            } else {
                System.out.println(RED + "Mật khẩu phải có ít nhất 7 kí tự cả số và chữ" + RESET);
            }
        }
        return password;
    }

    public static String validatePhone() {
        String phoneNumber;
        Scanner scanner = Validate.scanner();
        while (true) {
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches("0[0-9]{9}") || phoneNumber.matches("\\+840[0-9]{9}")) {
                break;
            } else {
                System.out.println(RED + "Số điện thoại không đúng định dạng, mời nhập lại" + RESET);
            }
        }
        return phoneNumber;
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
