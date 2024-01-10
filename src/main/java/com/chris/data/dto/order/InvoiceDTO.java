package com.chris.data.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceDTO {
    private long id;
    @JsonProperty("orders")
    private List<OrderDTO> orders;

    @JsonProperty("payment_id")
    private long paymentId;
}
