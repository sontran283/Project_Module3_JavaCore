package ra.view.user;

import ra.config.Validate;
import ra.model.Cart;
import ra.model.Product;
import ra.service.*;
import ra.service.impl.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ra.config.Color.*;

public class homePage {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    ICartService cartService = new CartServiceIMPL();

    Cart cart = new Cart();

    public void home() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                        --->> HOME PAGE <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                1. Tìm kiếm sản phẩm theo tên                         |");
            System.out.println("|                2. 10 Sản phẩm nổi bật theo tên (alpha-b)             |");
            System.out.println("|                3. Danh sách sản phẩm                                 |");
            System.out.println("|                4. Thêm vào giỏ hàng                                  |");
            System.out.println("|                5. Sắp xếp theo giá tăng/giảm dần                     |");
            System.out.println("|                0. Quay lại                                           |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validateInt()) {
                case 1:
                    searchProduct();
                    break;
                case 2:
                    showHotProduct();
                    break;
                case 3:
                    listProduct();
                    break;
                case 4:
                    addToCart();
                    break;
                case 5:
                    sortProduct();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void searchProduct() {
        System.out.println(YELLOW + "Danh sách sản phẩm: " + RESET);
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        for (Product product : productService.findAll()) {
            if (product.isStatus()) {
                System.out.println(product);
            }
        }

        System.out.print("Nhập tên sản phẩm muốn tìm: ");
        String search = Validate.validateString().toLowerCase();

        int count = 0;
        boolean found = false;
        System.out.println(YELLOW + "Danh sách sản phẩm cần tìm: " + RESET);
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");

        for (Product product : productService.findAll()) {
            if (product.getProductName().toLowerCase().contains(search) && product.isStatus()) {
                System.out.println(product);
                count++;
                found = true;
            }
        }
        if (!found) {
            System.out.println(RED + "Không tìm thấy sản phẩm theo tên vừa nhập" + RESET);
        } else {
            System.out.printf("Tìm thấy %d sản phẩm theo từ khoá vừa nhập", count, search);
        }
        System.out.println();
    }

    private void showHotProduct() {
        List<Product> hotProducts = new ArrayList<>(productService.findAll());

        System.out.println(YELLOW + "10 sản phẩm nổi bật theo thứ tự alpha-b:" + RESET);
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        hotProducts.sort(Comparator.comparing(Product::getProductName));

        int count = 0;
        for (Product product : hotProducts) {
            System.out.println(product);
            count++;
            if (count == 10) {
                break;
            }
        }
    }

    private void listProduct() {
        System.out.println(YELLOW + "Danh sách sản phẩm: " + RESET);
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        for (Product product : productService.findAll()) {
            if (product.isStatus()) {
                System.out.println(product);
            }
        }
    }

    private void addToCart() {
        // Hiển thị danh sách sản phẩm
        System.out.println("Danh sách sản phẩm:");
        System.out.println("_____________________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_____________________________________________________________________________________________________________________________");
        List<Product> products = productService.findAll();
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }

        // Yêu cầu người dùng chọn sản phẩm
        System.out.print("Mời chọn sản phẩm (1_" + products.size() + "), ");
        int choice = Validate.validateInt();

        // Lấy sản phẩm đã chọn
        Product selectedProduct = products.get(choice - 1);

        // Yêu cầu người dùng nhập số lượng
        System.out.print("Nhập số lượng muốn mua, ");
        int quantity = Validate.validateInt();

        // Kiểm tra số lượng có hợp lệ
        if (quantity <= 0) {
            System.out.println(RED + "Số lượng không hợp lệ. Không thêm được sản phẩm vào giỏ hàng" + RESET);
            return;
        }

        // Kiểm tra số lượng sản phẩm còn đủ để thêm vào giỏ hàng
        if (selectedProduct.getStock() < quantity) {
            System.out.println(RED + "Số lượng sản phẩm không đủ. Không thêm được sản phẩm vào giỏ hàng" + RESET);
            return;
        }

        // Thêm sản phẩm vào giỏ hàng
        cart.addProduct(selectedProduct.getProductId(), quantity);

        // Cập nhật số lượng sản phẩm trong kho
        selectedProduct.setStock(selectedProduct.getStock() - quantity);

        System.out.println(YELLOW + "Sản phẩm đã được thêm vào giỏ hàng" + RESET);
    }

    private void sortProduct() {
        System.out.println("1. Sắp xếp theo giá tăng dần");
        System.out.println("2. Sắp xếp theo giá giảm dần");

        int sortChoice = Validate.validateInt();
        if (sortChoice == 1) {
            productService.findAll().sort(Comparator.comparing(Product::getUnitPrice));
            System.out.println(YELLOW + "Đã sắp xếp giá tăng dần thành công" + RESET);
        } else if (sortChoice == 2) {
            productService.findAll().sort(Comparator.comparing(Product::getUnitPrice).reversed());
            System.out.println(YELLOW + "Đã sắp xếp giá giảm dần thành công" + RESET);
        } else {
            System.out.println(RED + "Lựa chọn không hợp lệ, không thực hiện sắp xếp" + RESET);
        }
        System.out.println(YELLOW + "Danh sách sản phẩm sau khi sắp xếp: " + RESET);
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        for (Product product : productService.findAll()) {
            if (product.isStatus()) {
                System.out.println(product);
            }
        }
    }
}
