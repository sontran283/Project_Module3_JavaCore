package ra.view.admin;

import ra.config.Validate;
import ra.model.Catalog;
import ra.model.Product;
import ra.service.*;
import ra.service.impl.*;

import static ra.config.Color.*;


public class productManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();

    public void menuProduct() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                      --->> PRODUCT MANAGER <<---                     |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                   1. Thêm mới sản phẩm                               |");
            System.out.println("|                   2. Hiển thị danh sách sản phẩm                     |");
            System.out.println("|                   3. Chỉnh sửa thông tin sản phẩm                    |");
            System.out.println("|                   4. Xoá sản phẩm theo mã sản phẩm                   |");
            System.out.println("|                   5. Tìm kiếm sản phẩm theo tên                      |");
            System.out.println("|                   6. Ẩn/Mở sản phẩm theo mã sản phẩm                 |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validateInt()) {
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
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void addProduct() {
        System.out.println("Nhập số lượng sản phẩm cần thêm: ");
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ: " + (i + 1) + ", ");
            Product product = new Product();

            // tên sp
            System.out.println("Nhập tên sản phẩm: ");
            product.setProductName(Validate.validateString());
            for (Product checkName : productService.findAll()) {
                if (checkName.getProductName().equalsIgnoreCase(product.getProductName())) {
                    System.out.println(RED + "Sản phẩm đã tồn tại, mời nhập lại" + RESET);
                    return;
                }
            }

            // danh mục sp
            System.out.println("Danh mục sản phẩm có thể chọn: ");
            for (int j = 0; j < catalogService.findAll().size(); j++) {
                Catalog catalog = catalogService.findAll().get(j);
                if (catalog.isStatus()) {
                    System.out.println((j + 1) + ", " + catalog.getCatalogName());
                }
            }
            System.out.println("Mời lựa chọn danh mục sản phẩm: ");
            while (true) {
                int choice = Validate.validateInt();
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

            System.out.println("Nhập mô tả sản phẩm: ");
            product.setDescription(Validate.validateString());

            System.out.println("Nhập đơn giá: ");
            product.setUnitPrice(Double.parseDouble(Validate.validateString()));

            System.out.println("Nhập số lượng trong kho: ");
            product.setStock(Integer.parseInt(Validate.validateString()));

            System.out.println(YELLOW + "Thêm sản phẩm thành công" + RESET);
            productService.save(product);
        }
    }

    private void showProduct() {
        System.out.println("1. Tất cả sản phẩm");
        System.out.println("2. Sản phẩm mở bán");
        System.out.println("3. Sản phẩm không mở bán");
        int choiceCheck = Validate.validateInt();

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
        } else {
            System.out.println("Không hợp lệ");
        }
    }

    private void editProduct() {
        System.out.println("Nhập ID sản phẩm cần thay đổi: ");
        int idEdit = Validate.validateInt();
        Product productedit = productService.findByID(idEdit);

        if (productedit != null) {
            System.out.println("1. Sửa tên sản phẩm");
            System.out.println("2. Sửa danh mục sản phẩm");
            System.out.println("3. Sửa mô tả sản phẩm");
            System.out.println("4. Sửa đơn giá");
            System.out.println("5. Sửa số lượng trong kho");
            System.out.println("0. Quay lại");
            int choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.println("Nhập tên mới: ");
                    productedit.setProductName(Validate.validateString());
                    System.out.println(YELLOW + "Sửa tên thành công" + RESET);
                    break;
                case 2:
                    System.out.println("Danh sách danh mục sản phẩm cần chọn: ");
                    for (int j = 0; j < catalogService.findAll().size(); j++) {
                        System.out.println((j + 1) + ", " + catalogService.findAll().get(j).getCatalogName());
                    }
                    System.out.println("Mời chọn danh mục mới: ");
                    while (true) {
                        int choiceEdit = Validate.validateInt();
                        if (choiceEdit >= 1 && choiceEdit <= catalogService.findAll().size()) {
                            productedit.setCatalog(catalogService.findAll().get(choiceEdit - 1));
                            break;
                        } else {
                            System.out.println(RED + "Không có danh mục theo lựa chọn, mời nhập lại" + RESET);
                        }
                    }
                    System.out.println(YELLOW + "Sửa danh mục thành công" + RESET);
                    break;
                case 3:
                    System.out.println("Nhập mô tả sản phẩm mới: ");
                    productedit.setDescription(Validate.validateString());
                    System.out.println(YELLOW + "Sửa mô tả thành công" + RESET);
                    break;
                case 4:
                    System.out.println("Nhập đơn giá mới: ");
                    productedit.setUnitPrice(Double.parseDouble(Validate.validateString()));
                    System.out.println(YELLOW + "Sửa đơn giá thành công" + RESET);
                    break;
                case 5:
                    System.out.println("Nhập mới số lượng hàng tồn kho: ");
                    productedit.setStock(Integer.parseInt(Validate.validateString()));
                    System.out.println(YELLOW + "Sửa số lượng thành công" + RESET);
                    break;
                case 0:
                    return;
                default:
                    System.err.println(RED + "Nhập không hợp lệ, mời nhập lại" + RESET);
                    break;
            }
        } else {
            System.out.println(RED + "Không tìm thấy sản phẩm có ID: " + idEdit + " ___" + RESET);
        }
    }

    private void deleteProduct() {
        System.out.println("Nhập ID sản phẩm cần xoá: ");
        int idDelete = Validate.validateInt();
        Product productDelete = productService.findByID(idDelete);
        if (productDelete != null) {
            productService.delete(idDelete);
            System.out.println(YELLOW + "Xoá thành công" + RESET);
            return;
        }
        System.out.println(RED + "Không tìm thấy sản phẩm có ID: " + idDelete + " ___" + RESET);
    }

    private void searchProduct() {
        System.out.println("Nhập tên sản phẩm muốn tìm: ");
        String search = Validate.validateString().toLowerCase();
        int count = 0;
        System.out.println("Danh sách sản phẩm cần tìm: ");
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
        int productId = Validate.validateInt();
        Product product = productService.findByID(productId);

        if (product != null) {
            System.out.println("1. Ẩn sản phẩm");
            System.out.println("2. Mở lại sản phẩm");
            int choice = Validate.validateInt();

            switch (choice) {
                case 1:
                    if (product.isStatus()) {
                        product.setStatus(false);
                        productService.update(product);
                        System.out.println(YELLOW + "Sản phẩm đã được ẩn thành công" + RESET);
                    } else {
                        System.out.println(YELLOW + "Sản phẩm đã được ẩn trước đó" + RESET);
                    }
                    break;
                case 2:
                    if (!product.isStatus()) {
                        product.setStatus(true);
                        productService.update(product);
                        System.out.println(YELLOW + "Sản phẩm đã được mở lại thành công" + RESET);
                    } else {
                        System.out.println(YELLOW + "Sản phẩm đã được mở lại trước đó" + RESET);
                    }
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ" + RESET);
                    break;
            }
        } else {
            System.out.println(RED + "Không tìm thấy sản phẩm có mã: " + productId + RESET);
        }
    }
}
