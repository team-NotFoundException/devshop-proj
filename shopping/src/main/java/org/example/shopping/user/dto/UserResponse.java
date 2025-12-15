package org.example.shopping.user.dto;

import lombok.Data;
import org.example.shopping.user.UserRole;

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

}
