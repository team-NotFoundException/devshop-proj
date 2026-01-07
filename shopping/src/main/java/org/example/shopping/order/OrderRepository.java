package org.example.shopping.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order>  findByUserId(Long userId);

    @Query("SELECT DISTINCT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt")
    List<Order> findAllByUserIdAndOrderByCreatedAtDesc(@Param("userId") Long userId);

    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.orderItems WHERE o.id = :orderId ORDER BY o.createdAt DESC ")
    Optional<Order> findByIdWithOrderItems(@Param("orderId") Long orderId);
}
