package com.chris.data.elasticsearch;

import com.chris.data.dto.BaseDTO;
import com.chris.data.elasticsearch.sub.ShopDetail;
import com.chris.data.entity.order.Invoice;
import com.chris.data.entity.order.Order;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.Shipment;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "order_info")
public class OrderInfo extends BaseDTO<String> {
    @Id
    private long id;

//    @JsonProperty("product_item")
//    @Field(name = "product_item")
//    private ProductItemDetail productItem;
//
//    private long quantity;

    @Enumerated(EnumType.STRING)
    private Order.OrderStatus status;

//    @JsonProperty("customer_id")
//    @Field(name = "customer_id")
//    private long customerId;

//    @JsonProperty("checkout_status")
//    @Field(name = "checkout_status")
//    @Enumerated(EnumType.STRING)
//    private  Order.InvoiceStatus checkoutStatus;

    @JsonProperty("order_lines")
    @Field(name = "order_lines")
    private List<OrderLine> orderLines;

    private Invoice invoice;

    @JsonProperty("payout_status")
    @Field(name = "payout_status")
    @Enumerated(EnumType.STRING)
    Order.InvoiceStatus payoutStatus;

    @JsonProperty("shipment_price")
    @Field(name = "shipment_price")
    private double shipmentPrice;

    private Shipment shipment;

    @JsonProperty("shipment_checkout_status")
    @Field(name = "shipment_checkout_status")
    @Enumerated(EnumType.STRING)
    private Order.InvoiceStatus shipmentCheckoutStatus;

    @JsonProperty("shop_info")
    @Field(name = "shop_info")
    private ShopDetail shopDetail;




    public static OrderInfo from(Order order, ShopDetail shopDetail) {
        OrderInfo orderInfo =  OrderInfo.builder()
                .id(order.getId())
                .status(order.getStatus())
                .orderLines(order.getOrderLines())
                .invoice(order.getInvoice())
                .payoutStatus(order.getPayoutStatus())
                .shipmentPrice(order.getShipmentPrice())
                .shipment(order.getShipment())
                .shipmentCheckoutStatus(order.getShipmentCheckoutStatus())
                .shopDetail(shopDetail)
                .build();
        orderInfo.setCreatedDate(order.getCreatedDate());
        orderInfo.setCreatedBy(order.getCreatedBy());
        orderInfo.setLastModifiedDate(order.getLastModifiedDate());
        orderInfo.setLastModifiedBy(order.getLastModifiedBy());
        return orderInfo;
    }
}
