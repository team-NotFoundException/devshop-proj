package org.example.shopping.user.dto;

import lombok.Data;
import org.example.shopping.user.enums.Gender;

import java.util.Date;

public class UserRequest {
    @Data
    public static class LoginDTO {
        private String username;
        private String password;

        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("id는 비어있을 수 없습니다.");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
            }
        }
    }

    // 회원가입
    @Data
    public static class SignUp {
        private String username; // 필수
        private String password; // 필수
        private String nickname; // 필수


        private String email;    // 필수
        private String address;  // 필수
        private String phoneNumber; // 필수
        private Gender gender; // 선택
        private Date birthday; // 선택

        public void validate() {
            if(username == null  || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자명을 입력해주세요");
            }
            if(password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호를 입력해주세요");
            }
            if(email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("이메일을 입력해주세요");
            }
            if(email.contains("@") == false) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
            }
            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("주소는 비어있을 수 없습니다.");
            }
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("연락처는 비어있을 수 없습니다.");
            }

        }
    }

    public static class UpdateDTO {
        private String password; // 필수
        private String nickname;
        private String address;  // 필수
        private Gender gender; // 선택
        private Date birthday;


    }

}
