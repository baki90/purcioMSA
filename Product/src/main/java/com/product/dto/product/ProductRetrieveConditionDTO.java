package com.product.dto.product;


import com.product.domain.Category;
import com.product.repository.product.ProductOrderCriteria;
import lombok.Data;
@Data
public class ProductRetrieveConditionDTO {

    private Long shopId; // 상점 아이디
    private String name; // 상품 이름
    private Category category; // 판매 카테고리

    private Integer maxPrice; // 가격 범위별 조회
    private Integer minPrice;

    private ProductOrderCriteria productOrderCriteria; // 조회 기준

}
