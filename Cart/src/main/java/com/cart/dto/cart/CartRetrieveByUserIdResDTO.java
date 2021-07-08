package com.cart.dto.cart;

import lombok.Builder;
import lombok.Data;

@Data
public class CartRetrieveByUserIdResDTO {

    // TODO: 추후 shop name과 id 추가 예정
    private String name; // 판매 상품 이름
    private int price; // 판매되는 가격
    private int count; // 수량

    @Builder
    public CartRetrieveByUserIdResDTO(String name,  int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
