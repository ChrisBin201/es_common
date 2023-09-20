package com.chris.data.entity.user;

import jakarta.persistence.*;

import com.chris.data.entity.user.sub.CountryAddress;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "seller")
public class Seller extends User {
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "shop_description")
    private String shopDescription;
//    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
//    @JoinColumn(name = "shop_address_id")
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "shop_address",columnDefinition = "jsonb")
    private CountryAddress shopAddress;

}
