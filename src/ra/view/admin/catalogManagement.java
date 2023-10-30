package ra.view.admin;

import ra.config.Config;
import ra.constant.RoleName;
import ra.model.Catalog;
import ra.model.Users;
import ra.service.*;
import ra.service.impl.*;

import java.util.List;

import static ra.config.Color.*;


public class catalogManagement {

    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();

    public void menuCatalog() {
        int choice;
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                      --->> CATALOG MANAGER <<---                     |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Thêm mới danh mục                              |");
            System.out.println("|                    2. Hiển thị danh sách danh mục                    |");
            System.out.println("|                    3. Tìm kiếm danh mục theo tên                     |");
            System.out.println("|                    4. Chỉnh sửa thông tin danh mục                   |");
            System.out.println("|                    5. Xoá danh mục theo mã ID                        |");
            System.out.println("|                    6. Ẩn danh mục theo mã ID                         |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    addCatalog();
                    break;
                case 2:
                    showCatalog();
                    break;
                case 3:
                    searchCatalog();
                    break;
                case 4:
                    editCatalog();
                    break;
                case 5:
                    deleteCatalog();
                    break;
                case 6:
                    hideCatalog();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, mời chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void addCatalog() {
        System.out.println("Nhập số lượng danh mục cần thêm: ");
        int n = Config.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Danh mục thứ " + (i + 1) + " ");
            Catalog catalog = new Catalog();

            System.out.println("Nhập tên danh mục");
            catalog.setCatalogName(Config.scanner().nextLine());

            System.out.println("Nhập mô tả danh mục");
            catalog.setDescription(Config.scanner().nextLine());

            catalogService.save(catalog);
        }
    }

    private void showCatalog() {
        System.out.println("Danh sách các danh mục: ");
        List<Catalog> catalogList = catalogService.findAll();
        for (Catalog subject : catalogList) {
            System.out.println(subject);
        }
    }

    private void searchCatalog() {
        System.out.println("Mời nhập tên danh mục cần tìm: ");
        String searchName = Config.scanner().nextLine().toLowerCase();
        int count = 0;
        System.out.println("Danh sách danh mục cần tìm kiếm: ");
        for (Catalog catalog : catalogService.findAll()) {
            if (catalog.getCatalogName().toLowerCase().contains(searchName)) {
                System.out.println(catalog);
                count++;
            }
        }
        System.out.printf("Tìm thấy %d danh mục theo từ khoá vừa nhập ", count);
        System.out.println();
    }

    private void editCatalog() {
        System.out.println("Nhập ID danh mục cần thay đổi thông tin: ");
        int idEdit = Config.validateInt();
        Catalog catalogEdit = catalogService.findByID(idEdit);
        if (catalogEdit == null) {
            System.out.println("Không tìm thất danh mục có ID: " + idEdit);
        } else {
            System.out.println(catalogEdit);
            System.out.println("Nhập tên mới: ");
            catalogEdit.setCatalogName(Config.scanner().nextLine());

            System.out.println("Nhập mô tả mới: ");
            catalogEdit.setDescription(Config.scanner().nextLine());

            catalogService.update(catalogEdit);
        }
    }

    private void deleteCatalog() {
        System.out.println("Mời nhập ID danh mục cần xoá: ");
        int idDelete = Config.validateInt();
        Catalog catalogDelete = catalogService.findByID(idDelete);
        if (catalogDelete == null) {
            System.out.println(RED + "Không tồn tại danh mục theo ID vừa nhập" + RESET);
        } else {
            catalogService.delete(idDelete);
            System.out.println(YELLOW + "Xóa danh mục thành công!" + RESET);
        }
    }

    private void hideCatalog() {

    }
}
