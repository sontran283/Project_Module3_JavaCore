package ra.view.admin;

import ra.config.Config;

import static ra.config.Color.*;

public class orderManagement {
    public void menuOrder() {
        int choice;
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                      --->> ORDER MANAGER <<---                       |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Hiển thị danh sách đơn hàng                    |");
            System.out.println("|                    2. Duyệt đơn hàng                                 |");
            System.out.println("|                    3. Tìm kiếm đơn hàng theo tên                     |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ, mời chọn lại"+RESET);
                    break;
            }
        } while (true);
    }
}
