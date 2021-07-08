package com.product.controller;

import com.product.domain.Shop;
import com.product.dto.shop.ShopCreateReqDTO;
import com.product.dto.shop.ShopUpdateReqDTO;
import com.product.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    
    /**
     * 뜨개샵 전체 리스트를 조회합니다.
     * @return 뜨개샵 리스트
     */
    @GetMapping("/api/shop")
    public ResponseEntity<List<Shop>> retrieveShopList(){
        return shopService.retrieveShopList();
    }
    
    /**
     * 뜨개샵을 생성합니다.
     * @param userCreateReqDTO 생성할 뜨개샵 정보
     * @return 생성된 뜨개샵
     */
    @PostMapping("/api/shop")
    public ResponseEntity<Long> createShop(@RequestBody ShopCreateReqDTO userCreateReqDTO){
        return shopService.createShop(userCreateReqDTO);
    }

    /**
     * id에 해당하는 뜨개샵을 한 개 조회합니다.
     * @return 조회한 뜨개샵
     */
    @GetMapping("/api/shop/{id}")
    public ResponseEntity<Shop> retrieveShopById(@PathVariable Long id){
        return shopService.retrieveShopById(id);
    }
    
    /**
     * id에 해당하는 뜨개샵을 수정합니다.
     * @param id 뜨개샵 id
     * @param userUpdateReqDTO 뜨개샵 수정 정보
     * @return 수정한 뜨개샵
     */
    @PutMapping("/api/shop/{id}")
    public ResponseEntity<Long> updateShopById(@PathVariable Long id, @RequestBody ShopUpdateReqDTO userUpdateReqDTO){
        return shopService.updateShopById(id, userUpdateReqDTO);
    }

    /**
     * id에 해당하는 뜨개샵을 삭제합니다.
     * @param id 뜨개샵 id
     * @return 삭제된 뜨개샵
     */
    @DeleteMapping("/api/shop/{id}")
    public ResponseEntity<Long> deleteShopById(@PathVariable Long id){
        return shopService.deleteShopById(id);
    }
}
