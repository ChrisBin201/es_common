package com.chris.data.entity.user;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country")
public class Country extends Auditable<String,Country> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(name = "country_code")
    @JsonProperty("country_code")
    private String countryCode;

    @Column(name = "country_name")
    @JsonProperty("country_name")
    private String countryName;

}
