package com.chris.data.dto.product;

import com.chris.data.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
//    private String preview;
    private String name;
    private String description;
    private Product.ProductStatus status;
//    private List<Variation> variationList;
//    private Set<ProductItem> productItemList;
    private List<VariationDTO> variations;
    @JsonProperty("product_items")
    private List<ProductItemDTO> productItems;
    private CategoryDTO category;
    @JsonProperty("seller_id")
    private Long sellerId;
//    private Long adminId;
}
