package com.chris.data.entity.product.sub;

import com.chris.data.entity.product.Variation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariantOption implements Serializable {
    private String code; //SKU_VAR_VO
    private String value;
}
