package org.example.shopping.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    // 마이페이지 - 내가 쓴 리뷰 목록에 주문 상품 정보 까지 가져오기
//    @Query("SELECT r FROM Review r " +
//            "JOIN FETCH r.orderItem oi " +
//            "JOIN FETCH oi.product p " +
//            "WHERE r.user.id = :userId " +
//            "ORDER BY r.createdAt DESC")
//    List<Review> findMyReviewsWithProductOrderByCreatedAtDesc(@Param("userId") Long userId);

    // 리뷰 작성자 포함 리스트 조회
    @Query("SELECT r FROM Review r JOIN FETCH r.user ORDER BY r.createdAt DESC")
    List<Review> findAllWithUserOrderByCreatedAtDesc();

    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.id = :id")
    Optional<Review> findByIdWithUser(@Param("id") Long id);

    List<Review> findByProductId(Long id);
}
