package com.chris.data.dto.product.req;

import com.chris.data.dto.product.ProductItemDTO;
import com.chris.data.dto.product.VariationDTO;
import com.chris.data.entity.product.Variation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUploadDTO {
    private Long id;
//    @NotNull
//    private List<MultipartFile> previewList;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
//    @NotNull
//    @JsonProperty("seller_id")
//    private Long sellerId;
    @NotNull
    @JsonProperty("category_id")
    private Long categoryId;
    @NotNull
    private List<Variation> variations = new ArrayList<>();
    @NotNull
    @JsonProperty("product_items")
    private List<ProductItemDTO> productItems = new ArrayList<>();
}
