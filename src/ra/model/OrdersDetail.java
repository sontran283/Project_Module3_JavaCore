package ra.model;

import java.io.Serializable;
import static ra.config.Color.*;


public class OrdersDetail  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private int orderId;
    private String name;
    private double unitPrice;
    private int quantity;

    public OrdersDetail() {
    }

    public OrdersDetail(int productId, int orderId, String name, double unitPrice, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrdersDetail{" +
                "productId=" + productId +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                '}';
    }
}
