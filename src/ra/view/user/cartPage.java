package ra.view.user;

import ra.config.Validate;
import ra.config.WriteReadFile;
import ra.model.*;
import ra.service.*;
import ra.service.impl.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ra.config.Color.*;

public class cartPage {

    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    ICartService cartService = new CartServiceIMPL();
    WriteReadFile<Users> config = new WriteReadFile<>();
    Users userLogin = config.readFile(WriteReadFile.PATH_USER_LOGIN);

    public void cartHome() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                         --->> GIỎ HÀNG <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Thay đổi số lượng đặt hàng                     |");
            System.out.println("|                    2. Xóa sản phẩm trong giỏ hàng                    |");
            System.out.println("|                    3. Đặt hàng                                       |");
            System.out.println("|                    4. Hiển thị danh sách trong giỏ hàng              |");
            System.out.println("|                    5. Lịch sử đơn hàng                               |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validatePositiveInt()) {
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
                    orderHistory();
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

        System.out.print("Nhập ID sản phẩm cần thay đổi số lượng: ");
        int productID = Validate.validatePositiveInt();

        // kiem tra sp co ton tai trong gio hang hay ko
        if (!products.containsKey(productID)) {
            System.out.println(RED + "Sản phẩm không tồn tại trong giỏ hàng" + RESET);
            return;
        }

        System.out.print("Nhập số lượng mới: ");
        int newQuantity = Validate.validatePositiveInt();

        Product product1 = productService.findByID(productID);

        if (newQuantity > product1.getStock()) {
            System.out.println(RED + "Số lượng trong kho không đủ, trong kho còn: " + product1.getStock() + " sản phẩm " + RESET);
            return;
        } else if (newQuantity < 1) {
            System.out.println(RED + "Số lượng không hợp lệ, mời nhập lại" + RESET);
            return;
        }

        products.put(productID, newQuantity);
        cartService.update(cart);
        System.out.println(YELLOW + "Thay đổi số lượng thành công" + RESET);
    }


    private void deleteProduct() {
        Cart cart = cartService.findCartByUserLogin();
        Map<Integer, Integer> products = cart.getProducts();

        System.out.print("Nhập ID sản phẩm cần xoá: ");
        int productId = Validate.validatePositiveInt();

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

        if (cart == null) {
            System.out.println(RED + "Giỏ hàng trống, không thể đặt hàng" + RESET);
            return;
        }

        Map<Integer, Integer> products = cart.getProducts();

        double total = 0;
        if (products.isEmpty()) {
            System.out.println(RED + "Giỏ hàng trống, không thể đặt hàng" + RESET);
        } else {
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Lấy thông tin sản phẩm từ productService bằng productId
                Product product = productService.findByID(productId);

                // Tính tổng tiền
                double productTotal = product.getUnitPrice() * quantity;
                total += productTotal;
            }

            System.out.println("Thông tin đơn hàng");
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                System.out.println("ID: " + productId);
                System.out.println("Số lượng: " + quantity);
                System.out.println("------------------------");

            }
            System.out.print("Nhập họ tên: ");
            String orderName = Validate.validateString();
            System.out.print("Nhập số điện thoại: ");
            String orderPhoneNumber = Validate.validatePhone();
            System.out.print("Nhập địa chỉ: ");
            String orderAddress = Validate.validateString();


            System.out.println(YELLOW + "Bạn có muốn đặt hàng? (1: Đồng ý, 0: Hủy bỏ)" + RESET);
            int choice = Validate.validatePositiveInt();
            if (choice == 1) {
                // Tạo đơn hàng
                Order order = new Order();
                order.setUserId(userLogin.getId());
                order.setOrderId(orderService.getNewId());
                order.setOrdersDetails(products);
                order.setOrderStatus(OrderStatus.WAITING);
                order.setName(orderName);
                order.setPhoneNumber(orderPhoneNumber);
                order.setAddress(orderAddress);
                order.setTotal(total);

                // Lưu đơn hàng vào cơ sở dữ liệu
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
                cart.setProducts(new HashMap<>());
                cartService.update(cart);

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

        if (cart == null) {
            System.out.println(RED + "Giỏ hàng trống" + RESET);
            return;
        }

        Map<Integer, Integer> products = cart.getProducts();

        if (products.isEmpty()) {
            System.out.println(RED + "Giỏ hàng trống" + RESET);
        } else {
            double total = 0;
            System.out.println(YELLOW + "Danh sách sản phẩm trong giỏ hàng: " + RESET);
            System.out.println("-------------------------------------------------------");
            System.out.printf("| %-4s | %-20s | %-10s | %-8s |\n", "ID", "Tên sản phẩm", "Giá tiền", "Số lượng");
            System.out.println("-------------------------------------------------------");
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Lấy thông tin sản phẩm từ productService bằng productId
                Product product = productService.findByID(productId);

                String formattedPrice = formatCurrency(product.getUnitPrice());

                System.out.printf("| %-4s | %-20s | %-10s | %-8s |\n", productId, product.getProductName(), formattedPrice, quantity);

                // Tính tổng tiền
                double productTotal = product.getUnitPrice() * quantity;
                total += productTotal;
            }
            System.out.println("-------------------------------------------------------");
            System.out.println("Tổng tiền: " + formatCurrency(total));
            System.out.println("--------------------");
        }
    }


    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " đ";
    }

    private void orderHistory() {
        List<Order> oders = orderService.findAll();

        if (oders == null || oders.isEmpty()) {
            System.out.println(RED + "Không có đơn hàng nào" + RESET);
            return;
        }

        List<Order> orderUser = oders.stream().filter(o -> o.getUserId() == userLogin.getId()).collect(Collectors.toList());
        if (orderUser == null || orderUser.isEmpty()) {
            System.out.println(RED + "Không có đơn hàng nào" + RESET);
            return;
        }

        System.out.println("__________________________________");
        for (Order order : oders) {
            if (order.getUserId() == userLogin.getId()) {
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
            }
            System.out.println("__________________________________");
        }

        System.out.print(YELLOW + "Mời nhập ID để chọn đơn hàng cần huỷ, Hoặc nhập 0 để quay lại: " + RESET);
        int orderId = Validate.validatePositiveInt();
        Order order = orderService.findByID(orderId);

        if (orderId == 0) {
            return;
        }

        if (order == null) {
            System.out.println(RED + "Không tồn tại trong danh sách" + RESET);
            return;
        }

        if (order.getOrderStatus() == OrderStatus.WAITING) {
            System.out.println("1. Huỷ đơn hàng");
            System.out.println("O. Quay lại");
            int choiceCheck = Validate.validatePositiveInt();
            switch (choiceCheck) {
                case 1:
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
        } else if (order.getOrderStatus() == OrderStatus.CANCEL) {
            System.out.println(YELLOW + "Đơn hàng đã ở trạng thái bị huỷ" + RESET);
        } else if (order.getOrderStatus() == OrderStatus.CONFIRM) {
            System.out.println(YELLOW + "Đơn hàng đang ở trạng thái xác nhận, không thể huỷ" + RESET);
        } else {
            System.out.println(RED + "Không hợp lệ" + RESET);
        }
    }
}
