package com.order.client;

import com.order.dto.order.ProductCount;
import com.order.dto.product.ProductResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductClientFallback implements ProductClient{
    @Override
    public ResponseEntity<List<ProductResDTO>> retrieveProductList(List<Long> productIds) {
        return null;
    }

    @Override
    public ResponseEntity removeStock(List<ProductCount> productCounts) {
        return null;
    }
}
