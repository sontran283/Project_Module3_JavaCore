package ra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static ra.config.Color.*;


public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int newCartId = 1;
    private static int newUserId = 1;
    private int cartId;
    private int userId;
    private Map<Integer, Integer> products;

    public Cart() {
        // khoi tao gio hang voi danh sach rong
        products = new HashMap<>();
        this.cartId = newCartId++;
        this.userId = newUserId++;
    }

    public Cart(int cartId, int userId, Map<Integer, Integer> products) {
        this.cartId = cartId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProduct(Integer productId, Integer quantity) {
        products.put(productId, quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProduct(Integer productId) {
        products.remove(productId);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", products=" + products +
                '}';
    }
}
