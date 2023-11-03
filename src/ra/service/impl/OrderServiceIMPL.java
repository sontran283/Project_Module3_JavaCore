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
        orderList.add(order);
        writeReadFile.writeFile(WriteReadFile.PATH_ORDER, findAll());
        updateData();
    }

    @Override
    public void update(Order order) {
        Order orderToUpdate = findByID(order.getOrderId());
        if (orderToUpdate != null) {
            orderToUpdate.setName(order.getName());
            orderToUpdate.setPhoneNumber(order.getPhoneNumber());
            orderToUpdate.setAddress(order.getAddress());
            orderToUpdate.setTotal(order.getTotal());
            orderToUpdate.setOrderAt(order.getOrderAt());
            orderToUpdate.setDeliverAt(order.getDeliverAt());
            updateData();
        } else {
            System.out.println("Không tìm thấy đơn hàng có ID: " + order.getOrderId());
        }
    }

    @Override
    public List<Product> delete(int id) {
        Order orderToDelete = findByID(id);
        if (orderToDelete != null) {
            orderList.remove(orderToDelete);
            updateData();
        } else {
            System.out.println("Không tìm thấy đơn hàng có ID: " + id);
        }
        return null;
    }

    @Override
    public Order findByID(int id) {
        for (Order order :orderList) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        writeReadFile.writeFile(WriteReadFile.PATH_ORDER, findAll());
    }

    @Override
    public int getNewId() {
        // Tìm đơn hàng có ID lớn nhất trong danh sách và tăng thêm 1 để tạo ID mới
        int maxId = 0;
        for (Order order : orderList) {
            if (order.getOrderId() > maxId) {
                maxId = order.getOrderId();
            }
        }
        return maxId + 1;
    }

    @Override
    public void address(Order order) {
    }
}