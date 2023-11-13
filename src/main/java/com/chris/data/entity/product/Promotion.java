package com.chris.data.entity.product;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "promotion")
public class Promotion extends Auditable<String,Promotion> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Max(1)
    @Min(0)
    @Column(name = "discount_rate")
    @JsonProperty("discount_rate")
    private Double discountRate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "start_date")
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "end_date")
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    private boolean active;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(
            name = "product_promotion",
            joinColumns = @JoinColumn(
                    name = "promotion_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"))
    private List<Product> productList;


}
