package org.example.shopping.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.shopping.user.User;
import org.example.shopping.user.enums.Gender;

import java.time.LocalDate;

public class UserRequest {
    @Data
    public static class LoginDTO {
        @Size(min = 5, max = 10)
        @NotBlank(message = "id는 필수 입력 항목입니다.")
        private String username;

        @Size(min = 6, max = 16)
        @NotBlank(message = "pw는 필수 입력 항목입니다.")
        private String password;
    }

    // 회원가입
    @Data
    public static class SignUpDTO {
        @Size(min = 5, max = 255)
        @NotBlank(message = "id는 필수 입력 항목입니다.")
        private String username; // 필수

        @Size(min = 6, max = 16)
        @NotBlank(message = "pw는 필수 입력 항목입니다.")
        private String password; // 필수

        @Size(min = 2, max = 6)
        @NotBlank(message = "nickname은 필수 입력 항목입니다.")
        private String nickname; // 필수

        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        private String email;    // 필수

        @NotBlank(message = "주소는 필수 입력 항목입니다.")
        private String address;  // 필수

        @NotBlank(message = "연락처는 필수 입력 항목입니다.")
//        @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
        private String phoneNumber; // 필수

        private Gender gender; // 선택

//        @Pattern(regexp = "^(19\\d{2}|20\\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
//                message = "생년월일은 yyyy-MM-dd 형식이어야 합니다.")
        private LocalDate birthday; // 선택

        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .nickname(this.nickname)
                    .email(this.email)
                    .address(this.address)
                    .phoneNumber(this.phoneNumber)
                    .gender(this.gender)
                    .birthday(this.birthday)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        @Size(min = 6, max = 16)
        @NotBlank(message = "pw는 필수 입력 항목입니다.")
        private String password; // 필수

        @Size(min = 2, max = 6)
        @NotBlank(message = "nickname은 필수 입력 항목입니다.")
        private String nickname;

        @NotBlank(message = "주소는 필수 입력 항목입니다.")
        private String address;  // 필수

        private Gender gender; // 선택
    }
}

