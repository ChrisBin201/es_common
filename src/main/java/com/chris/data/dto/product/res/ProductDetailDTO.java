package com.chris.data.dto.product.res;

import com.chris.data.entity.product.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private long id;
    private String preview;
    private String name;
    private String description;
    private Product.ProductStatus status;
    @JsonProperty("categories_tree")
    private List<Category> categoriesTree;
//    private SellerDTO sellerDTO;
    private Promotion promotion;
//    private List<Variation> variations;
    @JsonProperty("variations")
    private List<Variation> variationList;
    @JsonProperty("product_items")
    private List<ProductItem> productItemList;
//    private List<ProductItem> productItems;
    @JsonProperty("rating_average")
    private Double ratingAverage;
    private Long sales;

}
