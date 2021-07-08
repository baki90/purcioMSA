package com.cart.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.cart.common.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart")
public class Cart extends BaseEntity {

    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProducts = new ArrayList<>();

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void addProduct(CartProduct cartProduct){
        cartProducts.add(cartProduct);
        cartProduct.setCart(this);
        update();
    }

    public void removeProduct(Long cartProductId){
        // TODO: 설렉트 쿼리가 너무 많이 나감. 성능 개선 필요.
        cartProducts.removeIf(c -> c.getId().equals(cartProductId));
        update();
    }

    public static Cart createCart(Long userId){
        Cart cart = new Cart();
        cart.setUserId(userId);

        return cart;
    }
}
