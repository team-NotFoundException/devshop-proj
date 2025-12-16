package org.example.shopping.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role_tb", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_role", columnNames = {"user_id", "role"})})
@NoArgsConstructor
@Getter
public class UserRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_role_user"))
    private User user;

    @Enumerated(EnumType.STRING)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "role_name"
//            , referencedColumnName = "role_name"
//            , foreignKey = @ForeignKey(name = "fk_user_role_role"))
    private Role role;

    public UserRole(Long id, Role role) {
        this.id = id;
        this.role = role;
    }
}
