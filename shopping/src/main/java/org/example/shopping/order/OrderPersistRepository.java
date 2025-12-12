package org.example.shopping.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPersistRepository extends JpaRepository<Order, Long> {

}
