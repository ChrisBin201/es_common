package com.chris.data.elasticsearch;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import com.chris.data.elasticsearch.sub.SaleInfo;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.chris.data.entity.product.*;
import com.chris.data.entity.product.sub.PromotionDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.response.SortBy;

import java.io.Serializable;
import java.util.List;

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

    private List<PromotionDetail> promotions;
    //    private List<Variation> variations;
    @JsonProperty("variations")
    private List<Variation> variations;
    @JsonProperty("product_items")
    @Field(name = "product_items")
//    private Set<ProductItem> productItems;
    private List<ProductItemDetail> productItems;
    @JsonProperty("rating_average")
    @Field(name = "rating_average")
    private double ratingAverage;
    private SaleInfo sales;

    public static ProductInfo from(Product product, List<Category> categoriesTree ) {
        return ProductInfo.builder()
                .id(product.getId())
                .preview(product.getPreview())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus())
                .categoriesTree(categoriesTree)
                .sellerId(product.getSellerId())
                .promotions(product.getPromotionList().stream().map(PromotionDetail::from).toList())
                .variations(product.getVariations())
                .productItems(product.getProductItems().stream().map(item -> ProductItemDetail.from(item,product)).toList())
                .sales(new SaleInfo(0,0))
                .build();
    }

//    public static ProductInfo from (Product product) {
//        return ProductInfo.builder()
//                .id(product.getId())
//                .preview(product.getPreview())
//                .name(product.getName())
//                .description(product.getDescription())
//                .status(product.getStatus())
//                .sellerId(product.getSellerId())
//                .promotions(product.getPromotionList())
//                .variations(product.getVariations())
//                .productItems(product.getProductItems())
//                .build();
//    }
    public static List<Pair<String, Sort>> SORT_FIELDS = List.of(
            Pair.of("id_asc", Sort.sort(ProductInfo.class).by(ProductInfo::getId).ascending()),
            Pair.of("id_desc", Sort.sort(ProductInfo.class).by(ProductInfo::getId).descending()),
            Pair.of("rating_asc",Sort.sort(ProductInfo.class).by(ProductInfo::getRatingAverage).ascending()),
            Pair.of("rating_desc",Sort.sort(ProductInfo.class).by(ProductInfo::getRatingAverage).descending()),
            Pair.of("sales_asc",Sort.by("sales.total_quantity").ascending()),
            Pair.of("sales_desc",Sort.by("sales.total_quantity").descending()),
            Pair.of("price_asc",Sort.by("product_items.price").ascending()),
            Pair.of("price_desc",Sort.by("product_items.price").descending())
    );
}
