package ra.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static ra.config.Color.*;


public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus = OrderStatus.WAITING;
    private Map<Integer, Integer> ordersDetails = new HashMap<>();
    private LocalDateTime orderAt;
    private LocalDateTime deliverAt;

    public Order() {
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

    public Map<Integer, Integer> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(Map<Integer, Integer> ordersDetails) {
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    @Override
    public String toString() {
        String format = "| %-10s | %-10s | %-20s | %-15s | %-30s | %-10s | %-15s | %-30s |%n";
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        StringBuilder sb = new StringBuilder();

        System.out.println("___________________________________________________________________________________________________________________________________________________________________");
        sb.append(String.format(format, orderId, userId, name, phoneNumber, address, decimalFormat.format(total), orderStatus, ordersDetails));

        return sb.toString();
    }
}
