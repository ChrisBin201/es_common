package com.chris.data.dto.user.req;

import com.chris.data.dto.user.CountryAddressDTO;
import com.chris.data.entity.user.Customer;
import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
@Data
public class SignupCustomerReq extends Signup {

    @NotNull
    private CountryAddressDTO address;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private long dob;
    private Customer.Gender gender;
}
