package com.chris.data.elasticsearch;

import com.chris.data.dto.BaseDTO;
import com.chris.data.elasticsearch.sub.CustomerDetail;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.Rating;
import com.chris.data.entity.user.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "rating_info")
public class RatingInfo extends BaseDTO<String> {
    @Id
    private long id;
    private CustomerDetail customer;
    @JsonProperty("order_line")
    @Field(name = "order_line")
    private OrderLine orderLine;
    private long rating;
    private String message;

    public static RatingInfo from (CustomerDetail customer, Rating rating) {
        RatingInfo ratingInfo = RatingInfo.builder()
                .id(rating.getId())
                .customer(customer)
                .orderLine(rating.getOrderLine())
                .rating(rating.getRating())
                .message(rating.getMessage())
                .build();

        ratingInfo.setCreatedBy(rating.getCreatedBy());
        ratingInfo.setCreatedDate(rating.getCreatedDate());
        ratingInfo.setLastModifiedBy(rating.getLastModifiedBy());
        ratingInfo.setLastModifiedDate(rating.getLastModifiedDate());
        return ratingInfo;
    }
}
