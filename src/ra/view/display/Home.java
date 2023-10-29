package ra.view.display;

import ra.config.Config;
import ra.config.Validate;
import ra.constant.RoleName;
import ra.model.Users;
import ra.service.IUserService;
import ra.service.impl.UserServiceIMPL;
import ra.view.admin.AdminManager;
import ra.view.user.UserManager;

public class Home {
    IUserService userService = new UserServiceIMPL();
    public static Users userLogin;

    public void menuHome() {
        if (userLogin != null) {
            TypeMenu();
        } else {
            showMenu();
        }
    }

    public void showMenu() {
        do {
            System.out.println("Danh sách người dùng: ");
            for (Users users : userService.findAll()) {
                System.out.println(users);
            }
            System.out.println(".======================================================================.");
            System.out.println("|                     --->> HELLO, WELCOME BACK <<---                  |");
            System.out.println("|======================================================================|");
            System.out.println("|                             1. Đăng nhập                             |");
            System.out.println("|                             2. Đăng ký                               |");
            System.out.println("|                             0. Thoát                                 |");
            System.out.println(".======================================================================.");
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Config.validateInt()) {
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
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void login() {
        System.out.println(".======================================================================.");
        System.out.println("|                        --->> FORM LOGIN <<---                        |");
        System.out.println(".======================================================================.");
        System.out.println("Nhập tên tài khoản: ");
        String name = Config.validateString();

        System.out.println("Nhập mật khẩu: ");
        String pass = Config.validateString();

        Users users = userService.checkLogin(name, pass);
        if (users == null) {
            System.out.println("___ Sai tên tài khoản hoặc mật khẩu, mời nhập lại ___");
        } else {
            // đúng tên tk với mk
            if (users.getRole().equals(RoleName.ADMIN)) {
                userLogin = users;
                // chuyển trang quản lí với admin
                System.out.println("Đăng nhập thành công");
                new AdminManager().menuAdmin();

            } else {
                if (users.isStatus()) {
                    userLogin = users;
                    // chuyển đến trang user
                    System.out.println("Đăng nhập thành công");
                    new UserManager().menuUser();
                } else {
                    System.out.println("___ Tài khoản của bạn đã bị khoá ___ ");
                }
            }
        }
    }

    public void register() {
        System.out.println(".======================================================================.");
        System.out.println("|                       --->> FORM REGISTER <<---                      |");
        System.out.println(".======================================================================.");
        Users users = new Users();
        users.setId(userService.getNewId());

        System.out.println("ID: " + users.getId());

        System.out.println("Nhập họ tên: ");
        users.setName(Config.validateString());

        System.out.println("nhập tên tài khoản: ");
        while (true) {
            String username = Config.validateString();
            if (userService.existUsername(username)) {
                System.out.println("___ Tên đăng nhập đã tồn tại, mời nhập lại ___");
            } else {
                users.setUsername(username);
                break;
            }
        }

        System.out.println("Nhập mật khẩu: ");
        users.setPassword(Config.validateString());

        System.out.println("Xác nhận mật khẩu: ");
        while (true) {
            String repeatPass = Config.validateString();
            if (users.getPassword().equals(repeatPass)) {
                break;
            } else {
                System.out.println("___ Mật khẩu không trùng khớp, mời nhập lại ___");
            }
        }

        System.out.println("Nhập email: ");
        while (true) {
            String email = Validate.validateEmail();
            if (userService.existEmail(email)) {
                System.out.println("___ Email đăng nhập đã tồn tại, mời nhập lại ___");
            } else {
                users.setEmail(email);
                break;
            }
        }

        userService.save(users);
        System.out.println("Tạo tài khoản thành công!");
        login();
    }

    private void TypeMenu() {
        if (userLogin.getRole().equals(RoleName.ADMIN)) {
            new AdminManager().menuAdmin();
        } else {
            new UserManager().menuUser();
        }
    }
}
