package org.example.shopping.users.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByUserId(Long userId);

    @Query("select o from  Owner o where o.user.id = :userId")
    Optional<Owner> findByUserIdUseInterceptor(@Param("userId") Long userId);
}
