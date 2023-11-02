package ra.view.admin;

import ra.config.Validate;
import ra.constant.RoleName;
import ra.model.Users;
import ra.service.*;
import ra.service.impl.*;


import static ra.config.Color.*;

public class userManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    public static Users userLogin;


    public void menuUser() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                        --->> USER MANAGER <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                   1. Hiển thị danh sách người dùng                   |");
            System.out.println("|                   2. Tìm kiếm người dùng theo tên                    |");
            System.out.println("|                   3. Block/Unblock tài khoản người dùng              |");
            System.out.println("|                   4. Phân quyền ADMIN/USER                           |");
            System.out.println("|                   5. Xoá người dùng                                  |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validateInt()) {
                case 1:
                    showUser();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    editStatus();
                    break;
                case 4:
                    changeRole();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void showUser() {
        System.out.println(YELLOW + "Danh sách người dùng: " + RESET);
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
    }

    private void searchUser() {
        System.out.print("Mời nhập tên người dùng cần tìm: ");
        String searchName = Validate.validateString().toLowerCase();
        int count = 0;
        System.out.println(YELLOW + "Danh sách người dùng cần tìm kiếm" + RESET);
        for (Users users : userService.findAll()) {
            if (users.getUsername().toLowerCase().contains(searchName)) {
                System.out.println(users);
                count++;
            }
        }
        System.out.printf("Tìm thấy %d người dùng theo từ khoá vừa nhập ", count);
        System.out.println();
    }

    private void editStatus() {
        System.out.print("Nhập ID người dùng cần thay đổi trạng thái: ");
        int userId = Integer.parseInt(Validate.validateString());

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

    private void changeRole() {
        System.out.print("Nhập ID người dùng cần thay đổi Role: ");
        int userId = Integer.parseInt(Validate.validateString());

        Users user = userService.findByID(userId);
        if (user != null) {
            if (user.isAdmin()) {
                user.setRole(RoleName.USER);
                user.setAdmin(false);
                userService.update(user);
                System.out.println(YELLOW + "Đã thay đổi từ ADMIN thành USER" + RESET);
            } else {
                user.setRole(RoleName.ADMIN);
                user.setAdmin(true);
                userService.update(user);
                System.out.println(YELLOW + "Đã thay đổi từ USER thành ADMIN" + RESET);
            }
        } else {
            System.out.println(RED + "Không tìm thấy người dùng với ID đã nhập" + RESET);
        }
    }

    private void deleteUser() {
        System.out.print("Nhập ID người dùng cần xoá: ");
        int userId = Integer.parseInt(Validate.validateString());

        Users user = userService.findByID(userId);
        if (user != null) {
            if (user.isAdmin()) {
                System.out.println(RED + "Không thể xóa tài khoản admin" + RESET);
            } else {
                userService.delete(user.getId());
                System.out.println(YELLOW + "Xoá thành công" + RESET);
            }
        } else {
            System.out.println(RED + "Không tìm thấy người dùng với ID đã nhập" + RESET);
        }
    }
}
