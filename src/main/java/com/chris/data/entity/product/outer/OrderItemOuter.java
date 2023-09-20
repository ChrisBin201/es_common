package com.chris.data.entity.product.outer;

import com.chris.data.entity.Auditable;
import com.chris.data.entity.product.ProductItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="order_item_outer")
public class OrderItemOuter extends Auditable<String,OrderItemOuter> implements Serializable {
    @Id
    private Long id;
    private long quantity;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @JsonIgnore
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id")
    private RatingOuter ratingOuter;

}
