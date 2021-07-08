package com.product.service;

import com.product.domain.Shop;
import com.product.dto.shop.ShopCreateReqDTO;
import com.product.dto.shop.ShopUpdateReqDTO;
import com.product.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    /**
     * 뜨개샵 전체 리스트를 조회합니다.
     * @return 뜨개샵 리스트
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<Shop>> retrieveShopList(){
        List<Shop> shopList = shopRepository.findAll();
        
        return ResponseEntity.ok().body(shopList);
    }
    
    /**
     * 뜨개샵을 생성합니다.
     * @param shopCreateReqDTO 생성할 뜨개샵 정보
     * @return 생성된 뜨개샵
     */
    @Transactional()
    public ResponseEntity<Long> createShop(ShopCreateReqDTO shopCreateReqDTO){

        Shop shop = Shop.createShop(shopCreateReqDTO.getName(), shopCreateReqDTO.getPicture(), shopCreateReqDTO.getShopNumber(), shopCreateReqDTO.getUserId());
        shopRepository.save(shop);
        
        return ResponseEntity.ok().body(shop.getId());
    }
    
    /**
     * id에 해당하는 뜨개샵을 한 개 조회합니다.
     * @param id 뜨개샵 id
     * @return 조회한 뜨개샵
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Shop> retrieveShopById(Long id){
        Shop shop = shopRepository.findById(id).orElse(null);

        return ResponseEntity.ok().body(shop);
    }

    /**
     * id에 해당하는 뜨개샵을 수정합니다.
     * @param id 뜨개샵 id
     * @param shopUpdateReqDTO 수정할 뜨개샵 정보
     * @return 수정된 뜨개샵
     */
    @Transactional
    public ResponseEntity<Long> updateShopById(Long id, ShopUpdateReqDTO shopUpdateReqDTO){
        Shop shop = shopRepository.findById(id).orElseThrow(NoSuchElementException::new);

        shop.updateShop(shopUpdateReqDTO.getName(), shopUpdateReqDTO.getPicture(), shopUpdateReqDTO.getShopNumber(), null);

        shopRepository.save(shop);

        return ResponseEntity.ok().body(shop.getId());
    }

    /**
     * id에 해당하는 뜨개샵을 삭제합니다.
     * @param id 뜨개샵 id
     * @return 삭제된 뜨개샵
     */
    @Transactional
    public ResponseEntity<Long> deleteShopById(Long id){
        Shop shop = shopRepository.findById(id).orElseThrow(NoSuchElementException::new);

        shopRepository.delete(shop);

        return ResponseEntity.ok().body(shop.getId());
    }
}
