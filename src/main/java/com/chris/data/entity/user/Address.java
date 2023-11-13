package com.chris.data.entity.user;

import jakarta.persistence.*;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Address extends Auditable<String,Address> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
//    private String street;
//    @Column(name = "sub_district")
//    private String subDistrict;
//    private String district;
//    private String city;
//    private String detail;

    @Enumerated(EnumType.STRING)
    private AddressType type;

    private String code;

    private String name;

    @Column(name = "level_index")
    @JsonProperty("level_index")
    private int levelIndex;

    @Column(name = "parent_code")
    @JsonProperty("parent_code")
    private String parentCode;

    @ManyToOne
    @JsonIgnore
    private Country country;

    @Getter
    @RequiredArgsConstructor
    public enum AddressType {
        PROVINCE("PROVINCE"),
        DISTRICT("DISTRICT"),
        WARD("WARD"),
        REGION("REGION"),
        TOWNSHIP("TOWNSHIP"),
        CITY("CITY"),
        POSTAL_CODE("POSTAL_CODE");

        private final String type;

        public static AddressType fromType(String type) {
            for (AddressType e : AddressType.values()) {
                if (e.getType().equals(type)) {
                    return e;
                }
            }
            return null;
        }
    }
}
