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
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@Data
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;


    private String password;


    private String nickname;


    private String email;

    @Embedded
    private Address address;


    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreationTimestamp
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

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean hasRole(RoleType roleType) {
        return this.role != null && this.role.getRole() == roleType;
    }

    public boolean isAdmin() {
        return hasRole(RoleType.ADMIN);
    }

    public String getRoleDisplay() {
        return isAdmin() ? "ADMIN" : "USER";
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
