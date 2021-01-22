package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.model.CartItem;

import java.util.List;

public interface CartItemServiceContract extends CrudServiceContract<CartItem> {

    List<CartItem> listCartItems(String cartUid);

    void addItem(CartItem item);

    void removeItem(CartItem item);

    CartItem getCartCartItem(String cartItemUid, String cartUid);

}
