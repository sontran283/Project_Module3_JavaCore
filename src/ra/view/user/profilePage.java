package ra.view.user;

import ra.config.Config;
import ra.model.Users;
import ra.service.IUserService;
import ra.service.impl.UserServiceIMPL;

import static ra.config.Color.*;

public class profilePage {
    IUserService userService = new UserServiceIMPL();

    public void profileHome() {
        int choice;
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                       --->> PROFILE PAGE <<---                       |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Chỉnh sửa thông tin cá nhân                    |");
            System.out.println("|                    2. Hiển thị thông tin cá nhân                     |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    changeProfile();
                    break;
                case 2:
                    showProfile();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void changeProfile() {
        System.out.println("Nhập ID người dùng cần thay đổi trạng thái: ");
        int userId = Integer.parseInt(Config.scanner().nextLine());

        Users user = userService.findByID(userId);
        if (user != null) {
            if (user.isAdmin()) {
                System.out.println(RED + "Không thể thay đổi trạng thái tài khoản admin" + RESET);
            } else {
                boolean currentStatus = user.isStatus();
                boolean newStatus = !currentStatus;

                user.setStatus(newStatus);
                userService.update(user);

                if (newStatus) {
                    System.out.println(BLUE + "Tài khoản đã mở khóa" + RESET);
                } else {
                    System.out.println(RED + "Tài khoản đã bị khóa" + RESET);
                }
            }
        } else {
            System.out.println(RED + "Không tìm thấy người dùng với ID đã nhập" + RESET);
        }
    }

    private void showProfile() {
        System.out.println("Thông tin cá nhân: ");
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
    }
}
