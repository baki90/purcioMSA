package com.order.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductFallbackFactory implements FallbackFactory<ProductClient> {

    private final ProductClientFallback productClientFallback;

    @Override
    public ProductClient create(Throwable cause) {
        log.info("log:" + cause.getMessage());
        return productClientFallback;
    }
}
