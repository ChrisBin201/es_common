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
@Table(name = "invoice")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice extends Auditable<String, Invoice> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    @Field(name = "customer_id")
    @JsonIgnore
    private long customerId;

//    @Column(name = "order_lines_price")
//    @JsonProperty("order_lines_price")
//    @Field(name = "order_lines_price")
//    private double orderLinesPrice;

//    @JoinColumn(name = "invoice_id")
//    @JsonProperty("order_lines")
//    @Field(name = "order_lines")
//    @OneToMany
//    List<OrderLine> orderLines;
    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @org.springframework.data.annotation.Transient // ignore from elasticsearch
    private List<InvoiceLine> invoiceLines;

    @Column(name = "shipment_price")
    @JsonProperty("shipment_price")
    @Field(name = "shipment_price")
    private double shipmentPrice;

    @ManyToOne
    private Shipment shipment;

//    @Column(name = "total_price")
//    @JsonProperty("total_price")
//    @Field(name = "total_price")
//    private double totalPrice;


    public  enum InvoiceStatus {
        PAID, CANCEL;

        public static InvoiceStatus fromString(String status){
            try{
                return InvoiceStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_INVOICE_STATUS.getCode(), CommonErrorCode.INVALID_INVOICE_STATUS.getMessage());
            }
        }
    }

}
