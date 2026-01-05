package org.example.shopping.users.user;

import org.example.shopping.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // === 공통 === //

    Optional<User> findByUsername(String username);


    // 로그인

    // 로그아웃

    // 회원가입

    // 회원정보 수정


    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)

    // 유저 권한 부여

    // 유저 권한 회수

    // === OWNER 용 === //



    // === 관리자용 === //
//    Optional<User> findAllByUsernameOrderByCreatedAt(String username, Pageable pageable);


//    @Query("SELECT u.username, u.nickname, u.email, u.role FROM User u LEFT JOIN UserRole r ON r.user_id = :u.user_id ORDER BY u.createdAt ASC")
    //    Optional<User> findAllByUsernameAndRole
}
