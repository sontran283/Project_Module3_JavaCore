package ra.view.admin;

import ra.config.Config;
import ra.model.Product;
import ra.service.*;
import ra.service.impl.*;

public class productManagement {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();

    public void menuProduct() {
        int choice;
        do {
            System.out.println(".======================================================================.");
            System.out.println("|                      --->> PRODUCT MANAGER <<---                     |");
            System.out.println("|======================================================================|");
            System.out.println("|                   1. Thêm mới sản phẩm                               |");
            System.out.println("|                   2. Hiển thị danh sách sản phẩm                     |");
            System.out.println("|                   3. Chỉnh sửa thông tin sản phẩm                    |");
            System.out.println("|                   4. Xoá sản phẩm theo mã sản phẩm                   |");
            System.out.println("|                   5. Tìm kiếm sản phẩm theo tên                      |");
            System.out.println("|                   0. Quay lại                                        |");
            System.out.println(".======================================================================.");
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
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
                case 0:
                    return;
                default:
                    System.out.println("___ Lựa chọn không hợp lệ, mời chọn lại ___");
                    break;
            }
        } while (true);
    }

    private void addProduct() {
        System.out.println("Nhập số lượng sản phẩm cần thêm: ");
        int n = Config.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ: " + (i + 1) + ", ");
            Product product = new Product();

            // tên sp
            System.out.println("Nhập tên sản phẩm: ");
            product.setProductName(Config.scanner().nextLine());

            // danh mục sp
            System.out.println("Danh mục sản phẩm có thể chọn: ");
            for (int j = 0; j < catalogService.findAll().size(); j++) {
                System.out.println((j + 1) + ", " + catalogService.findAll().get(j).getCatalogName());
            }
            System.out.println("Mời lựa chọn danh mục sản phẩm: ");
            while (true) {
                int choice = Config.validateInt();
                if (choice >= 1 && choice <= catalogService.findAll().size()) {
                    product.setCatalog(catalogService.findAll().get(choice - 1));
                    break;
                } else {
                    System.out.println("___ Không có danh mục theo lựa chọn, mời nhập lại ___");
                }
            }

            System.out.println("Nhập mô tả sản phẩm: ");
            product.setDescription(Config.scanner().nextLine());

            System.out.println("Nhập đơn giá: ");
            product.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));

            System.out.println("Nhập số lượng trong kho: ");
            product.setStock(Integer.parseInt(Config.scanner().nextLine()));

            productService.save(product);
        }
    }

    private void showProduct() {
        System.out.println("Danh sách sản phẩm: ");
        for (Product product : productService.findAll()) {
            System.out.println(product);
        }
    }

    private void editProduct() {
        System.out.println("Nhập ID sản phẩm cần thay đổi: ");
        int idEdit = Config.validateInt();
        Product productedit = productService.findByID(idEdit);

        if (productedit != null) {
            System.out.println("1_ Sửa tên sản phẩm");
            System.out.println("2_ Sửa danh mục sản phẩm");
            System.out.println("3_ Sửa mô tả sản phẩm");
            System.out.println("4_ Sửa đơn giá");
            System.out.println("5_ Sửa số lượng trong kho");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    System.out.println("Nhập tên mới: ");
                    productedit.setProductName(Config.scanner().nextLine());
                    System.out.println("Sửa tên thành công!");
                    break;
                case 2:
                    System.out.println("Danh sách danh mục sản phẩm cần chọn: ");
                    for (int j = 0; j < catalogService.findAll().size(); j++) {
                        System.out.println((j + 1) + ", " + catalogService.findAll().get(j).getCatalogName());
                    }
                    System.out.println("Mời chọn danh mục mới: ");
                    while (true) {
                        int choiceEdit = Config.validateInt();
                        if (choiceEdit >= 1 && choiceEdit <= catalogService.findAll().size()) {
                            productedit.setCatalog(catalogService.findAll().get(choiceEdit - 1));
                            break;
                        } else {
                            System.out.println("___ Không có danh mục theo lựa chọn, mời nhập lại ___");
                        }
                    }
                    System.out.println("Sửa danh mục thành công!");
                    break;
                case 3:
                    System.out.println("Nhập mô tả sản phẩm mới: ");
                    productedit.setDescription(Config.scanner().nextLine());
                    System.out.println("Sửa mô tả thành công!");
                    break;
                case 4:
                    System.out.println("Nhập đơn giá mới: ");
                    productedit.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));
                    System.out.println("Sửa đơn giá thành công!");
                    break;
                case 5:
                    System.out.println("Nhập mới số lượng hàng tồn kho: ");
                    productedit.setStock(Integer.parseInt(Config.scanner().nextLine()));
                    System.out.println("Sửa số lượng thành công!");
                    break;
                default:
                    System.err.println("___ Nhập không hợp lệ, mời nhập lại ___");
                    break;
            }
        } else {
            System.out.println("___ Không tìm thấy sản phẩm có ID: " + idEdit + " ___");
        }
    }

    private void deleteProduct() {
        System.out.println("Nhập ID sản phẩm cần xoá: ");
        int idDelete = Config.validateInt();
        boolean check = true;
        for (Product product : productService.findAll()) {
            if (product.getProductId() == idDelete) {
                productService.delete(idDelete);
                System.out.println("Xoá thành công!");
                check = false;
            }
        }
        if (check) {
            System.out.println("___ Không tìm thấy sản phẩm có ID: " + idDelete + " ___");
        }
    }

    private void searchProduct() {
        System.out.println("Nhập tên sản phẩm muốn tìm: ");
        String search = Config.scanner().nextLine();
        int count = 0;
        System.out.println("Danh sách sản phẩm cần tìm: ");
        for (Product product : productService.findAll()) {
            if (product.getProductName().contains(search)) {
                System.out.println(product);
                count++;
            }
        }
        System.out.printf("Tìm thấy %d sản phẩm theo từ khoá vừa nhập ", count);
    }
}
