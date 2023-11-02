package ra.view.display;

import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.constant.RoleName;
import ra.model.Users;
import ra.service.IUserService;
import ra.service.impl.UserServiceIMPL;
import ra.view.admin.AdminManager;
import ra.view.user.UserManager;

import static ra.config.Color.*;

public class Home {
    IUserService userService = new UserServiceIMPL();
    public static Users userLogin;

    public static WriteReadFile<Users> config = new WriteReadFile<>();

    public void menuHome() {
        do {
            System.out.println("Danh sách người dùng: ");
            for (Users users : userService.findAll()) {
                System.out.println(users);
            }
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                    --->> HELLO, WELCOME BACK <<---                   |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                            1. Đăng nhập                              |");
            System.out.println("|                            2. Đăng ký                                |");
            System.out.println("|                            0. Thoát                                  |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validateInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void login() {
        System.out.println(BLUE + ".======================================================================.");
        System.out.println("|                        --->> FORM LOGIN <<---                        |");
        System.out.println(".======================================================================." + RESET);

        System.out.print("Nhập tên tài khoản: ");
        String name = Validate.validateString();

        System.out.print("Nhập mật khẩu: ");
        String pass = Validate.validateString();

        Users users = userService.checkLogin(name, pass);
        if (users == null) {
            System.out.println(RED + "Sai tên tài khoản hoặc mật khẩu, mời nhập lại" + RESET);
        } else {
            checkRoleLogin(users);
        }
    }

    public void checkRoleLogin(Users users) {
        if (users.getRole().equals(RoleName.ADMIN)) {
            WriteReadFile<Users> config = new WriteReadFile<>();
            config.writeFile(WriteReadFile.PATH_USER_LOGIN, users);

            // chuyen den admin
            new AdminManager().menuAdmin();

        } else {
            if (users.isStatus()) {
                WriteReadFile<Users> config = new WriteReadFile<>();
                // ghi doi tuong users dang dang nhap vao file
                config.writeFile(WriteReadFile.PATH_USER_LOGIN, users);

                // chuyen den user
                new UserManager().menuUser();

            } else {
                System.out.println(RED + "Tài khoản của bạn đã bị khoá, vui lòng liên hệ quản trị viên" + RESET);
            }
        }
    }

    public void register() {
        System.out.println(BLUE + ".======================================================================.");
        System.out.println("|                       --->> FORM REGISTER <<---                      |");
        System.out.println(".======================================================================." + RESET);
        Users users = new Users();
        users.setId(userService.getNewId());
        System.out.println("ID: " + users.getId());

        System.out.print("Nhập họ tên (ví dụ_tran van a):  ");
        String fullName;
        while (true) {
            fullName = Validate.validateString();
            if (!fullName.matches("[a-zA-Z\\p{L}\\s]+")) {
                System.out.println(RED + "Họ tên không hợp lệ, mời nhập lại" + RESET);
            } else {
                break;
            }
        }
        users.setName(fullName);

        System.out.print("Nhập tên tài khoản: ");
        while (true) {
            String username = Validate.validateString();
            if (userService.existUsername(username)) {
                System.out.println(RED + "Tên đăng nhập đã tồn tại, mời nhập lại" + RESET);
            } else {
                users.setUsername(username);
                break;
            }
        }

        System.out.print("Nhập mật khẩu: ");
        users.setPassword(Validate.validateString());

        System.out.print("Xác nhận mật khẩu: ");
        while (true) {
            String repeatPass = Validate.validateString();
            if (users.getPassword().equals(repeatPass)) {
                break;
            } else {
                System.out.println(RED + "Mật khẩu không trùng khớp, mời nhập lại" + RESET);
            }
        }

        System.out.print("Nhập email: ");
        while (true) {
            String email = Validate.validateEmail();
            if (userService.existEmail(email)) {
                System.out.println(RED + "Email đăng nhập đã tồn tại, mời nhập lại" + RESET);
            } else {
                users.setEmail(email);
                break;
            }
        }

        System.out.print("Nhập số điện thoại: ");
        while (true) {
            String phone = Validate.validatePhone();
            if (userService.existPhone(phone)) {
                System.out.println(RED + "Số điện thoại đăng nhập đã tồn tại, mời nhập lại" + RESET);
            } else {
                users.setPhoneNumber(phone);
                break;
            }
        }

        userService.save(users);
        System.out.println(YELLOW + "Tạo tài khoản thành công" + RESET);
        login();
    }
}