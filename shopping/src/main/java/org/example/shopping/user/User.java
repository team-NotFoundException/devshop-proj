package org.example.shopping.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.example.shopping.user.enums.Gender;
import org.example.shopping.utils.BaseTimeEntity;

import java.util.Date;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 10)
    @NotBlank(message = "id는 필수 입력 항목입니다.")
    private String username;

    @Size(min = 6, max = 16)
    @NotBlank(message = "pw는 필수 입력 항목입니다.")
    private String password;

    @Size(min = 2, max = 6)
    @NotBlank(message = "nickname은 필수 입력 항목입니다.")
    private String nickname;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String address;

    @NotBlank(message = "연락처는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber;

    private Gender gender;

    private Date birthday;

}
