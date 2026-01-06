package org.example.shopping.users.dto;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.shopping._core.utils.ValidationGroups;
import org.example.shopping.users.User;
import org.example.shopping.users.enums.Gender;
import org.example.shopping.users.user.Address;

import java.time.LocalDate;

public class UserRequest {

    @Data
    public static class LoginDTO {
        @NotBlank(message = "아이디를 입력해주세요.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 5, max = 16, message = "아이디는 5~16자 사이입니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 6, max = 16, message = "비밀번호는 6~16자 사이입니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String password;
    }

    @Data
    public static class SignUpDTO {
        @NotBlank(message = "아이디는 필수 입력 항목입니다.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 5, max = 16, message = "아이디는 5에서 16자 사이여야합니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String username;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 6, max = 16, message = "비밀번호는 6에서 16자 사이여야합니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String password;

        @NotBlank(message = "닉네임은 필수 입력 항목입니다.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 2, max = 6, message = "닉네임은 2에서 6자 사이여야합니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String nickname;

        @NotBlank(message = "이메일은 필수 입력 항목입니다.", groups = ValidationGroups.NotEmptyGroup.class)
        @Email(message = "유효한 이메일 주소를 입력해주세요.", groups = ValidationGroups.EmailCheckGroup.class)
        private String email;

        @Valid
        @NotNull(message = "주소 정보를 입력해주세요.", groups = ValidationGroups.NotEmptyGroup.class)
        private Address address;

        @NotBlank(message = "연락처는 필수 입력 항목입니다.", groups = ValidationGroups.NotEmptyGroup.class)
        @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
                message = "올바른 연락처 형식이 아닙니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String phoneNumber;

        private Gender gender;
        private LocalDate birthday;

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
        @NotBlank(message = "기존 비밀번호 또는 새 비밀번호를 입력해주세요.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 6, max = 16, message = "비밀번호는 6에서 16자 사이여야합니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String password;

        @NotBlank(message = "변경할 닉네임을 입력해주세요.", groups = ValidationGroups.NotEmptyGroup.class)
        @Size(min = 2, max = 6, message = "닉네임은 2에서 6자 사이여야합니다.", groups = ValidationGroups.PatternCheckGroup.class)
        private String nickname;

        @NotNull(message = "주소는 필수 입력 항목입니다.", groups = ValidationGroups.NotEmptyGroup.class)
        @Valid
        private Address address;

        private Gender gender;
    }
}

