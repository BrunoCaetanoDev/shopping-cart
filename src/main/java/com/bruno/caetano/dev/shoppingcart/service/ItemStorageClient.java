package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.request.out.DispatchItemRequest;
import com.bruno.caetano.dev.shoppingcart.entity.response.in.GetItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE;

@FeignClient(ITEM_STORAGE)
public interface ItemStorageClient {

    @GetMapping("/items/{id}")
    GetItemResponse getItem(@PathVariable("id") String id);

    @GetMapping("/items")
    List<GetItemResponse> listItems();

    @PostMapping("/items/{id}/dispatch")
    void dispatchItem(@PathVariable("id") String id, @RequestBody DispatchItemRequest request);

}
