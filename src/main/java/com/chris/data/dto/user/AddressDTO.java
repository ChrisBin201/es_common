package com.chris.data.dto.user;

import com.chris.data.dto.BaseDTO;
import lombok.Data;

@Data
public class AddressDTO extends BaseDTO<String> {
    private String id;
    private String street;
    private String subDistrict;
    private String district;
    private String city;
    private String country;
    private String detail;

}
