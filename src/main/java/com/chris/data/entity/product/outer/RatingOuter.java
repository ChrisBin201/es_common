package com.chris.data.entity.product.outer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.chris.data.entity.Auditable;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="rating_outer")
public class RatingOuter extends Auditable<String,OrderItemOuter> implements Serializable {
    @Id
    private Long id;

    @Max(5)
    @Min(1)
    private int rating;
    @Column(name = "order_item_id")
    private Long orderItemId;

    @OneToOne(mappedBy = "ratingOuter")
    private OrderItemOuter orderItemOuter;
}
