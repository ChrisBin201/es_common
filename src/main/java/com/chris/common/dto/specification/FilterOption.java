package com.chris.common.dto.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterOption {
    public String key;
    public String field;
    public Operator[] operators;
    public FieldType type;
}
