package com.chris.common.dto.criteria;

import java.io.Serializable;

//@Getter
public enum SearchOperation implements Serializable {
    CONTAINS(0,"cn"),
    DOES_NOT_CONTAIN(1,"nc"),
    EQUAL(2,"eq"),
    NOT_EQUAL(3,"ne"),
    BEGINS_WITH(4,"bw"),
    DOES_NOT_BEGIN_WITH(5,"bn"),
    ENDS_WITH(6,"ew"),
    DOES_NOT_END_WITH(7,"en"),
    NUL(8,"nu"),
    NOT_NULL(9,"nn"),
    GREATER_THAN(10,"gt"),
    GREATER_THAN_EQUAL(11,"ge"),
    LESS_THAN(12,"lt"),
    LESS_THAN_EQUAL(13,"le"),
    ANY(14,"any"),
    ALL(15,"all");

    private int value;
    private String name;

    public static final String[] SIMPLE_OPERATION_SET = {
            "cn", "nc", "eq", "ne", "bw", "bn", "ew",
            "en", "nu", "nn", "gt", "ge", "lt", "le", ":", ">", "<"
    };

    SearchOperation(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SearchOperation getDataOption(final String dataOption) {
        switch (dataOption) {
            case "all": return ALL;
            case "any": return ANY;
            default: return null;
        }
    }

    public static SearchOperation getSimpleOperation(final String input) {
        switch (input){
            case "cn": return CONTAINS;
            case "nc": return DOES_NOT_CONTAIN;
            case "eq":
            case ":" :
                return EQUAL;
            case "ne": return NOT_EQUAL;
            case "bw": return BEGINS_WITH;
            case "bn": return DOES_NOT_BEGIN_WITH;
            case "ew": return ENDS_WITH;
            case "en": return DOES_NOT_END_WITH;
            case "nu": return NUL;
            case "nn": return NOT_NULL;
            case "gt":
            case ">" :
                return GREATER_THAN;
            case "ge": return GREATER_THAN_EQUAL;
            case "lt": return LESS_THAN;
            case "le":
            case "<" :
                return LESS_THAN_EQUAL;
            default: return null;
        }
    }
}
