package com.chris.data.entity.order;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.data.entity.Auditable;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Entity
@Table(name = "order_line")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderLine extends Auditable<String, OrderLine> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_item",columnDefinition = "jsonb")
    @JsonProperty("product_item")
    @Field(name = "product_item")
    private ProductItemDetail productItem;

    private long quantity;

    @Column(name = "is_rated")
    @JsonProperty("is_rated")
    @Field(name = "is_rated")
    private boolean isRated ;


//    @Column(name = "customer_id")
//    @JsonProperty("customer_id")
//    @Field(name = "customer_id")
//    private long customerId;

//    @JsonProperty("invoice_line")
//    @Field(name = "invoice_line")
//    @OneToOne(mappedBy = "orderLine", cascade = CascadeType.ALL)
//    private InvoiceLine invoiceLine;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;




}
