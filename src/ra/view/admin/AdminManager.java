package ra.view.admin;

import ra.config.Config;
import ra.view.display.Home;

public class AdminManager {
    public void menuAdmin() {
        do {
            System.out.println("Hello: " + Home.userLogin.getName() + " !");
            System.out.println(".======================================================================.");
            System.out.println("|                       --->> ADMIN MENU <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println("|                        1. Quản lý người dùng                         |");
            System.out.println("|                        2. Quản lý danh mục                           |");
            System.out.println("|                        3. Quản lý sản phẩm                           |");
            System.out.println("|                        0. log out                                    |");
            System.out.println(".======================================================================.");
            System.out.println("                 --->> Mời nhập lựa chọn của bạn <<---");
            switch (Config.validateInt()) {
                case 1:
                    new userManagement().menuUser();
                    break;
                case 2:
                    new catalogManagement().menuCatalog();
                    break;
                case 3:
                    new productManagement().menuProduct();
                    break;
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
