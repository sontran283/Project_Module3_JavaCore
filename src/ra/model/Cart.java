package ra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int cartId;
    private int userId;
    private Map<Integer, Integer> products;

    public Cart() {
        // khoi tao gio hang voi danh sach rong
        products = new HashMap<>();
    }

    public Cart(int cartId, int userId, Map<Integer, Integer> products) {
        this.cartId = cartId;
        this.userId = userId;
        this.products = products;
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
