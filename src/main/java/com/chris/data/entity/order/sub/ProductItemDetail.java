package com.chris.data.entity.order.sub;

import com.chris.data.entity.product.Product;
import com.chris.data.entity.product.ProductItem;
import com.chris.data.entity.product.sub.ProductDetail;
import com.chris.data.entity.product.sub.VariantOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDetail implements Serializable {
    private long id;
    private String name;
    //  example: code for each product with same size and color
    private String SKU = UUID.randomUUID().toString();

    private String preview;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_info",columnDefinition = "jsonb")
    @JsonProperty("product_info")
    @Field(name = "product_info")
    ProductDetail productInfo;

    @Column(name = "quantity_in_stock")
    @Field(name = "quantity_in_stock")
    @JsonProperty("quantity_in_stock")
    private long quantityInStock;
    private double price;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "variant_options",columnDefinition = "jsonb")
    @JsonProperty("variant_options")
    @Field(name = "variant_options")
    private List<VariantOption> variantOptions = new ArrayList<>();

    public static ProductItemDetail from(ProductItem productItem) {
        return ProductItemDetail.builder()
                .id(productItem.getId())
                .name(productItem.getName())
                .SKU(productItem.getSKU())
                .preview(productItem.getPreview())
                .productInfo(ProductDetail.from(productItem.getProduct()))
                .quantityInStock(productItem.getQuantityInStock())
                .price(productItem.getPrice())
                .variantOptions(productItem.getVariantOptions())
                .build();
    }

    public static ProductItemDetail from(ProductItem productItem, Product product) {
        return ProductItemDetail.builder()
                .id(productItem.getId())
                .name(productItem.getName())
                .SKU(productItem.getSKU())
                .preview(productItem.getPreview())
                .productInfo(ProductDetail.from(product))
                .quantityInStock(productItem.getQuantityInStock())
                .price(productItem.getPrice())
                .variantOptions(productItem.getVariantOptions())
                .build();
    }
}
