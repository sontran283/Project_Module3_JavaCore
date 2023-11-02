package ra.service.impl;

import ra.config.WriteReadFile;
import ra.model.Order;
import ra.model.OrdersDetail;
import ra.model.Product;
import ra.service.IOrdersDetailService;

import java.util.ArrayList;
import java.util.List;

public class OrdersDetailServiceIMPL implements IOrdersDetailService {
    static WriteReadFile<List<OrdersDetail>> writeReadFile = new WriteReadFile<List<OrdersDetail>>();
    public static List<OrdersDetail> ordersDetailList;

    static {
        ordersDetailList = writeReadFile.readFile(WriteReadFile.PATH_ORDERSDETAIL);
        ordersDetailList = (ordersDetailList == null) ? new ArrayList<>() : ordersDetailList;
    }

    @Override
    public List<OrdersDetail> findAll() {
        return ordersDetailList;
    }

    @Override
    public void save(OrdersDetail ordersDetail) {

    }

    @Override
    public void update(OrdersDetail ordersDetail) {

    }

    @Override
    public List<Product> delete(int id) {

        return null;
    }

    @Override
    public OrdersDetail findByID(int id) {
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
    public void addOrdersDetail(OrdersDetail ordersDetail) {
//        orderDetails.add(orderDetail);
    }
}
