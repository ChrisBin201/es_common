package com.chris.data.redis;

import com.chris.data.entity.product.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Cart {
    private  long customerId;
    private ProductItem productItem;
    private long quantity;

}
