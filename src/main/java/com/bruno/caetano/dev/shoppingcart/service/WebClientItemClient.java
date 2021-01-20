package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.request.out.DispatchItemRequest;
import com.bruno.caetano.dev.shoppingcart.entity.response.in.GetItemResponse;
import com.bruno.caetano.dev.shoppingcart.utils.properties.ShoppingCartProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
public class WebClientItemClient implements ItemStorageClient {

    private final ShoppingCartProperties shoppingCartProperties;

    @Override
    public GetItemResponse getItem(String id) {
        return null;
    }

    @Override
    public List<GetItemResponse> listItems() {
        return null;
    }

    @Override
    public void dispatchItem(String id, DispatchItemRequest request) {

    }

}
