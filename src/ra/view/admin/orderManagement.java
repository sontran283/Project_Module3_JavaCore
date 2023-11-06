package ra.view.admin;


import ra.config.Validate;
import ra.model.Order;
import ra.model.OrderStatus;
import ra.service.*;
import ra.service.impl.*;

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
                    showListOrder111();
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
        System.out.println("Nhập ID đơn hàng muốn thay đổi trạng thái: ");
        int orderId = Validate.validateInt();
        Order order = orderService.findByID(orderId);
        if (order.getOrderStatus() == OrderStatus.WAITING) {
            if (order != null) {
                System.out.println("1. Duyệt đơn hàng: ");
                System.out.println("2. Huỷ đơn hàng: ");
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
                    default:
                        System.out.println("Nhap k hop le");
                }
            }
        } else if (order.getOrderStatus() == OrderStatus.CONFIRM) {
            System.out.println("Đã xác nhận đơn hàng");
        } else if (order.getOrderStatus() == OrderStatus.CANCEL) {
            System.out.println("không thể huỷ đơn hàng");
        } else {
            System.out.println("ko hoop le");
        }
    }

    private void showListOrder111() {
        System.out.println("Danh sách đơn hàng");
        for (Order order : orderService.findAll()) {
            System.out.println(order);
        }
    }
}
