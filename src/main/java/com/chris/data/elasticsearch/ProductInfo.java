package com.chris.data.elasticsearch;

import com.chris.data.entity.product.*;
import com.chris.data.entity.product.sub.PromotionInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product_info")
public class ProductInfo implements Serializable {
    @Id
    private long id;
//    @Field(type = FieldType.Text, name = "preview")
    private String preview;
//    @Field(type = FieldType.Text, name = "name")
    private String name;
//    @Field(type = FieldType.Text, name = "description")
    private String description;
    @Field(type = FieldType.Text, name = "status")
    @Enumerated(EnumType.STRING)
    private Product.ProductStatus status;

    @JsonProperty("categories_tree")
    @Field(name = "categories_tree")
    private List<Category> categoriesTree;

    @JsonProperty("seller_id")
    @Field(name = "seller_id")
    private long sellerId;

    private List<PromotionInfo> promotions;
    //    private List<Variation> variations;
    @JsonProperty("variations")
    private List<Variation> variations;
    @JsonProperty("product_items")
    @Field(name = "product_items")
    private Set<ProductItem> productItems;
    //    private List<ProductItem> productItems;
    @JsonProperty("rating_average")
    @Field(name = "rating_average")
    private double ratingAverage;
    private long sales;

    public static ProductInfo from(Product product, List<Category> categoriesTree ) {
        return ProductInfo.builder()
                .id(product.getId())
                .preview(product.getPreview())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus())
                .categoriesTree(categoriesTree)
                .sellerId(product.getSellerId())
                .promotions(product.getPromotionList())
                .variations(product.getVariations())
                .productItems(product.getProductItems())
                .build();
    }

    public static List<String> SORT_FIELDS = List.of("id","name","rating_average","sales");
}
