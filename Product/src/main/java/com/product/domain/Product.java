package com.product.domain;

import com.product.common.exception.NotEnoughStockException;
import com.product.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="product")
public class Product extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop; // 판매하는 샵

    private String name; // 판매 상품 이름
    private Category category; // 판매 카테고리
    private int price; // 판매되는 가격
    private int stockQuantity; // 재고


    @OneToMany(mappedBy = "product")
    private List<HeartProduct> heartProducts = new ArrayList<>();

    /** 비즈니스 메소드 */
    // 재고 수량 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }

        this.stockQuantity = restStock;
    }

    @Builder
    public Product(Shop shop, String name, Category category, int price, int stockQuantity) {
        this.shop = shop;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
