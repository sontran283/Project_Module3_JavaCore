package ra.view.admin;


import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.model.Order;
import ra.model.OrderStatus;
import ra.model.Product;
import ra.model.Users;
import ra.service.*;
import ra.service.impl.*;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ra.config.Color.*;

public class orderManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    WriteReadFile<Users> config = new WriteReadFile<>();
    Users userLogin = config.readFile(WriteReadFile.PATH_USER_LOGIN);

    public void menuOrder() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                    --->> QUẢN LÝ ĐƠN HÀNG <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                   1. Danh sách đơn hàng                              |");
            System.out.println("|                   2. Thay đổi trạng thái                             |");
            System.out.println("|                   3. Tìm kiếm đơn hàng theo UserID                   |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validatePositiveInt()) {
                case 1:
                    showListOrder();
                    break;
                case 2:
                    changeStatus();
                    break;
                case 3:
                    searchOrder();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void showListOrder() {
        List<Order> oders = orderService.findAll();
        if (oders == null || oders.isEmpty()) {
            System.out.println(RED + "Không có đơn hàng nào" + RESET);
            return;
        }
        List<Order> orderUser = oders.stream().filter(o -> o.getUserId() == userLogin.getId()).collect(Collectors.toList());

        System.out.println("__________________________________");
        for (Order order : oders) {
            System.out.println("-Order ID: " + order.getOrderId());
            System.out.println("-User ID: " + order.getUserId());
            System.out.println("-Name: " + order.getName());
            System.out.println("-Phone Number: " + order.getPhoneNumber());
            System.out.println("-Address: " + order.getAddress());
            String formattedTotal = formatCurrency(order.getTotal());
            System.out.println("-Total: " + formattedTotal);
            System.out.println("-Order Status: " + order.getOrderStatus());
            for (Integer id : order.getOrdersDetails().keySet()) {
                System.out.println("-Tên sản phẩm: " + productService.findByID(id).getProductName());
                System.out.println("-Số lượng: " + order.getOrdersDetails().get(id));
            }
            System.out.println("__________________________________");
        }
    }

    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " đ";
    }

    private void changeStatus() {
        System.out.print(YELLOW + "Nhập ID đơn hàng muốn thay đổi trạng thái, Hoặc nhập 0 để quay lại: " + RESET);
        int orderId = Validate.validatePositiveInt();
        Order order = orderService.findByID(orderId);

        if (orderId == 0) {
            return;
        }

        if (order == null) {
            System.out.println(RED + "Không có đơn hàng theo ID vừa nhập" + RESET);
            return;
        }

        if (order.getOrderStatus() == OrderStatus.WAITING) {
            if (order != null) {
                System.out.println("1. Duyệt đơn hàng");
                System.out.println("2. Huỷ đơn hàng");
                System.out.println("0. Quay lại");
                int choiceCheck = Validate.validatePositiveInt();
                switch (choiceCheck) {
                    case 1:
                        order.setOrderStatus(OrderStatus.CONFIRM);
                        System.out.println(YELLOW + "Duyệt đơn hàng thành công" + RESET);
                        orderService.update(order);
                        break;
                    case 2:
                        // truoc khi huy don hang, lay so luong sp ban dau tu kho hang
                        Map<Integer, Integer> initialQuantities = order.getOrdersDetails();

                        order.setOrderStatus(OrderStatus.CANCEL);
                        System.out.println(YELLOW + "Huỷ đơn hàng thành công" + RESET);
                        orderService.update(order);

                        // sau khi huy don hang,cap nhat lai so luong sp trong kho
                        for (Integer productId : initialQuantities.keySet()) {
                            int quantity = initialQuantities.get(productId);
                            Product product = productService.findByID(productId);
                            if (product != null) {
                                // tra lai so luong sp ban dau
                                product.setStock(product.getStock() + quantity);
                                productService.update(product);
                            }
                        }
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

    private void searchOrder() {
        System.out.println(YELLOW + "Nhập UserID cần tìm kiếm, Hoặc nhập 0 để quay lại: " + RESET);
        int UserID = Validate.validatePositiveInt();
        if (UserID == 0) {
            return;
        }

        int count = 0;
        System.out.println(YELLOW + "Danh sách đơn hàng cần tìm " + RESET);
        System.out.println("__________________________________");
        for (Order order : orderService.findAll()) {
            if (order.getUserId() == UserID) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("User ID: " + order.getUserId());
                System.out.println("Name: " + order.getName());
                System.out.println("Phone Number: " + order.getPhoneNumber());
                System.out.println("Address: " + order.getAddress());
                String formattedTotal = formatCurrency(order.getTotal());
                System.out.println("Total: " + formattedTotal);
                System.out.println("Order Status: " + order.getOrderStatus());
                System.out.println("Chi tiết đơn hàng:");

                for (Integer productId : order.getOrdersDetails().keySet()) {
                    Product product = productService.findByID(productId);
                    if (product != null) {
                        int quantity = order.getOrdersDetails().get(productId);
                        System.out.println("Tên sản phẩm: " + product.getProductName());
                        System.out.println("Số lượng: " + quantity);
                    }
                }

                System.out.println("__________________________________");
                count++;
            }
        }
        System.out.printf("Tìm thấy %d đơn hàng theo UserID vừa nhập ", count);
        System.out.println();
    }
}
