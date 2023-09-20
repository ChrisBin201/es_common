package com.chris.data.entity.user;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.chris.data.entity.Auditable;

@Entity
@Data
@Table(name = "user_info")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET status = 'DELETED' WHERE id=?")
@Where(clause = "status='ACTIVE'")
public class User extends Auditable<String,User> implements Serializable {
//        implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
}
