package ra.view.user;

import ra.config.Config;
import ra.config.WriteReadFile;
import ra.model.Users;
import ra.view.display.Home;

import static ra.config.Color.*;


public class UserManager {
    public void menuUser() {
        do {
//            System.out.println("Hello: " + Home.userLogin.getName() + " !");
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                          --->> MENU  USER <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                            1. Trang chủ                              |");
            System.out.println("|                            2. Giỏ hàng                               |");
            System.out.println("|                            3. Thay đổi mật khẩu                      |");
            System.out.println("|                            0. Log out                                |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Config.validateInt()) {
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
