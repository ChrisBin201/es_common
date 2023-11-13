package com.chris.data.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDTO {

    private long id;

    @NotNull(message = "Product item id is required")
    @JsonProperty("product_item_id")
    private long productItemId;

    @NotNull(message = "Quantity is required")
    private long quantity;
}
