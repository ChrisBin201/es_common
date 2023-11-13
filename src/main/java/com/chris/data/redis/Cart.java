package com.chris.data.redis;

import com.chris.data.entity.order.sub.ProductItemDetail;
import com.chris.data.entity.product.ProductItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Cart {
    @JsonProperty("customer_id")
    private  long customerId;
    @JsonProperty("product_item")
    private ProductItemDetail productItem;
    private long quantity;

}
