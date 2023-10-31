package ra.service;

import ra.model.Product;

import java.util.List;

public interface IProductService extends IService<Product> {

    void hideProductsByCatalogId(int catalogId, boolean status);

    List<Product> findName(String name);
}
