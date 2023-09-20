package com.chris.data.dto.user.req;

import com.chris.data.dto.user.CountryAddressDTO;
import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SignupSellerReq extends Signup {

    @NotBlank
    @JsonProperty("shop_name")
    private String shopName;
    @NotNull
    @JsonProperty("shop_address")
    private CountryAddressDTO shopAddress;
    @NotBlank
    @JsonProperty("shop_description")
    private String shopDescription;



}
