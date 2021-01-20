package com.bruno.caetano.dev.shoppingcart.repository;

import com.bruno.caetano.dev.shoppingcart.entity.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    List<CartItem> findByCartId(String cartUid);

    boolean existsByCartIdAndItemUid(String cartItemUid, String cartUid);

    Optional<CartItem> findByCartIdAndItemUid(String cartItemUid, String cartUid);

    Optional<CartItem> findByIdAndCartId(String id, String cartUid);

    Optional<CartItem> findByItemUidAndCartId(String itemUid, String cartUid);
}