package com.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order.dto.order.OrderCreateReqDTO;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<Long> createOrder(Long userId, @RequestBody OrderCreateReqDTO orderCreateReqDTO){
        return orderService.createOrder(userId,orderCreateReqDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> cancelOrder(@PathVariable("id") Long userId) throws JsonProcessingException {
        return orderService.cancelOrder(userId);
    }
}
