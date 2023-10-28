package ra.view.user;

import ra.config.Config;
import ra.view.display.Home;

public class UserManager {
    public void menuUser() {
        do {
            System.out.println("Hello: " + Home.userLogin.getName() + " !");
            System.out.println(".======================================================================.");
            System.out.println("|                          --->> MENU  USER <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println("|                            1. Trang chủ                              |");
            System.out.println("|                            2. Giỏ hàng                               |");
            System.out.println("|                            3. Thay đổi mật khẩu                      |");
            System.out.println("|                            0. Log out                                |");
            System.out.println(".======================================================================.");
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Config.validateInt()) {
                case 0:
                    Home.userLogin = null;
                    new Home().menuHome();
                    break;
                default:
                    System.out.println("___ Lựa chọn không hợp lệ, mời chọn lại ___");
                    break;
            }
        } while (true);
    }
}
