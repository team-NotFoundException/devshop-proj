package org.example.shopping.users.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.users.User;
import org.example.shopping.users.user.UserRole;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

public class UserResponse {

    @Data
    public static class UserList {
        private Long id;
        private String username;
        private String nickname;
        private String email;
        private UserRole role;
        private LocalDateTime createdAt;

        public UserList(User user){
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.role = user.getRole();
            if(user.getCreatedAt() != null){
                this.createdAt = user.getCreatedAt();
            }
        }
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


    @Data
    public class JusoResponseDTO {
        private String inputYn;          // 주소 선택 여부
        private String roadFullAddr;     // 전체 도로명주소
        private String roadAddrPart1;    // 도로명주소(참고항목 제외)
        private String roadAddrPart2;    // 도로명주소 참고항목
        private String engAddr;          // 도로명주소(영문)
        private String jibunAddr;        // 지번주소
        private String zipNo;            // 우편번호
        private String addrDetail;       // 고객 입력 상세주소
        private String admCd;            // 행정구역코드
        private String rnMgtSn;          // 도로명코드
        private String bdMgtSn;          // 건물관리번호
        private String detBdNmList;      // 상세건물명

        // 2017년 2월 추가 제공 항목
        private String bdNm;             // 건물명
        private String bdKdcd;           // 공동주택여부 (1:공동주택, 0:비공동주택)
        private String siNm;             // 시도명
        private String sggNm;            // 시군구명
        private String emdNm;            // 읍면동명
        private String liNm;             // 법정리명
        private String rn;               // 도로명
        private String udrtYn;           // 지하여부 (0:지상, 1:지하)
        private String buldMnnm;         // 건물본번
        private String buldSlno;         // 건물부번
        private String mtYn;             // 산여부 (0:대지, 1:산)
        private String lnbrMnnm;         // 지번본번(번지)
        private String lnbrSlno;         // 지번부번(호)
        private String emdNo;            // 읍면동일련번호

        public String buildRedirectUrl() throws UnsupportedEncodingException {
            StringBuilder url = new StringBuilder("redirect:/popup/juso?inputYn=Y");

            appendParam(url, "roadFullAddr", roadFullAddr);
            appendParam(url, "roadAddrPart1", roadAddrPart1);
            appendParam(url, "roadAddrPart2", roadAddrPart2);
            appendParam(url, "engAddr", engAddr);
            appendParam(url, "jibunAddr", jibunAddr);
            appendParam(url, "zipNo", zipNo);
            appendParam(url, "addrDetail", addrDetail);
            appendParam(url, "admCd", admCd);
            appendParam(url, "rnMgtSn", rnMgtSn);
            appendParam(url, "bdMgtSn", bdMgtSn);
            appendParam(url, "detBdNmList", detBdNmList);
            appendParam(url, "bdNm", bdNm);
            appendParam(url, "bdKdcd", bdKdcd);
            appendParam(url, "siNm", siNm);
            appendParam(url, "sggNm", sggNm);
            appendParam(url, "emdNm", emdNm);
            appendParam(url, "liNm", liNm);
            appendParam(url, "rn", rn);
            appendParam(url, "udrtYn", udrtYn);
            appendParam(url, "buldMnnm", buldMnnm);
            appendParam(url, "buldSlno", buldSlno);
            appendParam(url, "mtYn", mtYn);
            appendParam(url, "lnbrMnnm", lnbrMnnm);
            appendParam(url, "lnbrSlno", lnbrSlno);
            appendParam(url, "emdNo", emdNo);

            return url.toString();
        }

        private void appendParam(StringBuilder url, String key, String value)
                throws UnsupportedEncodingException {
            if (value != null && !value.isEmpty()) {
                url.append("&")
                        .append(key)
                        .append("=")
                        .append(URLEncoder.encode(value, "UTF-8"));
            }
        }
    }
}
