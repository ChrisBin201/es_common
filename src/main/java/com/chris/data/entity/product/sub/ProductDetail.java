package com.chris.data.entity.product.sub;

import com.chris.data.entity.product.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail implements Serializable {
//    private Long id;
    private String preview;
    private String name;
    private String description;

    private List<Variation> variations = new ArrayList<>();

    private Category category;

    @JsonProperty("seller_id")
    private Long sellerId;

    public static ProductDetail from (Product product ) {
        ProductDetail productInfo = new ProductDetail();
        productInfo.setName(product.getName());
        productInfo.setPreview(product.getPreview());
        productInfo.setDescription(product.getDescription());
        productInfo.setCategory(product.getCategory());
        productInfo.setVariations(product.getVariations());
        productInfo.setSellerId(product.getSellerId());
        return productInfo;
    }

}
