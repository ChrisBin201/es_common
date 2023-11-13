package com.chris.data.elasticsearch.sub;

import com.chris.data.dto.user.SellerDTO;
import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetail {
    private long id;
    @Field(name = "shop_name")
    @JsonProperty("shop_name")
    private String shopName;
    @JsonProperty("shop_description")
    @Field(name = "shop_description")
    private String shopDescription;

    @JsonProperty("shop_address")
    @Field(name = "shop_address")
    private CountryAddress shopAddress;

    public static ShopDetail from (SellerDTO sellerDTO){
        return ShopDetail.builder()
                .id(sellerDTO.getId())
                .shopName(sellerDTO.getShopName())
                .shopDescription(sellerDTO.getShopDescription())
                .shopAddress(sellerDTO.getShopAddress())
                .build();
    }
}
