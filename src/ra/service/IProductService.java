package ra.service;

import ra.model.Product;

import java.util.List;
import java.util.Map;

public interface IProductService extends IService<Product> {

    void hideProductsByCatalogId(int catalogId, boolean status);

    List<Product> findByCatalog(int catalogId, List<Product> productList);

    List<Product> findName(String name);

    Map<Integer, Integer> getProducts();
}
