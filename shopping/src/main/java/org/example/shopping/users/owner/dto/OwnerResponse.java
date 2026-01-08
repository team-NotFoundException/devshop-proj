package org.example.shopping.users.owner.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductResponse;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.enums.OwnerStatus;
import org.example.shopping.users.owner.Owner;
import org.example.shopping.users.user.Address;

import java.util.List;

public class OwnerResponse {

    @Data
    public static class OwnerList {

        private Long id;
        private String ownerName;
        private OwnerStatus ownerStatus;
        private long totalProduct;

        public OwnerList(Owner owner, long totalProduct) {
            this.id = owner.getId();
            this.ownerName = owner.getName();
            this.ownerStatus = owner.getStatus();
            this.totalProduct = getTotalProduct();
        }

        public boolean isAPPROVED() {
            return this.ownerStatus == OwnerStatus.APPROVED;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    public static class OwnerDetail extends UserResponse.UserDetail {
        private Long id;
        private String ownerName;
        private String email;
        private Address address;
        private String phoneNumber;
        private List<ProductResponse.ListDTO> productList;

        private OwnerStatus ownerStatus;

        public OwnerDetail(User user, Owner owner, List<ProductResponse.ListDTO> productList) {
            super(user);
            this.id = owner.getId();
            this.ownerName = owner.getName();
            this.email = user.getEmail();
            this.address = user.getAddress();
            this.phoneNumber = user.getPhoneNumber();
            this.productList = productList;
            this.ownerStatus = owner.getStatus();
        }

    }
}
