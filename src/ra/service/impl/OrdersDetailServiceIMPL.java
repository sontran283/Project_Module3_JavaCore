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
        ordersDetailList.add(ordersDetail);
        writeReadFile.writeFile(WriteReadFile.PATH_ORDERSDETAIL, findAll());
        updateData();
    }

    @Override
    public void update(OrdersDetail ordersDetail) {
        for (OrdersDetail detail : ordersDetailList) {
            if (detail.getOrderId() == ordersDetail.getOrderId() && detail.getProductId() == ordersDetail.getProductId()) {
                detail.setQuantity(ordersDetail.getQuantity());
                break;
            }
        }
        updateData();
    }

    @Override
    public List<Product> delete(int id) {
        OrdersDetail ordersDetailDelete = findByID(id);
        ordersDetailList.remove(ordersDetailDelete);
        updateData();
        return null;
    }

    @Override
    public OrdersDetail findByID(int id) {
        for (OrdersDetail ordersDetail : ordersDetailList) {
            if (ordersDetail.getOrderId() == id) {
                return ordersDetail;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        writeReadFile.writeFile(WriteReadFile.PATH_ORDERSDETAIL, findAll());
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
