package com.order.domain;

import com.order.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_product")
public class OrderProduct extends BaseEntity {

    private Long productId; //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

   public void setCount(int count){
        this.count = count;
   }

    public static OrderProduct createOrderProduct(Long productId, int orderPrice, int count){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(productId);
        orderProduct.setOrderPrice(orderPrice);
        orderProduct.setCount(count);
        return orderProduct;
   }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }


}
