package org.example.shopping.users.user;

import org.example.shopping.users.User;
import org.example.shopping.users.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    void deleteUserById(Long id);

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT 3")
    List<User> findAllOrderByCreatedAt();

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT :limit")
    List<User> findTopUsers(@Param("limit") int limit);

    Predicate<? super Owner> findByRole(UserRole role);

}
