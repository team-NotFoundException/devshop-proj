package org.example.shopping.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.shopping.user.enums.Gender;
import org.example.shopping.utils.BaseTimeEntity;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    private String address;

    private String phoneNumber;

    private Gender gender;

    private Date birthday;

}
