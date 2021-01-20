package com.bruno.caetano.dev.shoppingcart.repository;

import com.bruno.caetano.dev.shoppingcart.entity.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

}
