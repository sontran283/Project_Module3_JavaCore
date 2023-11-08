package ra.view.admin;

import ra.config.Validate;
import ra.model.Catalog;
import ra.model.Product;
import ra.service.*;
import ra.service.impl.*;

import static ra.config.Color.*;

public class catalogManagement {

    ICatalogService catalogService = new CatalogServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrdersDetailService ordersDetailService = new OrdersDetailServiceIMPL();


    public void menuCatalog() {
        do {
            System.out.println(BLUE + ".======================================================================.");
            System.out.println("|                     --->> QUẢN LÝ DANH MỤC <<---                     |");
            System.out.println("|======================================================================|");
            System.out.println(YELLOW + "|                    1. Thêm mới danh mục                              |");
            System.out.println("|                    2. Hiển thị danh sách danh mục                    |");
            System.out.println("|                    3. Tìm kiếm danh mục theo tên                     |");
            System.out.println("|                    4. Chỉnh sửa thông tin danh mục                   |");
            System.out.println("|                    5. Xoá danh mục theo mã ID                        |");
            System.out.println("|                    6. Ẩn/Mở danh mục theo mã ID                      |");
            System.out.println("|                    0. Quay lại                                       |");
            System.out.println(".======================================================================." + RESET);
            System.out.println("                  --->> Mời nhập lựa chọn của bạn <<---");
            switch (Validate.validatePositiveInt()) {
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
                    hideOpenCatalog();
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
        System.out.print(YELLOW + "Nhập số lượng danh mục cần thêm, Hoặc nhập 0 để quay lại: " + RESET);
        int n = Validate.validatePositiveInt();
        if (n == 0) {
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Danh mục thứ " + (i + 1) + ", ");
            Catalog catalog = new Catalog();

            while (true) {
                System.out.print("Nhập tên danh mục: ");
                catalog.setCatalogName(Validate.validateString());
                boolean check = false;

                for (Catalog checkName : catalogService.findAll()) {
                    if (checkName.getCatalogName().equalsIgnoreCase(catalog.getCatalogName())) {
                        System.out.println(RED + "Danh mục đã tồn tại" + RESET);
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    System.out.print("Nhập mô tả danh mục: ");
                    catalog.setDescription(Validate.validateString());

                    System.out.println(YELLOW + "Thêm danh mục thành công" + RESET);
                    catalogService.save(catalog);
                    break;
                }
            }
        }
        System.out.println(BLUE + "Đã thêm " + n + " danh mục" + RESET);
    }

    private void showCatalog() {
        System.out.println("1. Tất cả danh mục");
        System.out.println("2. Danh mục đang mở");
        System.out.println("3. Danh mục đang đóng");
        System.out.println("0. Quay lại");
        int choiceCheck = Validate.validatePositiveInt();

        if (choiceCheck == 1) {
            System.out.println(YELLOW + "Tất cả danh mục" + RESET);
            for (Catalog subject : catalogService.findAll()) {
                System.out.println(subject);
            }
        } else if (choiceCheck == 2) {
            System.out.println(YELLOW + "Danh mục đang mở" + RESET);
            for (Catalog subject : catalogService.findAll()) {
                if (subject.isStatus()) {
                    System.out.println(subject);
                }
            }
        } else if (choiceCheck == 3) {
            System.out.println(YELLOW + "Danh mục đang đóng" + RESET);
            for (Catalog subject : catalogService.findAll()) {
                if (!subject.isStatus()) {
                    System.out.println(subject);
                }
            }
        } else if (choiceCheck == 0) {
            return;
        } else {
            System.out.println(RED + "Không hợp lệ" + RESET);
        }
    }

    private void searchCatalog() {
        System.out.print("Mời nhập tên danh mục cần tìm: ");
        String searchName = Validate.validateString().toLowerCase();
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
        System.out.print(YELLOW + "Nhập ID danh mục cần thay đổi thông tin, Hoặc nhập 0 để quay lại: " + RESET);
        int idEdit = Validate.validatePositiveInt();
        Catalog catalogEdit = catalogService.findByID(idEdit);

        if (idEdit == 0) {
            return;
        }

        if (catalogEdit == null) {
            System.out.println(RED + "Không tồn tại danh mục theo ID vừa nhập" + RESET);
        } else {
            System.out.println("1. Sửa tên danh mục");
            System.out.println("2. Sửa mô tả danh mục");
            System.out.println("0. Quay lại");

            switch (Validate.validatePositiveInt()) {
                case 1:
                    System.out.print("Nhập mới tên danh mục: ");
                    catalogEdit.setCatalogName(Validate.validateString());
                    catalogService.update(catalogEdit);
                    System.out.println(YELLOW + "Sửa tên danh mục thành công" + RESET);

                    // lay ten danh muc moi
                    String newCatalogName = catalogEdit.getCatalogName();

                    // cap nhat ten danh muc moi cho cac sp co cung ma danh muc
                    for (Product product : productService.findAll()) {
                        if (product.getCatalog().getCatalogId() == idEdit) {
                            product.getCatalog().setCatalogName(newCatalogName);
                            productService.update(product);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Nhập mới mô tả danh mục: ");
                    catalogEdit.setDescription(Validate.validateString());
                    catalogService.update(catalogEdit);
                    System.out.println(YELLOW + "Sửa mô tả danh mục thành công" + RESET);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không đúng định dạng" + RESET);
                    break;
            }
        }
    }

    private void deleteCatalog() {
        System.out.print(YELLOW + "Mời nhập ID danh mục cần xoá, Hoặc nhập 0 để quay lại: " + RESET);
        int idDelete = Validate.validatePositiveInt();
        Catalog catalogDelete = catalogService.findByID(idDelete);

        if (idDelete == 0) {
            return;
        }

        if (catalogDelete == null) {
            System.out.println(RED + "Không tồn tại danh mục theo ID vừa nhập" + RESET);
        } else {
            for (Product product : productService.findAll()) {
                if (product.getCatalog().getCatalogId() == idDelete) {
                    System.out.println(RED + "Không thể xoá, do danh mục đã có sản phẩm" + RESET);
                    return;
                }
            }
            catalogService.delete(idDelete);
            System.out.println(YELLOW + "Xóa danh mục thành công!" + RESET);
        }
    }

    private void hideOpenCatalog() {
        System.out.print("Nhập ID danh mục cần ẩn/mở: ");
        int catalogId = Validate.validatePositiveInt();
        Catalog catalog = catalogService.findByID(catalogId);

        if (catalog != null) {
            System.out.println("1. Ẩn danh mục");
            System.out.println("2. Mở lại danh mục");
            System.out.println("0. Quay lại");
            int choice = Validate.validatePositiveInt();

            if (choice == 1) {
                // an danh muc va sp
                if (catalog.isStatus()) {
                    catalog.setStatus(false);
                    catalogService.updateData();
                    productService.hideProductsByCatalogId(catalogId, false);
                    System.out.println(YELLOW + "Ẩn danh mục và sản phẩm thành công" + RESET);
                } else {
                    System.out.println(YELLOW + "Danh mục đang ở trạng thái ẩn" + RESET);
                }
            } else if (choice == 2) {
                // mo lai danh muc va sp
                if (!catalog.isStatus()) {
                    catalog.setStatus(true);
                    catalogService.updateData();
                    productService.hideProductsByCatalogId(catalogId, true);
                    System.out.println(YELLOW + "Danh mục và sản phẩm đã được mở lại thành công" + RESET);
                } else {
                    System.out.println(YELLOW + "Danh mục đang ở trạng thái mở" + RESET);
                }
            } else if (choice == 0) {
                return;
            } else {
                System.out.println(RED + "Lựa chọn không hợp lệ" + RESET);
            }
        } else {
            System.out.println(RED + "Không tìm thấy danh mục có mã: " + catalogId + RESET);
        }
    }
}
