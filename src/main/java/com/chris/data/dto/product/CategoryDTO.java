package com.chris.data.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import com.chris.data.dto.BaseDTO;
import lombok.Data;

@Data
public class CategoryDTO extends BaseDTO<String> {
    private long id;
    @NotBlank(message = "Name is required")
    private String name;
    @JsonProperty("parent_id")
    private long parentId;
}
