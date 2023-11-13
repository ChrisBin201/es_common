package com.chris.data.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"SKU","name"}, allowGetters = true)
public class ProductItemDTO {

    private Long id;
    private String name;
    private String SKU = UUID.randomUUID().toString();
    @JsonProperty("quantity_in_stock")
    private Long quantityInStock;
    private Double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MultipartFile preview;
//    @JsonProperty("variation_options_ids")
//    private List<Long> variationOptionIds;
    @JsonProperty("variant_option_codes")
    private List<String> variantOptionCodes;
}
