package org.example.shopping.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartPersistRepository extends JpaRepository<Cart, Long> {

}
