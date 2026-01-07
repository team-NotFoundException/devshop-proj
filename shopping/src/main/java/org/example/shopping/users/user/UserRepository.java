package org.example.shopping.users.user;

import org.example.shopping.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    void deleteUserById(Long id);

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT 3")
    List<User> findAllOrderByCreatedAt();

    // 로그인

    // 로그아웃

    // 회원가입

    // 회원정보 수정

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)
//    @Query("SELECT u.username, u.nickname, u.email, u.role FROM User u LEFT JOIN UserRole r ON r.user_id = :u.user_id ORDER BY u.createdAt ASC")
//    Optional<User> findAllByUsernameAndRole

    // 유저 권한 부여

    // 유저 권한 회수


}
