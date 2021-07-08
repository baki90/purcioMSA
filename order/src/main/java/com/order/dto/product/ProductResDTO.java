package com.order.dto.product;

import lombok.Data;

@Data
public class ProductResDTO {
    private Long id;
    private String name; // 판매 상품 이름
    private int price; // 판매되는 가격
    private int stockQuantity; // 재고
}
