package com.chris.data.dto.user;

import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.chris.data.dto.BaseDTO;

@Data
public class ShopDTO extends BaseDTO<String> {
    @JsonProperty("shop_name")
    private String shopName;
    private String description;
    @JsonProperty("shop_address")
    private CountryAddress shopAddress;
}
