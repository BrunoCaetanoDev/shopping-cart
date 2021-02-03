package com.bruno.caetano.dev.shoppingcart.configuration;

import com.bruno.caetano.dev.shoppingcart.service.ItemStorageClient;
import com.bruno.caetano.dev.shoppingcart.service.RestTemplateItemClient;
import com.bruno.caetano.dev.shoppingcart.service.WebClientItemClient;
import com.bruno.caetano.dev.shoppingcart.utils.properties.ShoppingCartProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.cloud.discovery.enabled", havingValue = "false", matchIfMissing = true)
public class RestClientConfiguration {

    @Bean
    @ConditionalOnProperty(name = "item-storage.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "item-storage.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public ItemStorageClient itemStorageRestClient(final RestTemplate restTemplate, final ShoppingCartProperties shoppingCartProperties) {
        return new RestTemplateItemClient(restTemplate, shoppingCartProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "item-storage.client.provider", havingValue = "web-client")
    public ItemStorageClient itemStorageWebClient(final RestTemplate restTemplate, final ShoppingCartProperties shoppingCartProperties) {
        return new WebClientItemClient(shoppingCartProperties);
    }



}
