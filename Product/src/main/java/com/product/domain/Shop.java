package com.product.domain;

import com.product.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 상품을 판매하는 Shop 도메인
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseEntity {

    @Column(name="name" , unique=true)
    private String name; // 쇼핑몰 이름
    private String picture; // 쇼핑몰 대표 이미지
    private String shopNumber; // 전화번호

    private String userId; // shop 대표 관리자

    /**
     * 뜨개샵을 생성합니다.
     * @param name 뜨개샵 이름
     * @param picture 뜨개샵 이미지
     * @param shopNumber 뜨개샵 번호
     * @param userId 뜨개샵 관리자
     * @return 생성된 뜨개샵 객체
     */
    public static Shop createShop(String name, String picture, String shopNumber, String userId) {
        Shop shop = new Shop();
        if(name != null) shop.name = name;
        if(picture != null) shop.picture = picture;
        if(shopNumber != null) shop.shopNumber = shopNumber;
        if(userId != null) shop.userId = userId;
        return shop;
    }

    /**
     * 뜨개샵 정보를 수정합니다.
     * @param name 뜨개샵 이름
     * @param picture 뜨개샵 이미지
     * @param shopNumber 뜨개샵 번호
     * @param userId 뜨개샵 관리자
     */
    public void updateShop(String name, String picture, String shopNumber, String userId) {
        super.update();
        if(name != null) this.name = name;
        if(picture != null) this.picture = picture;
        if(shopNumber != null) this.shopNumber = shopNumber;
        if(userId != null) this.userId = userId;
    }

}
