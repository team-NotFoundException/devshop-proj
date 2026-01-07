package org.example.shopping.users.owner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.shopping.users.dto.UserRequest;

public class OwnerRequest {

    @Data
    public static class OwnerSignUpDTO extends UserRequest.SignUpDTO {
        @NotBlank(message = "입점 명은 비어있을 수 없습니다.")
        private String ownerName;
    }
}
