package com.chris.data.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class VariationDTO {

//    private Long id;
    private String name;
    private String code;
    @JsonProperty("variant_options")
    private List<VariationOptionDTO> variantOptions;
}
