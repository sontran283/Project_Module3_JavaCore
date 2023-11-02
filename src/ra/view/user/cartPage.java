package ra.view.user;

import ra.config.Validate;
import ra.model.Cart;
import ra.service.*;
import ra.service.impl.*;

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

    }

    private void deleteProduct() {

    }

    private void order() {

    }

    private void showListOrder() {
        Cart cart = new Cart();
        System.out.println(cart);
    }
}
