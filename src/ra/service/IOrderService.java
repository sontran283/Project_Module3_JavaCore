package ra.service;

import ra.model.Order;

public interface IOrderService extends IService<Order>{
    void address(Order order);
}
