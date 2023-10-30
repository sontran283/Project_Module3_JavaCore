package ra.view.admin;

import ra.config.Config;
import ra.config.WriteReadFile;
import ra.model.Users;
import ra.view.display.Home;

import static ra.config.Color.*;

public class AdminManager {
    public void menuAdmin() {
        do {
//            System.out.println("Hello: " + Home.userLogin.getName() + " !");

            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                       --->> ADMIN MENU <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                        1. Quản lý người dùng                         |");
            System.out.println("|                        2. Quản lý danh mục                           |");
            System.out.println("|                        3. Quản lý sản phẩm                           |");
            System.out.println("|                        4. Quản lý đơn hàng                           |");
            System.out.println("|                        0. log out                                    |");
            System.out.println(".======================================================================." + RESET);
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
                case 4:
                    new orderManagement().menuOrder();
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
