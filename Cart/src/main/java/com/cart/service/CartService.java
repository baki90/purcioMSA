package com.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cart.client.ProductClient;
import com.cart.domain.Cart;
import com.cart.domain.CartProduct;
import com.cart.dto.cart.CartAddProductReqDTO;
import com.cart.dto.product.ProductResDTO;
import com.cart.repository.CartRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @PostConstruct
    @Transactional
    public void initialize() {
        Cart cart1 = Cart.createCart(1L);
        Cart cart2 = Cart.createCart(2L);

        cart1.addProduct(CartProduct.createCartProduct(1L,1));
        cart1.addProduct(CartProduct.createCartProduct(2L,1));

        cart2.addProduct(CartProduct.createCartProduct(2L, 3));
        cart2.addProduct(CartProduct.createCartProduct(1L, 3));

        cartRepository.save(cart1);
        cartRepository.save(cart2);

    }

    /**
     * 상품을 장바구니에 추가한다.
     * @param userId
     * @return 장바구니 아이디
     */
    @Transactional
    public ResponseEntity<Long> addProductToCart(Long userId, CartAddProductReqDTO reqDTO) {
        // TODO: 상품의 재고에 따라서 주문 가능 여부 확인 필요

        // 1. CartProduct 생성
        CartProduct cartProduct = CartProduct.createCartProduct(reqDTO.getProductId(), reqDTO.getCount());

        // 2. 유저의 장바구니 조회 후 상품 추가
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);
        cart.addProduct(cartProduct);

        // 3. 저장
        cartRepository.save(cart);

        return ResponseEntity.ok().body(cart.getId());
    }


    /** 상품을 장바구니에서 제거한다.
     * @return null
     */
    @Transactional
    public ResponseEntity<Long> removeProductInCart(Long userId, Long cartProductId){
        // 1. 유저의 카트 조회 후, 삭제
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);
        cart.removeProduct(cartProductId);

        return ResponseEntity.ok().body(cart.getId());
    }

    /**
     * 유저의 아이디를 바탕으로 장바구니를 조회한다.
     * @param userId
     * @return
     */

    public ResponseEntity<List<ProductResDTO>> retrieveCartByUserId(Long userId){

        // 1. 카트 조회
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);

        // 2. Product API 통신
        List<Long> productIds = cart.getCartProducts().stream().map(CartProduct::getProductId).collect(Collectors.toList());
        List<ProductResDTO> productResDTOS = productClient.retrieveProductList(productIds).getBody();

        return ResponseEntity.ok().body(productResDTOS);
    }


}
