package ra.service;

import ra.model.Product;

import java.util.List;

public interface IService<T> {
    List<T> findAll();

    void save(T t);

    void update(T t);

    List<Product> delete(int id);

    T findByID(int id);

    void updateData();

    int getNewId();
}
