package org.example.shopping.user;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.shopping.user.enums.RoleType;

@Entity
@Table(name = "role_tb")
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoleType role;
}
