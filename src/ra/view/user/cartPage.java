package ra.view.user;

import ra.config.Validate;
import ra.model.Cart;
import ra.model.Order;
import ra.model.OrdersDetail;
import ra.model.Product;
import ra.service.*;
import ra.service.impl.*;

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
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                        --->> CART PAGE <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Thay đổi số lượng đặt hàng                     |");
            System.out.println("|                    2. Xóa sản phẩm trong giỏ hàng                    |");
            System.out.println("|                    3. Đặt hàng                                       |");
            System.out.println("|                    4. Hiển thị danh sách trong giỏ hàng              |");
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
                    order();
                    break;
                case 4:
                    showListOrder();
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
        System.out.println("Nhập ID sản phẩm cần thay đổi Stock: ");
        int productID = Validate.validateInt();

        System.out.println("Nhập số lượng mới:");
        int newQuantity = Validate.validateInt();

        Cart cart = new Cart();
        Map<Integer, Integer> products = cart.getProducts();

        if (products.containsKey(productID)) {
            products.put(productID, newQuantity);
            System.out.println(YELLOW + "Thay đổi số lượng thành công" + RESET);
        } else {
            System.out.println(RED + "Sản phẩm không tồn tại trong giỏ hàng" + RESET);
        }
    }

    private void deleteProduct() {
        System.out.println("Nhập ID sản phẩm cần xoá");
        int productId = Validate.validateInt();

        Cart cart = new Cart();
        Map<Integer, Integer> products = cart.getProducts();

        if (products.containsKey(productId)) {
            products.remove(productId);
            System.out.println(YELLOW + "Xoá sản phẩm thành công" + RESET);
        } else {
            System.out.println(RED + "Sản phẩm không tồn tại trong giỏ hàng" + RESET);
        }
    }

    private void order() {
        Cart cart = new Cart();
        Map<Integer, Integer> products = cart.getProducts();

        if (products.isEmpty()) {
            System.out.println(RED + "Giỏ hàng trống, không thể đặt hàng" + RESET);
        } else {
            System.out.println("Thông tin đơn hàng");
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                System.out.println("Sản phẩm ID: " + productId);
                System.out.println("Số lượng: " + quantity);
                System.out.println("-----");
            }

            System.out.println("Bạn có muốn đặt hàng? (1: Đồng ý, 0: Hủy bỏ)");
            int choice = Validate.validateInt();
            if (choice == 1) {
                // tao don hang
                Order order = new Order();
                order.setUserId(1);

                orderService.address(order);

                // tao chi tiêt don hang
                for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue();

                    OrdersDetail ordersDetail = new OrdersDetail();
                    ordersDetail.setOrderId(order.getOrderId()); // Đặt giá trị orderId tương ứng
                    ordersDetail.setProductId(productId);
                    ordersDetail.setQuantity(quantity);
                    ordersDetailService.addOrdersDetail(ordersDetail);
                }

                // Xóa sản phẩm khỏi giỏ hàng sau khi đặt hàng thành công
                cart.removeProduct(1);

                System.out.println("Đặt hàng thành công.");
            } else {
                System.out.println("Đặt hàng bị hủy bỏ.");
            }
        }
    }

    private void showListOrder() {
        Cart cart = new Cart();
        Map<Integer, Integer> products = cart.getProducts();

        if (products.isEmpty()) {
            System.out.println(RED + "Giỏ hàng trống" + RESET);
        } else {
            System.out.println(YELLOW + "Danh sách sản phẩm trong giỏ hàng:" + RESET);
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Lấy thông tin sản phẩm từ productService bằng productId
                Product product = productService.findByID(productId);

                // Hiển thị thông tin sản phẩm và số lượng
                System.out.println("Sản phẩm: " + product.getProductName());
                System.out.println("Số lượng: " + quantity);
                System.out.println("-----");

                // hiển thị id sản phẩm và số lượng
                System.out.println("Sản phẩm ID: " + productId);
                System.out.println("Số lượng: " + quantity);
                System.out.println("-----");
            }
        }
    }
}
