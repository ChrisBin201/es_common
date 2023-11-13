package com.chris.data.entity.order;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Entity
@Table(name = "rating")
@ToString
public class Rating extends Auditable<String, Rating>  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(1)
    @Max(5)
    private int rating;

    private String message;

    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    @Field(name = "customer_id")
    private long customerId;

    @OneToOne
    @JoinColumn(name = "order_line_id")
    @JsonProperty("order_line")
    @Field(name = "order_line")
    private OrderLine orderLine;

}
