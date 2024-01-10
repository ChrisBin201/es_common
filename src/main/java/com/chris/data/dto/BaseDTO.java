package com.chris.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(value = {"created_by", "created_date", "last_modified_by", "last_modified_date"}, allowGetters = true)
public class BaseDTO<T> {
    @JsonProperty("created_by")
    public T createdBy;

    @JsonProperty("created_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Transient
    public LocalDateTime createdDate;

    @JsonProperty("last_modified_by")
    public T lastModifiedBy;

    @JsonProperty("last_modified_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Transient
    public LocalDateTime lastModifiedDate;


//    @JsonProperty("created_date")
//    @Field(name = "created_date")
//    public long createdDate;
//
//    @JsonProperty("last_modified_date")
//    @Field(name = "last_modified_date")
//    public long lastModifiedDate;
}
