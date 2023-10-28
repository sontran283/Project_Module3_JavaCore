package ra.service;

import ra.model.Product;

import java.util.List;

public interface IProductService extends IService<Product> {
    List<Product> findName(String name);
}
