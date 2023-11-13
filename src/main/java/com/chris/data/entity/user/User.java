package com.chris.data.entity.user;

import com.chris.data.elasticsearch.ProductInfo;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.chris.data.entity.Auditable;
import org.springframework.data.domain.Sort;

@Entity
@Data
@Table(name = "user_info")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET status = 'DELETED' WHERE id=?")
//@Where(clause = "status='ACTIVE'")
public class User extends Auditable<String,User> implements Serializable {
//        implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String avatar;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String fullname;
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserRole> userRoles = new ArrayList<>();

    public void addRole (Role role) {
        UserRole userRole = new UserRole(this, role);
        userRoles.add(userRole);
//        role.getUserRoles().add(userRole);
    }

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roles.add(userRole.getRole());
        }
        return roles;
    }

    public static enum UserStatus {
        ACTIVE, BLOCK, DELETED
    }

    public static List<Pair<String, Sort>> SORT_FIELDS = List.of(
            Pair.of("id_asc", Sort.by("id").ascending()),
            Pair.of("id_desc", Sort.by("id").descending()),
            Pair.of("name_asc",Sort.by("fullname").ascending()),
            Pair.of("name_desc",Sort.by("fullname").descending())
    );
}
