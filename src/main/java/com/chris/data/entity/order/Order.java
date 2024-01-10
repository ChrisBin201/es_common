package com.chris.data.entity.order;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "order_info")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends Auditable<String, Order> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payout_status")
    @JsonProperty("payout_status")
    @Field(name = "payout_status")
    Order.InvoiceStatus payoutStatus;

//    @Column(name = "customer_id")
//    @JsonProperty("customer_id")
//    @Field(name = "customer_id")
//    @JsonIgnore
//    private long customerId;

    @Column(name = "seller_id")
    @JsonProperty("seller_id")
    @Field(name = "seller_id")
    private long sellerId;

//    @Column(name = "order_lines_price")
//    @JsonProperty("order_lines_price")
//    @Field(name = "order_lines_price")
//    private double orderLinesPrice;

//    @JoinColumn(name = "invoice_id")
//    @JsonProperty("order_lines")
//    @Field(name = "order_lines")
//    @OneToMany
//    List<OrderLine> orderLines;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @org.springframework.data.annotation.Transient // ignore from elasticsearch
    private List<OrderLine> orderLines;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Invoice invoice;

    @Column(name = "shipment_price")
    @JsonProperty("shipment_price")
    @Field(name = "shipment_price")
    private double shipmentPrice;

    @ManyToOne
    private Shipment shipment;

    @Column(name = "shipment_checkout_status")
    @JsonProperty("shipment_checkout_status")
    @Field(name = "shipment_checkout_status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus shipmentCheckoutStatus;

//    @Column(name = "payment_id")
//    @JsonProperty("payment_id")
//    @Field(name = "payment_id")
//    private long paymentId;

    public  enum InvoiceStatus {
        PAID, PENDING, CANCEL;

        public static InvoiceStatus fromString(String status){
            try{
                return InvoiceStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_INVOICE_STATUS.getCode(), CommonErrorCode.INVALID_INVOICE_STATUS.getMessage());
            }
        }
    }

    public OrderStatus findNextStatus(){
        return switch (this.status) {
            case PAYMENT_PENDING -> OrderStatus.CONFIRM_PENDING;
            case CONFIRM_PENDING -> OrderStatus.SHIPMENT_PENDING;
            case SHIPMENT_PENDING -> OrderStatus.DELIVERING;
            case DELIVERING -> OrderStatus.COMPLETE;
//            case RATING_PENDING -> OrderStatus.COMPLETE;
            case COMPLETE -> OrderStatus.COMPLETE;
            default -> null;
        };
    }


    public  enum OrderStatus {
        PAYMENT_PENDING, CONFIRM_PENDING, SHIPMENT_PENDING,DELIVERING, COMPLETE,CANCEL;

        public static OrderStatus fromString(String status){
            try{
                return OrderStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_ORDER_STATUS.getCode(), CommonErrorCode.INVALID_ORDER_STATUS.getMessage());
            }
        }
    }

}
