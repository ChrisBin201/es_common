package com.chris.data.dto.order;

import com.chris.data.entity.order.Order;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.Shipment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutInfo {
    @JsonProperty("invoice_id")
    @Field(name = "invoice_id")
    private long invoiceId;

    @Enumerated(EnumType.STRING)
    private Order.InvoiceStatus status;

    @JsonProperty("customer_id")
    @Field(name = "customer_id")
    private long customerId;

    @JsonProperty("order_lines")
    private List<OrderLine> orderLines;

    @JsonProperty("shipment_price")
    @Field(name = "shipment_price")
    private double shipmentPrice;

    private Shipment shipment;

//    public static CheckoutInfo from(Order order, List<OrderLine> orderLines ) {
//        return CheckoutInfo.builder()
//                .invoiceId(order.getId())
//                .status(order.getCheckoutStatus())
//                .customerId(order.getCustomerId())
//                .orderLines(orderLines)
//                .shipmentPrice(order.getShipmentPrice())
//                .shipment(order.getShipment())
//                .build();
//    }

}
