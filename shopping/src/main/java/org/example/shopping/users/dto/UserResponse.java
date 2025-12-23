package org.example.shopping.users.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.example.shopping.users.user.UserRole;

public class UserResponse {

    @Data
    public static class UserList {
        private String username;
        private String nickname;
        private String email;
        private UserRole role;
    }

//    @Data
//    public static class UserDetail {
//        private String username;
//        private String nickname;
//        private String email;
//        private String address;
//        private UserRole role;
//    }

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OAuthToken {
        private String tokenType;
        private String accessToken;
        private String expiresIn;
        private String refreshToken;
        private String refreshTokenExpiresIn;
    }

    // 카카오
    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoProfile {
        private Long id;
        private String connectedAt;
        private Properties properties;
        private KakaoAccount kakaoAccount;
    }

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        // 이름
        private Boolean nameNeedsAgreement;
        private String name;

        // 이메일
        private Boolean emailNeedsAgreement;
        private Boolean isEmailValid;
        private Boolean isEmailVerified;
        private String email;

        // 생년
        private Boolean birthyearNeedsAgreement;
        private String birthyear;

        // 생일
        private Boolean birthdayNeedsAgreement;
        private String birthday;
        private String birthdayType;
        private Boolean isLeapMonth;

        // 성별
        private Boolean genderNeedsAgreement;
        private String gender;

        // 전화번호
        private Boolean phoneNumberNeedsAgreement;
        private String phoneNumber;
    }

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Properties {
        private String nickname;
    }

}
