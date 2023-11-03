package ra.service;

import ra.model.Cart;

public interface ICartService extends IService<Cart> {
    Cart findCartByUserLogin();  // xem co ton tai cart nao cua user chua thanh toan hay ko
}
