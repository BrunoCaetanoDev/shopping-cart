package com.bruno.caetano.dev.shoppingcart.utils.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ShoppingCartProperties {

    @Value("${spring.application.name:shopping-cart}")
    public String applicationName;
    @Value("${server.servlet.context-path:#{null}}")
    private String servletContextPath;
    @Value("${item-storage.api.name:items}")
    public String itemStorageApiName;
    @Value("${item-storage.api.version:v1}")
    public String itemStorageApiVersion;
    @Value("${item-storage.api.url:http://item-storage:8080/item-storage/api/v1/items}")
    public String itemStorageApiUrl;

}