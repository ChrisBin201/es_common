package com.chris.data.entity.order;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.data.dto.order.OrderLineDTO;
import com.chris.data.entity.Auditable;
import com.chris.data.entity.order.sub.ProductItemDetail;
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
public class OrderLine extends Auditable<String, OrderLine> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @JsonProperty("unit_price")
//    @Column(name = "unit_price")
//    @Field(name = "unit_price")
//    private double unitPrice;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_item",columnDefinition = "jsonb")
    @JsonProperty("product_item")
    @Field(name = "product_item")
    private ProductItemDetail productItem;

    private long quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    @Field(name = "customer_id")
    private long customerId;

    @JsonProperty("invoice_line")
    @Field(name = "invoice_line")
    @OneToOne(mappedBy = "orderLine", cascade = CascadeType.ALL)
    private InvoiceLine invoiceLine;

    public OrderStatus findNextStatus(){
        return switch (this.status) {
            case CONFIRM_PENDING -> OrderStatus.SHIPMENT_PENDING;
            case SHIPMENT_PENDING -> OrderStatus.DELIVERING;
            case DELIVERING -> OrderStatus.RATING_PENDING;
            case RATING_PENDING -> OrderStatus.COMPLETE;
            default -> null;
        };
    }


    public  enum OrderStatus {
        CONFIRM_PENDING, SHIPMENT_PENDING,DELIVERING, RATING_PENDING, COMPLETE,CANCEL;

        public static OrderStatus fromString(String status){
            try{
                return OrderStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_ORDER_STATUS.getCode(), CommonErrorCode.INVALID_ORDER_STATUS.getMessage());
            }
        }
    }



}
