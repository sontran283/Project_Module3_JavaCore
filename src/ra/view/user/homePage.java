package ra.view.user;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Product;
import ra.service.ICatalogService;
import ra.service.IProductService;
import ra.service.impl.CatalogServiceIMPL;
import ra.service.impl.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ra.config.Color.*;

public class homePage {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();


    public void home() {
        int choice;
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                        --->> HOME PAGE <<---                         |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                1. Tìm kiếm sản phẩm theo tên                         |");
            System.out.println("|                2. Sản phẩm nổi bật theo giá giảm dần                 |");
            System.out.println("|                3. Danh sách sản phẩm                                 |");
            System.out.println("|                4. Thêm vào giỏ hàng                                  |");
            System.out.println("|                5. Sắp xếp theo giá tăng/giảm dần                     |");
            System.out.println("|                0. Quay lại                                           |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Validate.validateInt();
            switch (choice) {
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
        System.out.println("Nhập tên sản phẩm muốn tìm: ");
        String search = Config.scanner().nextLine().toLowerCase();
        int count = 0;
        System.out.println(YELLOW + "Danh sách sản phẩm cần tìm: " + RESET);
        for (Product product : productService.findAll()) {
            if (product.getProductName().toLowerCase().contains(search)) {
                System.out.println(product);
                count++;
            }
        }
        System.out.printf("Tìm thấy %d sản phẩm theo từ khoá vừa nhập ", count);
        System.out.println();
    }

    private void showHotProduct() {
        List<Product> hotProducts = productService.findAll();
        hotProducts.sort(Comparator.comparing(product1 -> -product1.getUnitPrice()));
        int count = 0;
        System.out.println("Sản phẩm nổi bật theo giá giảm dần: ");
        for (Product product : hotProducts) {
            System.out.println(product);
            count++;
            if (count == 5) {
                break;
            }
        }
    }

    private void listProduct() {
        System.out.println("Danh sách sản phẩm: ");
        for (Product product : productService.findAll()) {
            if (product.isStatus()) {
                System.out.println(product);
            }
        }
    }

    private void addToCart() {

    }

    private void sortProduct() {
        System.out.println("1. Sắp xếp theo giá tăng dần");
        System.out.println("2. Sắp xếp theo giá giảm dần");
        System.out.println("Mời lựa chọn: ");
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
        for (Product product : productService.findAll()) {
            if (product.isStatus()) {
                System.out.println(product);
            }
        }
    }
}
