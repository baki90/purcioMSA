package com.order.dto.order;

import com.order.domain.Address;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderCreateReqDTO {
    public Address address;
    public List<ProductCount> productCounts;
}
