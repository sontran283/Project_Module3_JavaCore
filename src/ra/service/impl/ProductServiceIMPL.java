package ra.service.impl;

import ra.config.WriteReadFile;
import ra.model.Product;
import ra.service.IProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    static WriteReadFile<List<Product>> writeReadFile = new WriteReadFile<List<Product>>();
    public static List<Product> productList;

    static {
        productList = writeReadFile.readFile(WriteReadFile.PATH_CATALOG);
        productList = (productList == null) ? new ArrayList<>() : productList;
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        productList.add(product);
        writeReadFile.writeFile(WriteReadFile.PATH_PRODUCT, findAll());
        updateData();
    }

    @Override
    public void update(Product product) {
        Product productEdit = findByID(product.getProductId());
        productEdit.setProductName(product.getProductName());
        productEdit.setDescription(product.getDescription());
        productEdit.setUnitPrice(product.getUnitPrice());
        productEdit.setStock(product.getStock());
        productEdit.setCatalog(product.getCatalog());
        productEdit.setStatus(product.isStatus());
        updateData();
    }

    @Override
    public void delete(int id) {
        Product productDelete = findByID(id);
        productList.remove(productDelete);
        writeReadFile.writeFile(WriteReadFile.PATH_PRODUCT, findAll());
        updateData();
    }

    @Override
    public Product findByID(int id) {
        for (Product product : productList) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        writeReadFile.writeFile(WriteReadFile.PATH_PRODUCT, findAll());
    }

    @Override
    public int getNewId() {
        return 0;
    }

    @Override
    public List<Product> findName(String name) {
        return null;
    }
}
