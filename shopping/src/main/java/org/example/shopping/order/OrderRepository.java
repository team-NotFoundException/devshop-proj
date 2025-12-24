package org.example.shopping.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query(value = "SELECT DISTINCT o FROM Order o JOIN FETCH o.payment ORDER BY o.createdAt DESC ",
//           countQuery = "SELECT COUNT(DISTINCT O) FROM Order o")
//    List<Order> findAllOrderByCreatedAt();
}
