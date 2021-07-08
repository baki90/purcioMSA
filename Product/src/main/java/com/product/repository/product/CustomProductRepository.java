package com.product.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.product.dto.product.ProductRetrieveConditionDTO;
import com.product.dto.product.ProductRetrieveResponseDTO;

public interface CustomProductRepository {

    public Page<ProductRetrieveResponseDTO> retrieve(ProductRetrieveConditionDTO condition, Pageable pageable);
}
