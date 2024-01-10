package com.chris.data.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Data
@Entity
@Table(name = "invoice")
@ToString
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "checkout_status")
    @JsonProperty("checkout_status")
    @Field(name = "checkout_status")
    private Order.InvoiceStatus checkoutStatus;

    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    @Field(name = "customer_id")
    private long customerId;

    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @org.springframework.data.annotation.Transient // ignore from elasticsearch
    private List<Order> orders;

    @Column(name = "payment_id")
    @JsonProperty("payment_id")
    @Field(name = "payment_id")
    private long paymentId;

}
