package com.chris.data.entity.product;

import com.chris.data.entity.product.sub.ProductDetail;
import com.chris.data.entity.product.sub.VariantOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="product_item")
public class ProductItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    //  example: code for each product with same size and color
    private String SKU = UUID.randomUUID().toString();
    @Column(name = "quantity_in_stock")
    @Field(name = "quantity_in_stock")
    @JsonProperty("quantity_in_stock")
    private long quantityInStock;
    private double price;
    private String preview;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Product product;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_info",columnDefinition = "jsonb")
    @JsonProperty("product_info")
    @Field(name = "product_info")
    ProductDetail productInfo;
//    @JoinTable(
//            name = "product_config",
//            uniqueConstraints = @UniqueConstraint(
//                    columnNames = {"product_item_id", "variation_option_id"}),
//            joinColumns = @JoinColumn(
//                    name = "product_item_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "variation_option_id", referencedColumnName = "id"))
//    private List<VariationOption> variationOptions = new ArrayList<>();

//    @Type(value = JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "variant_options",columnDefinition = "jsonb")
    @JsonProperty("variant_options")
    @Field(name = "variant_options")
    private List<VariantOption> variantOptions = new ArrayList<>();

    public void addVariantOption(VariantOption option){
        this.variantOptions.add(option);
    }

    public void removeVariantOption(VariantOption option){
        this.variantOptions.remove(option);
    }

}
