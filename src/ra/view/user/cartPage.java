package ra.view.user;

import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.model.*;
import ra.service.*;
import ra.service.impl.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import static ra.config.Color.*;

public class cartPage {

    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    ICartService cartService = new CartServiceIMPL();


    public void cartHome() {
        Order order = new Order();

        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                        --->> CART PAGE <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Thay đổi số lượng đặt hàng                     |");
            System.out.println("|                    2. Xóa sản phẩm trong giỏ hàng                    |");
            System.out.println("|                    3. Đặt hàng                                       |");
            System.out.println("|                    4. Hiển thị danh sách trong giỏ hàng              |");
            System.out.println("|                    5. Lịch sử đơn hàng                               |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validateInt()) {
                case 1:
                    changeStock();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    checkOut();
                    break;
                case 4:
                    showListOrder();
                    break;
                case 5:
                    orderHistory(order);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void changeStock() {
        Cart cart = cartService.findCartByUserLogin();
        Map<Integer, Integer> products = cart.getProducts();

        System.out.print("Nhập ID sản phẩm cần thay đổi Stock: ");
        int productID = Validate.validateInt();

        System.out.print("Nhập số lượng mới:");
        int newQuantity = Validate.validateInt();

        Product product1 = productService.findByID(productID);
        if (products.containsKey(productID)) {
            if (newQuantity > product1.getStock()) {
                System.out.println(RED + "Số lượng trong kho không đủ, trong kho còn: " + product1.getStock() + RESET);
                return;
            } else if (newQuantity < 1) {
                System.out.println(RED + "Số lượng không hợp lệ, mời nhập lại" + RESET);
                return;
            }
            products.put(productID, newQuantity);
            cartService.update(cart);
            System.out.println(YELLOW + "Thay đổi số lượng thành công" + RESET);
        } else {
            System.out.println(RED + "Sản phẩm không tồn tại trong giỏ hàng" + RESET);
        }
    }

    private void deleteProduct() {
        Cart cart = cartService.findCartByUserLogin();
        Map<Integer, Integer> products = cart.getProducts();

        System.out.print("Nhập ID sản phẩm cần xoá, ");
        int productId = Validate.validateInt();

        if (products.containsKey(productId)) {
            products.remove(productId);
            cartService.update(cart);
            System.out.println(YELLOW + "Xoá sản phẩm thành công" + RESET);
        } else {
            System.out.println(RED + "Sản phẩm không tồn tại trong giỏ hàng" + RESET);
        }
    }

    private void checkOut() {
        WriteReadFile<Users> config = new WriteReadFile<>();
        Users userLogin = config.readFile(WriteReadFile.PATH_USER_LOGIN);

        Cart cart = cartService.findCartByUserLogin();
        Map<Integer, Integer> products = cart.getProducts();

        if (products.isEmpty()) {
            System.out.println(RED + "Giỏ hàng trống, không thể đặt hàng" + RESET);
        } else {
            System.out.println("Thông tin đơn hàng");
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                System.out.println("ID: " + productId);
                System.out.println("Số lượng: " + quantity);
                System.out.println("------------------------");
            }

            System.out.println(YELLOW + "Bạn có muốn đặt hàng? (1: Đồng ý, 0: Hủy bỏ)" + RESET);
            int choice = Validate.validateInt();
            if (choice == 1) {
                // Tạo đơn hàng
                Order order = new Order();
                order.setUserId(userLogin.getId());
                order.setOrdersDetails(products);
                order.setOrderStatus(OrderStatus.WAITING);

                // Lưu đơn hàng
                orderService.save(order);

                // Cập nhật số lượng sản phẩm trong danh sách sản phẩm
                for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue();

                    Product product = productService.findByID(productId);
                    int currentStock = product.getStock();
                    int updatedStock = currentStock - quantity;
                    product.setStock(updatedStock);

                    productService.update(product);
                }

                // Cập nhật giỏ hàng
                cart.removeProduct();
                cartService.save(cart);

                // Cập nhật biến order với đơn hàng mới được tạo
                order = orderService.findByID(order.getOrderId());

                System.out.println(YELLOW + "Đặt hàng thành công" + RESET);
            } else if (choice == 0) {
                System.out.println(YELLOW + "Đặt hàng bị hủy bỏ" + RESET);
            } else {
                System.out.println(RED + "Không hợp lệ, mời nhập lại" + RESET);
            }
        }
    }

    private void showListOrder() {
        Cart cart = cartService.findCartByUserLogin();
        Map<Integer, Integer> products = cart.getProducts();

        if (cart == null) {
            System.out.println(RED + "Giỏ hàng trống" + RESET);
            return;
        }

        if (products.isEmpty()) {
            System.out.println(RED + "Giỏ hàng trống" + RESET);
        } else {
            double total = 0;
            System.out.println(YELLOW + "Danh sách sản phẩm trong giỏ hàng:" + RESET);
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Lấy thông tin sản phẩm từ productService bằng productId
                Product product = productService.findByID(productId);

                System.out.println("ID: " + productId);
                System.out.println("Tên sản phẩm: " + product.getProductName());
                System.out.println("Giá tiền: " + formatCurrency(product.getUnitPrice()));
                System.out.println("Số lượng: " + quantity);
                System.out.println("------------------------");

                // Tính tổng tiền
                double productTotal = product.getUnitPrice() * quantity;
                total += productTotal;
            }
            System.out.println("Tổng tiền: " + formatCurrency(total));
            System.out.println("------------------------");
        }
    }

    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " đ";
    }

    private void orderHistory(Order order) {
        WriteReadFile<Users> config = new WriteReadFile<>();
        Users userLogin = config.readFile(WriteReadFile.PATH_USER_LOGIN);

        System.out.println(YELLOW + "Thông tin đơn hàng" + RESET);
        System.out.println("-ID đơn hàng: " + order.getOrderId());
        System.out.println("-Người đặt hàng: " + userLogin.getName());
        System.out.println("-Số điện thoại: " + userLogin.getPhoneNumber());
        System.out.println("-Trạng thái: " + order.getOrderStatus());

        System.out.println(YELLOW + "Chi tiết đơn hàng:" + RESET);
        Map<Integer, Integer> orderDetails = order.getOrdersDetails();
        for (Map.Entry<Integer, Integer> entry : orderDetails.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            // Lấy thông tin sản phẩm từ productService bằng productId
            Product product = productService.findByID(productId);
            Order order1  = orderService.findByID(order.getOrdersDetails().get(order));

            System.out.println("Tên sản phẩm: " + order1.getOrdersDetails());
            System.out.println("Tên sản phẩm: " + order1.getOrderAt());
            System.out.println("Tên sản phẩm: " + order1.getOrderStatus());
            System.out.println("Tên sản phẩm: " + order1.getName());
            System.out.println("Giá tiền: " + formatCurrency(product.getUnitPrice()));
            System.out.println("Số lượng: " + quantity);
            System.out.println("------------------------");
        }
    }
}
