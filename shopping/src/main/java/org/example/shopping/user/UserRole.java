package org.example.shopping.user;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role_tb")
@NoArgsConstructor
public class UserRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_role_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "role_name"
//            , referencedColumnName = "role_name"
//            , foreignKey = @ForeignKey(name = "fk_user_role_role"))
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
