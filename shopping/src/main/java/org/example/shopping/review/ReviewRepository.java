package org.example.shopping.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 작성자 포함 리스트 조회
    @Query("SELECT r FROM Review r JOIN FETCH r.user ORDER BY r.createdAt DESC")
    List<Review> findAllWithUserOrderByCreatedAtDesc();

    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.id = :id")
    Optional<Review> findByIdWithUser(@Param("id") Long id);
}
