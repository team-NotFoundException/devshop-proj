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
    }

}
