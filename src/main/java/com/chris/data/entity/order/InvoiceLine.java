package com.chris.data.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

@Data
@Entity
@Table(name = "invoice_line")
@ToString
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    Invoice.InvoiceStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private Invoice invoice;

    @OneToOne(orphanRemoval = true,cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "order_line_id")
    @JsonIgnore
    @Transient // ignore from elasticsearch
    private OrderLine orderLine;
}
