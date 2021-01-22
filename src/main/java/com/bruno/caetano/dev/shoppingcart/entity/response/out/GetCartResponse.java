package com.bruno.caetano.dev.shoppingcart.entity.response.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse {

    private String id;
    private List<GetCartItemResponse> items;
    private String status;
    private BigDecimal total;

}