package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.model.CartItem;
import com.bruno.caetano.dev.shoppingcart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.CART;
import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.ENTITY_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceContract {

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> listCartItems(String cartUid) {
        return cartItemRepository.findByCartId(cartUid);
    }

    @Override
    public void addItem(CartItem item) {

    }

    @Override
    public void removeItem(CartItem item) {

    }

    @Override
    public CartItem getCartCartItem(String cartItemUid, String cartUid) {
        return cartItemRepository.findByItemUidAndCartId(cartItemUid, cartUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, CART, cartUid)));
    }

    @Override
    public Page<CartItem> findAll(CartItem cartItem, Pageable pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("itemUid", ExampleMatcher.GenericPropertyMatchers.exact());
        return cartItemRepository.findAll(Example.of(cartItem, exampleMatcher), pageRequest);
    }

    @Override
    public CartItem findBydId(String id) {
        return cartItemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, CART, id)));
    }

    @Override
    public CartItem save(CartItem cart) {
        return cartItemRepository.save(cart);
    }

    @Override
    public CartItem update(CartItem cart) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        cartItemRepository.deleteById(id);
    }
}
