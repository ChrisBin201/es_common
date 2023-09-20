package com.chris.data.dto.user;

import com.chris.data.entity.user.Address;
import com.chris.data.entity.user.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CountryAddressDTO  {
    @JsonProperty("country_id")
    long countryId;
    @NotNull
    @JsonProperty("address_ids")
    List<Long> addressIds;
    @NotBlank
    String detail;

}
