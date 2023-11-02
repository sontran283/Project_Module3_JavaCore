package ra.service.impl;

import ra.config.WriteReadFile;
import ra.model.Order;
import ra.model.Product;
import ra.service.IOrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceIMPL implements IOrderService {
    static WriteReadFile<List<Order>> writeReadFile = new WriteReadFile<List<Order>>();
    public static List<Order> orderList;

    static {
        orderList = writeReadFile.readFile(WriteReadFile.PATH_ORDER);
        orderList = (orderList == null) ? new ArrayList<>() : orderList;
    }

    @Override
    public List<Order> findAll() {
        return orderList;
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

    @Override
    public void address(Order order) {

    }
}
