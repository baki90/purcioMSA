package com.product.domain;

import com.product.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 사용자가 좋아요를 눌러 놓은 상품
 * (즐겨찾기)
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartProduct extends BaseEntity {

    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public HeartProduct(String userId, Product product) {
        this.userId = userId;
        this.product = product;
    }
}
