package ra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private int cartId;
    private int userId;
    private Map<Long, Integer> products;

    public Cart() {
        // khoi tao gio hang voi danh sach rong
        products = new HashMap<>();
    }

    public Cart(int cartId, int userId, Map<Long, Integer> products) {
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

    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProduct(Long productId, Integer quantity) {
        products.put(productId, quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProduct(Long productId) {
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
