package ra.service;

import ra.model.Catalog;
import ra.model.OrdersDetail;

public interface IOrdersDetailService extends IService<OrdersDetail>{
    void addOrdersDetail(OrdersDetail ordersDetail);
}
