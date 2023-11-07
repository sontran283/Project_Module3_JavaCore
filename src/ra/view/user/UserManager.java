package ra.view.user;

import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.model.Users;
import ra.view.display.Home;

import static ra.config.Color.*;

public class UserManager {
    public void menuUser() {
        do {
            WriteReadFile<Users> config = new WriteReadFile<>();
            Users users = config.readFile(WriteReadFile.PATH_USER_LOGIN);
            System.out.println(CYAN_BOLD_BRIGHT + "您好: " + users.getName() + RESET);

            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|           --->> CỬA HÀNG HOA QUẢ TƯƠI COGO XIN CHÀO <<---            |");
            System.out.println("|                        \uD83C\uDF4E    \uD83C\uDF4C     \uD83C\uDF4A     \uD83C\uDF47                       |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                         1. Cửa hàng                                  |");
            System.out.println("|                         2. Giỏ hàng                                  |");
            System.out.println("|                         3. Thông tin cá nhân                         |");
            System.out.println("|                         0. Đăng xuất                                 |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validatePositiveInt()) {
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
