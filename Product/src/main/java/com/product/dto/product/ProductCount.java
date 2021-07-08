package com.product.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ProductCount {
    private Long productId;
    private Integer count;
}
