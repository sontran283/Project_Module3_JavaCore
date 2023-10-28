package ra.view.admin;

import ra.config.Config;
import ra.model.Users;
import ra.service.*;
import ra.service.impl.*;

public class userManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    public static Users userLogin;


    public void menuUser() {
        int choice;
        do {
            System.out.println(".======================================================================.");
            System.out.println("|                        --->> USER MANAGER <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println("|                   1. Hiển thị danh sách người dùng                   |");
            System.out.println("|                   2. Tìm kiếm người dùng theo tên                    |");
            System.out.println("|                   3. Block/ Unblock tài khoản người dùng             |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================.");
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    showUser();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    editStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("___ Lựa chọn không hợp lệ, mời chọn lại ___");
                    break;
            }
        } while (true);
    }

    private void showUser() {
        System.out.println("Danh sách người dùng: ");
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
    }

    private void searchUser() {
        System.out.println("Mời nhập tên người dùng cần tìm: ");
        String searchName = Config.scanner().nextLine();
        int count = 0;
        System.out.println("Danh sách người dùng cần tìm kiếm");
        for (Users users : userService.findAll()) {
            if (users.getUsername().contains(searchName)) {
                System.out.println(users);
                count++;
            }
        }
        System.out.printf("Tìm thấy %d người dùng theo từ khoá vừa nhập ", count);
        System.out.println();
    }

    private void editStatus() {
        System.out.println("Nhập ID người dùng cần thay đổi trạng thái: ");
        int userId = Integer.parseInt(Config.scanner().nextLine());

        Users user = userService.findByID(userId);
        if (user != null) {
            boolean currentStatus = user.isStatus();
            boolean newStatus = !currentStatus;

            user.setStatus(newStatus);
            userService.update(user);

            if (newStatus) {
                System.out.println("Tài khoản đã mở khóa.");
            } else {
                System.out.println("Tài khoản đã bị khóa.");
            }
        } else {
            System.out.println("___ Không tìm thấy người dùng với ID đã nhập ___");
        }
    }
}
