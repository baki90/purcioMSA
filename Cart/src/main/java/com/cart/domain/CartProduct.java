package com.cart.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.cart.common.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_product")
public class CartProduct extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Long productId;

    private int count;


    public static CartProduct createCartProduct(Long productId, int count){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProductId(productId);
        cartProduct.setCount(count);

        return cartProduct;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
