package com.chris.data.entity.user;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import com.chris.data.entity.Auditable;

@Data
@NoArgsConstructor
@Entity
@Table(name="role_info")
public class Role extends Auditable<String,Role> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @JsonIgnore
    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserRole> userRoles = new ArrayList<>();

    public Role (RoleName name) {
        this.name = name;
    }

    public static enum RoleName {
        ADMIN, CUSTOMER, SELLER
    }
}
