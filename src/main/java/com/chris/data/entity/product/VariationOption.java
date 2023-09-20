package com.chris.data.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
//@Entity
//@Table(name="variation_option")
public class VariationOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Variation variation;
}
