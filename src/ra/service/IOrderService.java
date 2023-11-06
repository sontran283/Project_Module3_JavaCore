package ra.service;

import ra.model.Order;

import java.util.List;

public interface IOrderService extends IService<Order>{
    List<Order> address(Order order);

    List<Order> getOrderHistory();
}
