package ra.view.admin;

import ra.config.Validate;
import ra.model.Catalog;
import ra.model.Product;
import ra.service.*;
import ra.service.impl.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static ra.config.Color.*;


public class productManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();
    // Khởi tạo danh sách liên kết để lưu trữ các sản phẩm
    LinkedList<Product> productList = new LinkedList<>();

    public void menuProduct() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                    --->> QUẢN LÝ SẢN PHẨM <<---                      |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                   1. Thêm mới sản phẩm                               |");
            System.out.println("|                   2. Hiển thị danh sách sản phẩm                     |");
            System.out.println("|                   3. Chỉnh sửa thông tin sản phẩm                    |");
            System.out.println("|                   4. Xoá sản phẩm theo mã sản phẩm                   |");
            System.out.println("|                   5. Tìm kiếm sản phẩm theo tên sản phẩm             |");
            System.out.println("|                   6. Ẩn/Mở sản phẩm theo mã sản phẩm                 |");
            System.out.println("|                   7. Sắp xếp 2 sản phẩm theo giá cao nhất            |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validatePositiveInt()) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    showProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProduct();
                    break;
                case 6:
                    hideOpenProduct();
                    break;
                case 7:
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

    private void sortProduct() {
        System.out.println("Danh sach san pham");
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        Collections.sort(productService.findAll(), new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return (int) -(o1.getUnitPrice() - o2.getUnitPrice());
            }
        });

        for (int i = 0; i < 2; i++) {
            System.out.println(productService.findAll().get(i));
        }
    }

    private void addProduct() {
        System.out.print("Nhập số lượng sản phẩm cần thêm: ");
        int number = Validate.validatePositiveInt();
        for (int i = 0; i < number; i++) {
            System.out.println("Sản phẩm thứ: " + (i + 1) + ", ");
            Product product = new Product();

            while (true) {
                // tên sp
                System.out.print("Nhập tên sản phẩm: ");
                product.setProductName(Validate.validateString());
                boolean check = false;

                for (Product checkName : productService.findAll()) {
                    if (checkName.getProductName().equalsIgnoreCase(product.getProductName())) {
                        System.out.println(RED + "Sản phẩm đã tồn tại" + RESET);
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    // danh mục sp
                    System.out.println(YELLOW + "Danh mục sản phẩm có thể chọn: " + RESET);
                    for (int j = 0; j < catalogService.findAll().size(); j++) {
                        Catalog catalog = catalogService.findAll().get(j);
                        if (catalog.isStatus()) {
                            System.out.println((j + 1) + ", " + catalog.getCatalogName());
                        }
                    }
                    System.out.print("Mời lựa chọn danh mục sản phẩm: ");
                    while (true) {
                        int choice = Validate.validatePositiveInt();
                        if (choice >= 1 && choice <= catalogService.findAll().size()) {
                            Catalog selectedCatalog = catalogService.findAll().get(choice - 1);
                            if (selectedCatalog.isStatus()) {
                                product.setCatalog(selectedCatalog);
                                break;
                            } else {
                                System.out.println(RED + "Danh mục đã bị ẩn, mời chọn lại" + RESET);
                            }
                        } else {
                            System.out.println(RED + "Lựa chọn không hợp lệ, mời nhập lại" + RESET);
                        }
                    }

                    System.out.print("Nhập mô tả sản phẩm: ");
                    product.setDescription(Validate.validateString());

                    System.out.print("Nhập đơn giá: ");
                    product.setUnitPrice(Validate.validatePositiveDouble());

                    System.out.print("Nhập số lượng trong kho: ");
                    product.setStock(Validate.validatePositiveInt());

                    System.out.println(YELLOW + "Thêm sản phẩm thành công" + RESET);
                    productService.save(product);
                    break;
                }
            }
        }
        System.out.println(BLUE + "Đã thêm " + number + " sản phẩm" + RESET);
    }

    private void showProduct() {
        System.out.println("1. Tất cả sản phẩm");
        System.out.println("2. Sản phẩm mở bán");
        System.out.println("3. Sản phẩm không mở bán");
        System.out.println("0. Quay lại");
        int choiceCheck = Validate.validatePositiveInt();

        if (choiceCheck == 1) {
            System.out.println(YELLOW + "Tất cả sản phẩm" + RESET);
            System.out.println("_______________________________________________________________________________________________________________________");
            System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                    "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
            System.out.println("_______________________________________________________________________________________________________________________");
            for (Product product : productService.findAll()) {
                System.out.println(product);
            }
        } else if (choiceCheck == 2) {
            System.out.println(YELLOW + "Sản phẩm mở bán" + RESET);
            System.out.println("_______________________________________________________________________________________________________________________");
            System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                    "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
            System.out.println("_______________________________________________________________________________________________________________________");
            for (Product product : productService.findAll()) {
                if (product.isStatus()) {
                    System.out.println(product);
                }
            }
        } else if (choiceCheck == 3) {
            System.out.println(YELLOW + "Sản phẩm không mở bán" + RESET);
            System.out.println("_______________________________________________________________________________________________________________________");
            System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                    "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
            System.out.println("_______________________________________________________________________________________________________________________");
            for (Product product : productService.findAll()) {
                if (!product.isStatus()) {
                    System.out.println(product);
                }
            }
        } else if (choiceCheck == 0) {
            return;
        } else {
            System.out.println(RED + "Không hợp lệ, mời nhập lại" + YELLOW);
        }
    }

    private void editProduct() {
        System.out.print("Nhập ID sản phẩm cần thay đổi: ");
        int idEdit = Validate.validatePositiveInt();
        Product productedit = productService.findByID(idEdit);

        if (productedit != null) {
            System.out.println("1. Sửa tên sản phẩm");
            System.out.println("2. Sửa danh mục sản phẩm");
            System.out.println("3. Sửa mô tả sản phẩm");
            System.out.println("4. Sửa đơn giá");
            System.out.println("5. Sửa số lượng trong kho");
            System.out.println("0. Quay lại");
            int choice = Validate.validatePositiveInt();
            switch (choice) {
                case 1:
                    System.out.print("Nhập tên mới: ");
                    productedit.setProductName(Validate.validateString());
                    productService.update(productedit);
                    System.out.println(YELLOW + "Sửa tên thành công" + RESET);
                    break;
                case 2:
                    System.out.println("Danh sách danh mục sản phẩm cần chọn: ");
                    for (int j = 0; j < catalogService.findAll().size(); j++) {
                        System.out.println((j + 1) + ", " + catalogService.findAll().get(j).getCatalogName());
                    }
                    System.out.print("Mời chọn danh mục mới: ");
                    while (true) {
                        int choiceEdit = Validate.validatePositiveInt();
                        if (choiceEdit >= 1 && choiceEdit <= catalogService.findAll().size()) {
                            productedit.setCatalog(catalogService.findAll().get(choiceEdit - 1));
                            break;
                        } else {
                            System.out.println(RED + "Không có danh mục theo lựa chọn" + RESET);
                        }
                    }
                    productService.update(productedit);
                    System.out.println(YELLOW + "Sửa danh mục thành công" + RESET);
                    break;
                case 3:
                    System.out.print("Nhập mô tả sản phẩm mới: ");
                    productedit.setDescription(Validate.validateString());
                    productService.update(productedit);
                    System.out.println(YELLOW + "Sửa mô tả thành công" + RESET);
                    break;
                case 4:
                    System.out.print("Nhập đơn giá mới: ");
                    productedit.setUnitPrice(Validate.validatePositiveDouble());
                    productService.update(productedit);
                    System.out.println(YELLOW + "Sửa đơn giá thành công" + RESET);
                    break;
                case 5:
                    System.out.print("Nhập mới số lượng hàng tồn kho: ");
                    productedit.setStock(Validate.validatePositiveInt());
                    productService.update(productedit);
                    System.out.println(YELLOW + "Sửa số lượng thành công" + RESET);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(RED + "Không hợp lệ, mời nhập lại" + RESET);
                    break;
            }
        } else {
            System.out.println(RED + "Không tìm thấy sản phẩm có ID: " + idEdit + RESET);
        }
    }

    private void deleteProduct() {
        System.out.print(YELLOW + "Nhập ID sản phẩm cần xoá, Hoặc nhập 0 để quay lại: " + RESET);
        int idDelete = Validate.validatePositiveInt();
        Product productDelete = productService.findByID(idDelete);

        if (idDelete == 0) {
            return;
        }

        if (productDelete != null) {
            productService.delete(idDelete);
            System.out.println(YELLOW + "Xoá thành công" + RESET);
            return;
        }
        System.out.println(RED + "Không tìm thấy sản phẩm có ID: " + idDelete + RESET);
    }

    private void searchProduct() {
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        for (Product product : productService.findAll()) {
            System.out.println(product);
        }

        System.out.print("Nhập tên sản phẩm muốn tìm: ");
        String search = Validate.validateString().toLowerCase();

        int count = 0;
        System.out.println(YELLOW + "Danh sách sản phẩm cần tìm: " + RESET);
        System.out.println("_______________________________________________________________________________________________________________________");
        System.out.printf("%-15s %-20s %-20s %-20s %-10s %-20s %-15s%n",
                "Product ID", "Product Name", "Description", "Unit Price", "Stock", "Catalog Name", "Status");
        System.out.println("_______________________________________________________________________________________________________________________");
        for (Product product : productService.findAll()) {
            if (product.getProductName().toLowerCase().contains(search)) {
                System.out.println(product);
                count++;
            }
        }
        System.out.printf("Tìm thấy %d sản phẩm theo từ khoá vừa nhập ", count);
        System.out.println();
    }

    private void hideOpenProduct() {
        System.out.println("Nhập ID sản phẩm cần ẩn/mở lại: ");
        int productId = Validate.validatePositiveInt();
        Product product = productService.findByID(productId);

        if (product != null) {
            System.out.println("1. Ẩn sản phẩm");
            System.out.println("2. Mở lại sản phẩm");
            System.out.println("0. Quay lại");
            int choice = Validate.validatePositiveInt();

            switch (choice) {
                case 1:
                    if (product.isStatus()) {
                        product.setStatus(false);
                        productService.update(product);
                        System.out.println(YELLOW + "Sản phẩm đã ẩn thành công" + RESET);
                    } else {
                        System.out.println(YELLOW + "Sản phẩm đang ở trạng thái ẩn" + RESET);
                    }
                    break;
                case 2:
                    if (!product.isStatus()) {
                        product.setStatus(true);
                        productService.update(product);
                        System.out.println(YELLOW + "Sản phẩm đã mở thành công" + RESET);
                    } else {
                        System.out.println(YELLOW + "Sản phẩm đang ở trạng thái mở" + RESET);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ" + RESET);
                    break;
            }
        } else {
            System.out.println(RED + "Không tìm thấy sản phẩm có mã: " + productId + RESET);
        }
    }
}
