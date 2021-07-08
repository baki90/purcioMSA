package com.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.domain.Product;
import com.product.dto.product.ProductCount;
import com.product.dto.product.ProductResDTO;
import com.product.dto.product.ProductRetrieveResponseDTO;
import com.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.product.dto.product.ProductRetrieveConditionDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    @Transactional
    public void initialize() {
        Product product = Product.builder()
        .name("램스울")
        .price(3000)
        .stockQuantity(300)
        .build();

        Product product2 = Product.builder()
                .name("면사")
                .price(3000)
                .stockQuantity(300)
                .build();

        productRepository.save(product);
        productRepository.save(product2);
    }

    /**
     * 조건에 따라 상품을 찾고, 페이징으로 반환한다.
     * @param condition 조회 조건
     * @param pageable offset, size
     * @return 상품 페이징 정보
     */
    public Page<ProductRetrieveResponseDTO> retrieveProductList(ProductRetrieveConditionDTO condition, Pageable pageable){
        return productRepository.retrieve(condition, pageable);
    }

    public ResponseEntity<List<ProductResDTO>> retrieveProductList(List<Long> productIds){
        List<Product> allById = productRepository.findAllById(productIds);
        List<ProductResDTO> resDTOS = new ArrayList<>();
        allById.stream().forEach(o -> {
            resDTOS.add(ProductResDTO.builder().
                    id(o.getId())
                    .name(o.getName())
                    .price(o.getPrice())
                    .stockQuantity(o.getStockQuantity())
                    .build());
        });

        return ResponseEntity.ok().body(resDTOS);
    }

    @Transactional
    @KafkaListener(topics= "order-cancel", groupId = "purcio")
    public void addStock(String message) throws JsonProcessingException {
        Map<Long, Integer> cancelOrderDTOS = objectMapper.readValue(message, new TypeReference<Map<Long, Integer>>() {
        });

        Set<Long> productIds = cancelOrderDTOS.keySet();
        List<Product> allById = productRepository.findAllById(productIds);

        allById.stream().forEach(o -> {
            o.addStock(cancelOrderDTOS.get(o.getId()));
            productRepository.save(o);
        });
    }

    @Transactional
    public ResponseEntity removeStock(List<ProductCount> orderProductDTOs) {

        Map<Long, Integer> productMap = orderProductDTOs.stream().collect(Collectors.toMap(ProductCount::getProductId, ProductCount::getCount));
        List<Product> allById = productRepository.findAllById(productMap.keySet());

        if(productMap.size() != allById.size()) {
            throw new IllegalArgumentException("잘못된 상품 정보가 기입되었습니다.");
        }

        allById.stream().forEach(o -> {
            o.removeStock(productMap.get(o.getId()));
            productRepository.save(o);
        });

        return ResponseEntity.ok().body(null);
    }

}