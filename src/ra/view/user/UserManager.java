package ra.view.user;

import ra.config.Config;
import ra.config.WriteReadFile;
import ra.model.Users;
import ra.view.admin.userManagement;
import ra.view.display.Home;

import static ra.config.Color.*;


public class UserManager {
    public void menuUser() {
        do {
            WriteReadFile<Users> config = new WriteReadFile<>();
            Users user = config.readFile(Config.PATH_LOGIN);
            System.out.println("Hello " + user.getName());

            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                           --->> HOME PAGE <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                            1. Trang chủ                              |");
            System.out.println("|                            2. Giỏ hàng                               |");
            System.out.println("|                            3. Thay đổi thông tin cá nhân             |");
            System.out.println("|                            0. Log out                                |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Config.validateInt()) {
                case 1:
                    new homePage().home();
                    break;
                case 2:
                    new cartPage().cartHome();
                    break;
                case 3:
                    new profilePage().profileHome();
                    break;
                case 0:
                    new WriteReadFile<Users>().writeFile(WriteReadFile.PATH_USER_LOGIN, null);
                    new Home().menuHome();
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }
}
