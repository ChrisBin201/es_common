package com.chris.data.view.product;

import com.chris.common.dto.specification.FieldType;
import com.chris.common.dto.specification.FilterOption;
import com.chris.common.dto.specification.Operator;
import com.chris.data.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Subselect("SELECT \n" +
        "            p.id, p.preview, p.name, p.description, p.status,max(pi.price) AS max_price, min(pi.price) AS min_price,\n" +
        "            c.id as category_id, " +
        "            SUM(oi.quantity) AS sales, \n" +
        "            ifnull(AVG(r.rating),0) AS rating_average,\n" +
        "            pr.discount_rate, pr.start_date, pr.end_date\n" +
        "        FROM\n" +
        "        product p \n" +
        "            LEFT JOIN (product_promotion p2_0 \n" +
        "                       JOIN promotion pr ON pr.id=p2_0.promotion_id) " +
        "                       ON p.id=p2_0.product_id \n" +
        "                       AND NOW() BETWEEN pr.start_date AND pr.end_date \n" +
        "            LEFT JOIN category c ON c.id = p.category_id " +
        "            LEFT JOIN product_item pi ON p.id = pi.product_id\n" +
        "            LEFT JOIN order_item_outer oi ON pi.id = oi.product_item_id\n" +
        "            LEFT JOIN rating_outer r ON oi.rating_id = r.id \n" +
        "        GROUP BY p.id")
@Synchronize({ "category", "promotion", "product", "order_item_outer", "rating_outer","product_item" })
@Immutable
@Where(clause = "status = 'ACTIVE'")
public class ProductSummary {
    @Id
    private long id;
    private String preview;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Product.ProductStatus status;
    private Long sales;
    @JsonProperty("rating_average")
    @Column(name = "rating_average")
    private Double ratingAverage;

    @JsonProperty("max_price")
    @Column(name = "max_price")
    private Double maxPrice;
    @JsonProperty("min_price")
    @Column(name = "min_price")
    private Double minPrice;

    @JsonProperty("category_id")
    @Column(name = "category_id")
    private Long categoryId;

    @JsonProperty("discount_rate")
    @Column(name = "discount_rate")
    private Double discountRate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("promotion_start_date")
    @Column(name = "start_date")
    private LocalDateTime promotionStartDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("promotion_end_date")
    @Column(name = "end_date")
    private LocalDateTime promotionEndDate;

//    public enum SORT_OPTIONS {
//        ID("id"),
//        NAME("name"),
//        SALES("sales"),
//        MAX_PRICE("maxPrice"),
//        MIN_PRICE("minPrice"),
//        RATING("ratingAverage");
//
//        public String field;
//        SORT_OPTIONS(String field) {
//            this.field = field;
//        }
//
//    }
//    public static String[] SORT_OPTIONS = {
//            "id",
//            "name",
//            "sales",
//            "maxPrice",
//            "minPrice",
//            "ratingAverage"
//    };

    public static Map<String, String> SORT_OPTIONS = Map.ofEntries(
            Map.entry("ID", "id"),
            Map.entry("NAME", "name"),
            Map.entry("SALES", "sales"),
            Map.entry("PRICE", "minPrice"),
            Map.entry("RATING", "ratingAverage")
    );


    public static FilterOption[] FILTER_OPTIONS = {
            new FilterOption("CATEGORY","categoryId",new  Operator[] {Operator.EQUAL}, FieldType.LONG),
            new FilterOption("PRICE","minPrice", new Operator[] {Operator.BETWEEN, Operator.GREATER_THAN_EQUAL, Operator.LESS_THAN_EQUAL}, FieldType.DOUBLE),
            new FilterOption("RATING","ratingAverage", new Operator[] {Operator.GREATER_THAN_EQUAL}, FieldType.DOUBLE)
    };



}
