package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.request.out.DispatchItemRequest;
import com.bruno.caetano.dev.shoppingcart.entity.response.in.GetItemResponse;
import com.bruno.caetano.dev.shoppingcart.error.UnavailableServiceException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE;

@FeignClient(value = ITEM_STORAGE, fallbackFactory = ItemStorageClient.ItemStorageClientFallbackFactory.class)
public interface ItemStorageClient {

    @GetMapping("/items/{id}")
    GetItemResponse getItem(@PathVariable("id") String id);

    @GetMapping("/items")
    List<GetItemResponse> listItems();

    @PostMapping("/items/{id}/dispatch")
    void dispatchItem(@PathVariable("id") String id, @RequestBody DispatchItemRequest request);

    @Component
    class ItemStorageClientFallbackFactory implements FallbackFactory<ItemStorageClientFallback> {

        @Override
        public ItemStorageClientFallback create(Throwable cause) {
            return new ItemStorageClientFallback();
        }

    }

    class ItemStorageClientFallback implements ItemStorageClient {


        @Override
        public GetItemResponse getItem(String id) {
            throw new UnavailableServiceException(ITEM_STORAGE);
        }

        @Override
        public List<GetItemResponse> listItems() {
            throw new UnavailableServiceException(ITEM_STORAGE);
        }

        @Override
        public void dispatchItem(String id, DispatchItemRequest request) {
            throw new UnavailableServiceException(ITEM_STORAGE);
        }
    }
}
