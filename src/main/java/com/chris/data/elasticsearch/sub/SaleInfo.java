package com.chris.data.elasticsearch.sub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleInfo {
    @JsonProperty("order_count")
    @Field(name = "order_count")
    long orderCount;

    @JsonProperty("total_quantity")
    @Field(name = "total_quantity")
    long totalQuantity;
}
