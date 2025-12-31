package org.example.shopping.users.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Address {

    private String zipNo;
    private String roadAddr;
    private String addrDetail;

    public Address(String zipNo, String roadAddr, String addrDetail) {
        this.zipNo = zipNo;
        this.roadAddr = roadAddr;
        this.addrDetail = addrDetail;
    }
}
