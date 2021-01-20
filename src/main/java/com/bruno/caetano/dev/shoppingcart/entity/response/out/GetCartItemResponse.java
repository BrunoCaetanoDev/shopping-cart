package com.bruno.caetano.dev.shoppingcart.entity.response.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItemResponse {

    private String itemUid;
    private Integer quantity;

}