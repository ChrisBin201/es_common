package com.chris.data.entity.user;

import com.chris.data.entity.user.sub.CountryAddress;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customer")
public class Customer extends User{
//    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
//    private Address address;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "address",columnDefinition = "jsonb")
    private CountryAddress address;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dob;
    private String gender;
}
