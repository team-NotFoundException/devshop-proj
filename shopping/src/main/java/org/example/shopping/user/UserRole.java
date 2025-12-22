package org.example.shopping.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.shopping.user.enums.RoleType;

@Entity
@Table(name = "user_role_tb", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_role", columnNames = {"user_id", "role"})
})
@NoArgsConstructor
@Getter
public class UserRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_role_user"))
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public UserRole(Long id, RoleType role) {
        this.id = id;
        this.role = role;
    }
}
