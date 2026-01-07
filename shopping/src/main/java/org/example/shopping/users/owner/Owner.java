package org.example.shopping.users.owner;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.users.User;
import org.example.shopping.users.enums.OwnerStatus;

@Entity
@Table(name = "user_owner_tb")
@Data
@NoArgsConstructor
public class Owner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnerStatus status;

    @Builder
    public Owner(Long id, User user, String name, OwnerStatus status) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.status = status;
    }
}
