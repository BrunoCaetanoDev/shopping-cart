package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.model.Cart;
import com.bruno.caetano.dev.shoppingcart.entity.model.CartItem;
import com.bruno.caetano.dev.shoppingcart.entity.request.out.DispatchItemRequest;
import com.bruno.caetano.dev.shoppingcart.enums.CartStatus;
import com.bruno.caetano.dev.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.CART;
import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.ENTITY_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class CartService implements CartServiceContract {

    private final CartRepository cartRepository;

    private final CartItemService cartItemService;

    private final ItemStorageClient itemClient;


    @Override
    public Page<Cart> findAll(Cart cart, Pageable pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());
        return cartRepository.findAll(Example.of(cart, exampleMatcher), pageRequest);
    }

    @Override
    public Cart findBydId(String id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, CART, id)));
    }

    @Override
    @Transactional
    public Cart save(Cart cart) {
        cart.setStatus(CartStatus.PENDING);
        Map<String, CartItem> cartItemMap = cart.getItems().stream()
                .collect(Collectors.toMap(CartItem::getItemUid, Function.identity(), (existing, replacement) -> {
                    existing.setQuantity(existing.getQuantity().add(replacement.getQuantity()));
                    return existing;
                }));
        cart.setItems(cartItemMap.values().stream().collect(Collectors.toList()));
        final Cart persistedCart = cartRepository.save(cart);
        cart.getItems().stream().forEach(cartItem -> {
            itemClient.getItem(cartItem.getItemUid());
            cartItem.setCart(persistedCart);
            cartItemService.save(cartItem);
        });
        return cart;
    }

    @Override
    public Cart update(Cart entity) {
        Cart cart = findBydId(entity.getId());
        if (cart.getStatus().compareTo(CartStatus.SUBMITTED) != 0 && cart.getStatus().compareTo(CartStatus.CANCELLED) != 0) {
            cart = findBydId(entity.getId());
            cart.getItems().forEach(i -> itemClient
                    .dispatchItem(i.getItemUid(), DispatchItemRequest.builder().quantity(i.getQuantity()).build()));
        }
        if (cart.getStatus().compareTo(CartStatus.SUBMITTED) != 0) {
            cart.setStatus(entity.getStatus());
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public void deleteById(String id) { cartRepository.deleteById(id); }

    @Override
    public List<CartItem> listCartItems(String cartUid) {
        return cartItemService.listCartItems(cartUid);
    }

    @Override
    public BigDecimal calculateCartTotal(Cart cart) {
        return cart.getItems().stream().map(cartItem ->
                itemClient.getItem(cartItem.getItemUid())
                        .getPrice().multiply(new BigDecimal(cartItem.getQuantity()))
        ).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getCartItem(String cartUid, String itemUid) {
        exists(cartUid);
        return cartItemService.getCartCartItem(itemUid, cartUid);
    }

    private void exists(String cartUid) {
        if (!cartRepository.existsById(cartUid)) {
            new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, CART, cartUid));
        }
    }

}
