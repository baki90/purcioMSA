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
 * 사용자가 좋아요를 눌러 놓은 상점
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartShop extends BaseEntity {

    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public HeartShop(String userId, Shop shop) {
        this.userId = userId;
        this.shop = shop;
    }
}
