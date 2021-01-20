package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.request.out.DispatchItemRequest;
import com.bruno.caetano.dev.shoppingcart.entity.response.in.GetItemResponse;
import com.bruno.caetano.dev.shoppingcart.utils.properties.ShoppingCartProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.*;

@RequiredArgsConstructor
public class RestTemplateItemClient implements ItemStorageClient {

    private final RestTemplate restTemplate;

    private final ShoppingCartProperties shoppingCartProperties;

    @Override
    public GetItemResponse getItem(String id) {
            return restTemplate
                    .getForEntity(String.join(FRONT_SLASH_DELIMITER, shoppingCartProperties.itemStorageApiUrl, ITEMS, String.valueOf(id)),
                            GetItemResponse.class).getBody();
    }

    @Override
    public List<GetItemResponse> listItems() {
        return null;
    }

    @Override
    public void dispatchItem(String id, DispatchItemRequest request) {
        restTemplate
                .postForEntity(String
                                .join(FRONT_SLASH_DELIMITER, shoppingCartProperties.itemStorageApiUrl,id, ITEM_STORAGE_DISPATCH_URI),
                        request, Object.class);
    }
}
