package org.example.shopping.payment.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("SELECT h FROM History h JOIN FETCH h.payment p WHERE p.id = :id")
    Optional<History> findByPaymentId(@Param("id") Long paymentId);
}
