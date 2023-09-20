package com.chris.data.entity.user;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country_address_type")
public class CountryAddressType extends Auditable<String,CountryAddressType> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    private Country country;

    @Enumerated(EnumType.STRING)
    private Address.AddressType type;

    @JsonProperty("level_index")
    @Column(name = "level_index")
    private int levelIndex;

    private String title;

    private String placeholder;
}
