package com.chris.data.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceDTO {
    @JsonProperty("order_lines")
    private List<OrderLineDTO> orderLines;

//    @JsonProperty("payment_id")
//    private long paymentId;
//
    @JsonProperty("shipment_id")
    private long shipmentId;


}
