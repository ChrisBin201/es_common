package com.chris.data.elasticsearch;

import com.chris.data.elasticsearch.sub.ShopDetail;
import com.chris.data.entity.order.InvoiceLine;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "order_info")
public class OrderInfo {
    @Id
    private long id;

    @JsonProperty("product_item")
    @Field(name = "product_item")
    private ProductItemDetail productItem;

    private long quantity;

    @Enumerated(EnumType.STRING)
    private OrderLine.OrderStatus status;

    @JsonProperty("customer_id")
    @Field(name = "customer_id")
    private long customerId;

    @JsonProperty("invoice_line")
    @Field(name = "invoice_line")
    private InvoiceLine invoiceLine;

    @JsonProperty("shop_info")
    @Field(name = "shop_info")
    private ShopDetail shopDetail;


    public static OrderInfo from(OrderLine orderLine, ShopDetail shopDetail) {
        return OrderInfo.builder()
                .id(orderLine.getId())
                .productItem(orderLine.getProductItem())
                .quantity(orderLine.getQuantity())
                .status(orderLine.getStatus())
                .customerId(orderLine.getCustomerId())
                .invoiceLine(orderLine.getInvoiceLine())
                .shopDetail(shopDetail)
                .build();
    }
}
