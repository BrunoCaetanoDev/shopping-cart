package com.bruno.caetano.dev.shoppingcart.enums;

import com.bruno.caetano.dev.shoppingcart.error.InvalidResourceStatusException;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.CART;

public enum CartStatus {
    PENDING, SUBMITTED, CANCELLED;

    public static CartStatus fromName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Optional<CartStatus> itemStatus = Arrays.stream(values()).filter(e -> e.name().equals(name)).findFirst();
        return itemStatus.orElseThrow(
                () -> new InvalidResourceStatusException(CART, name, Arrays.stream(values()).map(Enum::name).collect(
                        Collectors.toList())));
    }
}