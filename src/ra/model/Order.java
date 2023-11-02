package ra.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Order  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int newId = 1;
    private int orderId;
    private int userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;

    private enum orderStatus {WAITING, CONFIRM, DELIVERY, SUCCESS, CANCEL}

    private List<OrdersDetail> ordersDetails;
    private LocalDateTime orderAt;
    private LocalDateTime deliverAt;

    public Order() {
        this.orderId = newId++;
    }

    public Order(int orderId, int userId, String name, String phoneNumber, String address, double total, List<OrdersDetail> ordersDetails, LocalDateTime orderAt, LocalDateTime deliverAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.ordersDetails = ordersDetails;
        this.orderAt = orderAt;
        this.deliverAt = deliverAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<OrdersDetail> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(List<OrdersDetail> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public LocalDateTime getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(LocalDateTime deliverAt) {
        this.deliverAt = deliverAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", total=" + total +
                ", ordersDetails=" + ordersDetails +
                ", orderAt=" + orderAt +
                ", deliverAt=" + deliverAt +
                '}';
    }
}
