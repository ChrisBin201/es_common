package com.chris.data.elasticsearch;

import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "shop_info")
public class ShopInfo {
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

    @JsonProperty("rating_count")
    @Field(name = "rating_count")
    private long ratingCount;

    @JsonProperty("rating_average")
    @Field(name = "rating_average")
    private double ratingAverage;

    @JsonProperty("product_count")
    @Field(name = "product_count")
    private long productCount;



}
