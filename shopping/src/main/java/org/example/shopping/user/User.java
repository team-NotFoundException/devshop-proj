package org.example.shopping.user;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.shopping.utils.BaseTimeEntity;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String address;

    private String phoneNumber;


    public void signUp() {

    }
}
