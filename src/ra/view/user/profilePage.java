package ra.view.user;

import ra.config.Config;
import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.model.Users;
import ra.service.IUserService;
import ra.service.impl.UserServiceIMPL;
import ra.view.display.Home;

import static ra.config.Color.*;
import static ra.view.display.Home.config;

public class profilePage {
    IUserService userService = new UserServiceIMPL();
    public Users users = config.readFile(WriteReadFile.PATH_USER_LOGIN);


    public void profileHome() {
        int choice;
        do {
            WriteReadFile<Users> config = new WriteReadFile<>();
            Users users = config.readFile(WriteReadFile.PATH_USER_LOGIN);
            System.out.println("Xin chào: " + users.getName());

            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                       --->> PROFILE PAGE <<---                       |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Đổi mật khẩu                                   |");
            System.out.println("|                    2. Hiển thị thông tin cá nhân                     |");
            System.out.println("|                    3. Chỉnh sửa thông tin cá nhân                    |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    changePass();
                    break;
                case 2:
                    showProfile();
                    break;
                case 3:
                    changeProfile();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void changePass() {
        Users usersEdit = userService.findByID(users.getId());
        System.out.println("Nhập mật khẩu cần đổi");
        String password = Validate.validateString();
        usersEdit.setPassword(password);
        userService.save(usersEdit);
        System.out.println(YELLOW + "Đổi mật khẩu thành công" + RESET);
        new WriteReadFile<Users>().writeFile(Config.PATH_LOGIN, null);
        new Home().menuHome();
    }

    private void showProfile() {
        System.out.println("Thông tin cá nhân: ");
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
    }

    private void changeProfile() {
        Users usersProfile = userService.findByID(users.getId());
        System.out.println(usersProfile);
        System.out.println("Sửa thông tin cá nhân: ");
        System.out.println("1.Sửa tên");
        System.out.println("2.Sửa email");
        System.out.println("3.Sửa số điện thoại");
        switch (Validate.validateInt()) {
            case 1:
                System.out.println("Nhập tên cần đổi");
                String fullName = Config.validateString();
                usersProfile.setName(fullName);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi tên thành công" + RESET);
                break;
            case 2:
                System.out.println("Nhập email cần đổi");
                String email = Config.validateEmail();
                usersProfile.setEmail(email);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi email thành công" + RESET);
                break;
            case 3:
                System.out.println("Nhập số điện thoại cần đổi");
                String phone = Config.validatePhone();
                usersProfile.setPassword(phone);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi số điện thoại thành công" + RESET);
                break;
            default:
                System.out.println(RED + "Nhập không đúng dịnh dạng, mời nhập lại" + RESET);
        }
    }
}
