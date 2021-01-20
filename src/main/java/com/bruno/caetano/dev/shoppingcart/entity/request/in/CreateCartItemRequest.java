package com.bruno.caetano.dev.shoppingcart.entity.request.in;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartItemRequest {

    @NotNull
    @NotEmpty
    private String itemUid;
    @NotNull
    @Positive
    private BigInteger quantity;

}
