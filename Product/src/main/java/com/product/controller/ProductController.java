package com.product.controller;


import com.product.domain.Product;
import com.product.dto.product.ProductCount;
import com.product.dto.product.ProductResDTO;
import com.product.dto.product.ProductRetrieveConditionDTO;
import com.product.dto.product.ProductRetrieveResponseDTO;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 조건에 따라 상품을 찾고, 페이징으로 반환한다.
     * @param condition 조회 조건
     * @param pageable page, size 입력
     * @return 상품 페이징 정보
     */
    @GetMapping("/api/product")
    public Page<ProductRetrieveResponseDTO> retrieveProductList(
            ProductRetrieveConditionDTO condition, Pageable pageable){

        return productService.retrieveProductList(condition, pageable);
    }

    @GetMapping("/api/product/list")
    public ResponseEntity<List<ProductResDTO>> retrieveProductList(@RequestParam List<Long> productIds) {
        return productService.retrieveProductList(productIds);
    }

    @PutMapping("/api/product/remove")
    public ResponseEntity removeStock(@RequestBody List<ProductCount> orderProductDTOs){
        return productService.removeStock(orderProductDTOs);
    }

}
