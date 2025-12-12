package org.example.shopping.user;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(@Size(min = 5, max = 10) @NotBlank(message = "id는 필수 입력 항목입니다.") String username, @Size(min = 6, max = 16) @NotBlank(message = "pw는 필수 입력 항목입니다.") String password);

    User findByUsername(@Size(min = 5, max = 10) @NotBlank(message = "id는 필수 입력 항목입니다.") String username);

    // 로그인

    // 로그아웃

    // 회원가입

    // 회원정보 수정

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)

    // 유저 권한 부여

    // 유저 권한 회수


}
