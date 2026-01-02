package org.example.shopping.users.user;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {

    @NotBlank(message = "우편번호는 필수입니다.")
    private String zipNo;

    @NotBlank(message = "도로명주소는 필수입니다.")
    private String roadAddr;

    @NotBlank(message = "상세 주소는  필수 입니다.")
    private String addrDetail;      // 상세주소 (선택)
    private String roadAddrPart2;   // 참고항목 (선택)
    private String jibunAddr;       // 지번주소 (선택)

    public Address(String zipNo, String roadAddr, String addrDetail) {
        this.zipNo = zipNo;
        this.roadAddr = roadAddr;
        this.addrDetail = addrDetail;
    }

    public Address(String zipNo, String roadAddr, String addrDetail,
                   String roadAddrPart2, String jibunAddr) {
        this.zipNo = zipNo;
        this.roadAddr = roadAddr;
        this.addrDetail = addrDetail;
        this.roadAddrPart2 = roadAddrPart2;
        this.jibunAddr = jibunAddr;
    }
}
