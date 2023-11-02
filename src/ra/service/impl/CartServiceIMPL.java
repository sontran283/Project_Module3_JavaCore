package ra.service.impl;

import ra.config.WriteReadFile;
import ra.model.Cart;
import ra.model.Product;
import ra.service.ICartService;

import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService {
    static WriteReadFile<List<Cart>> writeReadFile = new WriteReadFile<List<Cart>>();
    public static List<Cart> cartList;

    static {
        cartList = writeReadFile.readFile(WriteReadFile.PATH_CART);
        cartList = (cartList == null) ? new ArrayList<>() : cartList;
    }


    @Override
    public List<Cart> findAll() {
        return cartList;
    }

    @Override
    public void save(Cart cart) {
        cartList.add(cart);
        updateData();
    }

    @Override
    public void update(Cart cart) {

    }

    @Override
    public List<Product> delete(int id) {
        Cart cartDelete = findByID(id);
        cartList.remove(cartDelete);
        updateData();
        return null;
    }

    @Override
    public Cart findByID(int id) {
        for (Cart cart : cartList) {
            if (cart.getCartId() == id) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        writeReadFile.writeFile(WriteReadFile.PATH_CART, findAll());

    }

    @Override
    public int getNewId() {
        return 0;
    }
}
