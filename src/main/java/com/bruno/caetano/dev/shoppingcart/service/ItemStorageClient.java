package com.bruno.caetano.dev.shoppingcart.service;

import com.bruno.caetano.dev.shoppingcart.entity.request.out.DispatchItemRequest;
import com.bruno.caetano.dev.shoppingcart.entity.response.in.GetItemResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ItemStorageClient {

    @GetMapping("/item-storage/api/v1/items/{id}")
    GetItemResponse getItem(@PathVariable("id") String id);

    @GetMapping("/item-storage/api/v1/items")
    List<GetItemResponse> listItems();

    @PostMapping("/item-storage/api/v1/items/{id}/dispatch")
    void dispatchItem(@PathVariable("id") String id, @RequestBody DispatchItemRequest request);

}
