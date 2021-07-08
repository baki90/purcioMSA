package com.order.client;

import com.order.dto.order.ProductCount;
import com.order.dto.product.ProductResDTO;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name="product-service", url="http://192.168.0.40:8084", fallbackFactory = ProductFallbackFactory.class)
public interface ProductClient {

    @GetMapping("/api/product/list")
    ResponseEntity<List<ProductResDTO>> retrieveProductList(@RequestParam List<Long> productIds);


    @PutMapping("/api/product/remove")
    public ResponseEntity removeStock(@RequestBody List<ProductCount> productCounts);

}
