package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cart.dto.product.ProductResDTO;

import java.util.List;

@FeignClient(name="product-service", url="http://192.168.0.40:8084")
public interface ProductClient {

    @GetMapping("/api/product/list")
    ResponseEntity<List<ProductResDTO>> retrieveProductList(@RequestParam List<Long> productIds);

}
