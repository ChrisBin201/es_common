package com.chris.data.entity.product;

import com.chris.data.entity.product.sub.VariantOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Variation implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String name;
//    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
//    @JoinColumn(name = "variation_id")
//    private List<VariationOption> variationOptions = new ArrayList<>();
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Type(value = JsonBinaryType.class)
//    @Column(name = "variation_options",columnDefinition = "jsonb")
    @JsonProperty("variant_options")
    @Field(name = "variant_options")
    private List<VariantOption> variantOptions = new ArrayList<>();

//    public void addVariationOption(VariationOption variationOption){
//        variationOptions.add(variationOption);
//        variationOption.setVariation(this);
//    }
//
//    public void removeVariationOption(VariationOption variationOption){
//        variationOptions.remove(variationOption);
//        variationOption.setVariation(null);
//    }
}
