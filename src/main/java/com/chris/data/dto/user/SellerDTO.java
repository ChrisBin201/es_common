package com.chris.data.dto.user;

import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SellerDTO extends UserDTO {
//    @NotBlank
    @JsonProperty("shop_name")
    private String shopName;
//    @NotBlank
    @JsonProperty("shop_description")
    private String shopDescription;
//    @NotNull
    @JsonProperty("shop_address")
    private CountryAddress shopAddress;

    @JsonProperty("payment_id")
    private long paymentId;

}
