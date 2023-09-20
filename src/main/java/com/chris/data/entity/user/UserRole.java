package com.chris.data.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import com.chris.data.entity.Auditable;

@Data
@NoArgsConstructor
@Entity
@Table(name="user_role")
public class UserRole extends Auditable<String,UserRole> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole(User u, Role r){
        user = u;
        role = r;
    };
}
