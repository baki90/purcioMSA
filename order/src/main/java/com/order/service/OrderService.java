package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.client.ProductClient;
import com.order.domain.Address;
import com.order.domain.Delivery;
import com.order.domain.Order;
import com.order.domain.OrderProduct;
import com.order.dto.order.OrderCreateReqDTO;
import com.order.dto.order.ProductCount;
import com.order.dto.product.ProductResDTO;
import com.order.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    /** Repository */
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    @Transactional
    public void initialize() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct1 = OrderProduct.createOrderProduct(1L, 3000, 2);
        OrderProduct orderProduct2 = OrderProduct.createOrderProduct(2L, 3000, 3);

        orderProductList.add(orderProduct1);
        orderProductList.add(orderProduct2);

        Delivery delivery = Delivery.createDelivery(Address.builder().city("1").district("2").street("3").build());

        Order order = Order.createOrder(1L, delivery, orderProductList);

        orderRepository.save(order);
    }

    /**
     * N개의 상품을 주문합니다.
     * @param userId
     * @param reqDto 배송 정보 & 상품 주문 정보 포함
     * @return 주문 아이디
     */
    @Transactional
    public ResponseEntity<Long> createOrder(Long userId, OrderCreateReqDTO reqDto) {

        //  맵을 바탕으로 상품 조회
        // TODO: 상품 조회 후 예외 처리 필요
        List<ProductCount> productCounts = reqDto.getProductCounts();
        Map<Long, Integer> productMap = productCounts.stream().collect(Collectors.toMap(ProductCount::getProductId, ProductCount::getCount));

        // 2. Product API 통신
        List<Long> productIds = new ArrayList<>(productMap.keySet());
        List<ProductResDTO> products = productClient.retrieveProductList(productIds).getBody();

        // 3. 배송지 생성
        Delivery delivery = Delivery.createDelivery(reqDto.getAddress());
        List<OrderProduct> orderProducts = new ArrayList<>();

        // 4. 각 상품별로 주문 리스트를 생성하기
        products.stream().forEach(p->{
            orderProducts.add(OrderProduct.createOrderProduct(p.getId(), p.getPrice()
                    ,productMap.get(p.getId())));
        });
        // 5. 주문 생성
        Order order = Order.createOrder(userId, delivery, orderProducts);

        // 6. 재고 감소 API 통신
       productClient.removeStock(productCounts);

        // 7. 저장
        orderRepository.save(order);

        return ResponseEntity.ok().body(order.getId());
    }

    /**
     * 주문 내역을 취소합니다.
     * @param orderId
     * @return 주문 아이디
     */
    @Transactional
    public ResponseEntity<Long> cancelOrder(Long orderId) throws JsonProcessingException {

        // 1. 주문 내역 조회
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("해당하는 주문 내역을 찾을 수 없습니다.")
        );

        // 2. order 에서 주문 취소
        order.cancelOrder();

        // 3. product 재고 복원 PUB 발행
        Map<Long, Integer> productIds = new HashMap<>();

        order.getOrderProducts().stream().forEach(o -> {
            productIds.put(o.getProductId(), o.getCount());
        });

        String json = objectMapper.writeValueAsString(productIds);
        kafkaTemplate.send("order-cancel", json);

        return ResponseEntity.ok().body(order.getId());
    }
}
