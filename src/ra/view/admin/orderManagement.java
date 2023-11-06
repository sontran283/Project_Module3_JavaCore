package ra.view.admin;


import ra.config.Validate;
import ra.model.Order;
import ra.model.OrderStatus;
import ra.service.*;
import ra.service.impl.*;

import java.sql.SQLOutput;

import static ra.config.Color.*;

public class orderManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();

    public void menuOrder() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                      --->> ORDER MANAGER <<---                       |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                   1. Danh sách đơn hàng                              |");
            System.out.println("|                   2. Thay đổi trạng thái                             |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validateInt()) {
                case 1:
                    showListOrder();
                    break;
                case 2:
                    changeStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void changeStatus() {
        System.out.print("Nhập ID đơn hàng muốn thay đổi trạng thái, ");
        int orderId = Validate.validateInt();
        Order order = orderService.findByID(orderId);

        if (order == null) {
            System.out.println(RED + "Không có đơn hàng theo ID vừa nhập" + RESET);
            return;
        }

        if (order.getOrderStatus() == OrderStatus.WAITING) {
            if (order != null) {
                System.out.println("1. Duyệt đơn hàng");
                System.out.println("2. Huỷ đơn hàng");
                System.out.println("0. Quay lại");
                int choiceCheck = Validate.validateInt();
                switch (choiceCheck) {
                    case 1:
                        order.setOrderStatus(OrderStatus.CONFIRM);
                        orderService.update(order);
                        break;
                    case 2:
                        order.setOrderStatus(OrderStatus.CANCEL);
                        orderService.update(order);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println(RED + "Không hợp lệ, mời nhập lại" + RESET);
                        break;
                }
            }
        } else if (order.getOrderStatus() == OrderStatus.CONFIRM) {
            System.out.println(YELLOW + "Đã xác nhận đơn hàng" + RESET);
        } else if (order.getOrderStatus() == OrderStatus.CANCEL) {
            System.out.println(RED + "Không thể huỷ đơn hàng" + RESET);
        } else {
            System.out.println(RED + "Không hợp lệ" + RESET);
        }
    }

    private void showListOrder() {
        if (orderService.findAll() == null || orderService.findAll().isEmpty()) {
            System.out.println(RED + "Danh sách trống" + RESET);
            return;
        }

        System.out.println(YELLOW + "Danh sách đơn hàng" + RESET);
        System.out.printf("| %-10s | %-10s | %-20s | %-15s | %-30s | %-10s | %-15s | %-30s |%n ",
                "Order ID", "User ID", "Name", "Phone Number", "Address", "Total", "Order Status", "Order Details");
        for (Order order : orderService.findAll()) {
            System.out.println(order);
        }
    }
}
