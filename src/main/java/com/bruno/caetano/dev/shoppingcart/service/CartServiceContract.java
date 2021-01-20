package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.model.Cart;
import com.bruno.caetano.dev.shoppingcart.entity.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartServiceContract extends CrudServiceContract<Cart> {

    List<CartItem> listCartItems(String cartUid);

    BigDecimal calculateCartTotal(Cart cart);

}
