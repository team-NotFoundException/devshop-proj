package org.example.shopping.users.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Address {

    private String roadAddr;
    private String detailAddr;
    private String zipNo;

    private String siNm;
    private String sggNm;
    private String emdNm;

    public Address(String roadAddr, String detailAddr, String zipNo, String siNm, String sggNm, String emdNm) {
        this.roadAddr = roadAddr;
        this.detailAddr = detailAddr;
        this.zipNo = zipNo;
        this.siNm = siNm;
        this.sggNm = sggNm;
        this.emdNm = emdNm;
    }
}
