package com.product.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
public class ProductResDTO {
    private Long id;
    private String name; // 판매 상품 이름
    private int price; // 판매되는 가격
    private int stockQuantity; // 재고

    @Builder
    public ProductResDTO(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
