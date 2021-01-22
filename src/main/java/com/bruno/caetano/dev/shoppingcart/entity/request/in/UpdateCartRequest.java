package com.bruno.caetano.dev.shoppingcart.entity.request.in;

import com.bruno.caetano.dev.shoppingcart.enums.CartStatus;
import com.bruno.caetano.dev.shoppingcart.utils.annotation.CartStatusSubset;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {

    @JsonIgnore
    private String id;
    @CartStatusSubset(anyOf = {CartStatus.PENDING, CartStatus.SUBMITTED, CartStatus.CANCELLED})
    private String status;

}