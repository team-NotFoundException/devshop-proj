package org.example.shopping.users;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping._core.utils.BaseTimeEntity;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.enums.Gender;
import org.example.shopping.users.enums.OAuthProvider;
import org.example.shopping.users.enums.RoleType;
import org.example.shopping.users.user.Address;
import org.example.shopping.users.user.UserRole;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@Data
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 255)
    @NotBlank(message = "id는 필수 입력 항목입니다.")
    private String username;

//    @Size(min = 6, max = 16)
    @NotBlank(message = "pw는 필수 입력 항목입니다.")
    private String password;

    @Size(min = 2, max = 6)
    @NotBlank(message = "nickname은 필수 입력 항목입니다.")
    private String nickname;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @Embedded
    private Address address;

    @NotBlank(message = "연락처는 필수 입력 항목입니다.")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "user")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) @ColumnDefault("'LOCAL'")
    private OAuthProvider provider;

    @Builder
    public User(Long id, String username, String password, String nickname, String  email, Address address, String phoneNumber, Gender gender, LocalDate birthday, OAuthProvider provider) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
        this.provider = (provider == null) ? OAuthProvider.LOCAL : provider;
    }

    public boolean isWriter(Long userId) {
        return this.id.equals(userId);
    }

    public void update(@Valid UserRequest.UpdateDTO updateDTO) {
        this.password = updateDTO.getPassword();
        this.address = updateDTO.getAddress();
        this.nickname = updateDTO.getNickname();
        this.gender = updateDTO.getGender();
    }

    public boolean isMale() {
        return Gender.M.equals(this.gender);
    }

    public boolean isFemale() {
        return Gender.F.equals(this.gender);
    }

    public boolean isNone() {
        return Gender.N.equals(this.gender) || this.gender == null;
    }

    public boolean hasRole(RoleType role) {
        return this.role != null;
    }

    public boolean isAdmin() {
        return hasRole(RoleType.ADMIN);
    }

    public boolean isOwner() {
        return hasRole(RoleType.OWNER);
    }

    public boolean isUser() {
        return hasRole(RoleType.USER);
    }

    public boolean getIsAdmin() {
        return isAdmin();
    }

    public boolean getIsOwner() {
        return isOwner();
    }

    public boolean getIsUser() {
        return isUser();
    }





}
