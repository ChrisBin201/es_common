package com.chris.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    public boolean error;
    public String message;
//    public Integer status;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime timestamp;
    public T data;

    @JsonProperty("paging_data")
    public Page<T> pagingData;

    public ResponseData() {
        this.timestamp = LocalDateTime.now();
    }

    public ResponseData(boolean success, String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
//        this.status = success ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        error = !success ;
    }

    public void success() {
        this.message = "Successful";
//        this.status = HttpStatus.OK.value();
//        error = false;
    }

    public void initData(T data) {
        this.data = data;
        this.success();
    }
    public void error(String message){
//        status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        error = true;
        this.message = message;
    }
}

