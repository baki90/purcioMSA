package com.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cart.dto.cart.CartAddProductReqDTO;
import com.cart.dto.product.ProductResDTO;
import com.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping()
    public ResponseEntity<Long> addProductToCart(Long userId, @RequestBody CartAddProductReqDTO reqDTO){
        return cartService.addProductToCart(userId, reqDTO);
    }


    @DeleteMapping()
    public ResponseEntity<Long> removeProductInCart(Long userId, Long cartProductId){
        return cartService.removeProductInCart(userId, cartProductId);
    }


    @GetMapping("userId={id}")
    public ResponseEntity<List<ProductResDTO>> retrieveCartByUserId(@PathVariable("id") Long userId) {
        return cartService.retrieveCartByUserId(userId);
    }

}
