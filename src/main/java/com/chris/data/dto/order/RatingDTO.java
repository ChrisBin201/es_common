package com.chris.data.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RatingDTO {
    private long id;
    @JsonProperty("order_line_id")
    private long orderLineId;

    @Min(1)
    @Max(5)
    private int rating;
    private String message;
}
