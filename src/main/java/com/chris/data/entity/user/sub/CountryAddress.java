package com.chris.data.entity.user.sub;

import com.chris.data.entity.user.Address;
import com.chris.data.entity.user.Country;
import lombok.*;

import java.io.Serializable;
import java.util.List;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryAddress implements Serializable {
    Country country;
    List<Address> addresses;
    String detail;

}
