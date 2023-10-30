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
        String currentPassword = usersEdit.getPassword();

        System.out.println("Nhập mật khẩu cũ: ");
        String oldPassword = Validate.validateString();

        while (!oldPassword.equals(currentPassword)) {
            System.out.println(RED + "Mật khẩu cũ không trùng khớp, Vui lòng nhập lại" + RESET);
            oldPassword = Validate.validateString();
        }

        System.out.println("Nhập mật khẩu mới: ");
        String newPassword = Validate.validateString();
        usersEdit.setPassword(newPassword);
        userService.save(usersEdit);

        System.out.println(YELLOW + "Đổi mật khẩu thành công, vui lòng đăng nhập lại!" + RESET);
        new WriteReadFile<Users>().writeFile(Config.PATH_LOGIN, null);
        new Home().menuHome();
    }

    private void showProfile() {
        System.out.println("Thông tin tài khoản: ");
        Users usersProfile = userService.findByID(users.getId());
        System.out.println(usersProfile);
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
                String newName = Config.validateString();
                usersProfile.setName(newName);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi tên thành công" + RESET);
                break;
            case 2:
                System.out.println("Nhập email cần đổi");
                String newEmail = Config.validateEmail();
                usersProfile.setEmail(newEmail);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi email thành công" + RESET);
                break;
            case 3:
                System.out.println("Nhập số điện thoại cần đổi");
                String newPhone = Config.validatePhone();
                usersProfile.setPhoneNumber(newPhone);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi số điện thoại thành công" + RESET);
                break;
            default:
                System.out.println(RED + "Không đúng định dạng, mời nhập lại" + RESET);
        }
    }
}
