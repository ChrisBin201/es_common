package com.chris.data.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderLineDTO {
    private long id;
    @JsonProperty("product_item_id")
    private long productItemId;
    private long quantity;
}
