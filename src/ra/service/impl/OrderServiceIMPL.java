package ra.service.impl;

import ra.model.Order;
import ra.model.Product;
import ra.service.IOrderService;

import java.util.List;

public class OrderServiceIMPL implements IOrderService {

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void save(Order order) {

    }

    @Override
    public void update(Order order) {

    }

    @Override
    public List<Product> delete(int id) {

        return null;
    }

    @Override
    public Order findByID(int id) {
        return null;
    }

    @Override
    public void updateData() {

    }

    @Override
    public int getNewId() {
        return 0;
    }
}
