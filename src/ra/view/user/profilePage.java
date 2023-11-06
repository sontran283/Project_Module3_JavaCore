package ra.view.user;


import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.model.Users;
import ra.service.ICartService;
import ra.service.IUserService;
import ra.service.impl.CartServiceIMPL;
import ra.service.impl.UserServiceIMPL;
import ra.view.display.Home;

import static ra.config.Color.*;
import static ra.view.display.Home.config;

public class profilePage {
    IUserService userService = new UserServiceIMPL();
    ICartService cartService = new CartServiceIMPL();

    public Users users = config.readFile(WriteReadFile.PATH_USER_LOGIN);

    public void profileHome() {
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
            switch (Validate.validatePositiveInt()) {
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
        String pass = usersEdit.getPassword();

        System.out.print("Nhập mật khẩu cũ: ");
        String oldPass = Validate.validateString();

        while (!oldPass.equals(pass)) {
            System.out.println(RED + "Mật khẩu cũ không trùng khớp, Vui lòng nhập lại" + RESET);
            oldPass = Validate.validateString();
        }

        System.out.print("Nhập mật khẩu mới: ");
        String newPass = Validate.validateString();
        usersEdit.setPassword(newPass);
        userService.save(usersEdit);

        System.out.println(YELLOW + "Đổi mật khẩu thành công, vui lòng đăng nhập lại!" + RESET);
        new WriteReadFile<Users>().writeFile(WriteReadFile.PATH_LOGIN, null);
        new Home().menuHome();
    }

    private void showProfile() {
        System.out.println(YELLOW + "Thông tin tài khoản: " + RESET);
        Users usersProfile = userService.findByID(users.getId());

        System.out.println("- Tên: " + usersProfile.getName());
        System.out.println("- Email: " + usersProfile.getEmail());
        System.out.println("- Số điện thoại: " + usersProfile.getPhoneNumber());
    }

    private void changeProfile() {
        Users usersProfile = userService.findByID(users.getId());
        System.out.println(usersProfile);
        System.out.println(BLUE + ".=================================================.");
        System.out.println("|        --->> Sửa thông tin cá nhân <<---        |");
        System.out.println("|=================================================|");
        System.out.println(YELLOW + "|               1. Sửa tên                        |");
        System.out.println("|               2. Sửa email                      |");
        System.out.println("|               3. Sửa số điện thoại              |");
        System.out.println("|               0. Quay lại                       |");
        System.out.println(".=================================================." + RESET);
        switch (Validate.validatePositiveInt()) {
            case 1:
                System.out.print("Nhập tên cần đổi: ");
                String newName;
                while (true) {
                    newName = Validate.validateString();
                    if (newName.equals(usersProfile.getName())) {
                        System.out.println(RED + "Tên giống tên ban đầu, mời nhập lại" + RESET);
                    } else {
                        break;
                    }
                }
                usersProfile.setName(newName);
                userService.save(usersProfile);
                System.out.println(YELLOW + "Đổi tên thành công" + RESET);
            case 2:
                System.out.print("Nhập email cần đổi");
                String newEmail = Validate.validateEmail();
                if (newEmail.equals(usersProfile.getEmail())) {
                    System.out.println(RED + "Email giống email ban đầu" + RESET);
                    return;
                } else {
                    usersProfile.setEmail(newEmail);
                    userService.save(usersProfile);
                    System.out.println(YELLOW + "Đổi email thành công" + RESET);
                    break;
                }
            case 3:
                System.out.print("Nhập số điện thoại cần đổi");
                String newPhone = Validate.validatePhone();
                if (newPhone.equals(usersProfile.getPhoneNumber())) {
                    System.out.println(RED + "Số điện thoại giống số điện thoại ban đầu" + RESET);
                    return;
                } else {
                    usersProfile.setPhoneNumber(newPhone);
                    userService.save(usersProfile);
                    System.out.println(YELLOW + "Đổi số điện thoại thành công" + RESET);
                    break;
                }
            case 0:
                return;
            default:
                System.out.println(RED + "Không đúng định dạng, mời nhập lại" + RESET);
                break;
        }
    }
}
