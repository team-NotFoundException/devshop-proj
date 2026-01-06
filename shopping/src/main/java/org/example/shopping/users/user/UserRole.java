package org.example.shopping.users.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.shopping.users.User;
import org.example.shopping.users.enums.RoleType;

@Entity
@Table(name = "user_role_tb", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_role", columnNames = {"user_id", "role"})})
@NoArgsConstructor
@Getter
public class UserRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_role_user"))
    private User user;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public UserRole(User user, RoleType role) {
        this.user = user;
        this.role = role;
    }
}
