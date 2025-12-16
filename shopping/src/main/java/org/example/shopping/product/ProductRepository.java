package org.example.shopping.product;

import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 전체 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.createdAt DESC")
    List<Product> findAllWithCategory();

    // 단건 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);

    // 상태별 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.status = :status")
    List<Product> findByStatusWithCategory(@Param("status") ProductStatus status);

    // 카테고리별 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.id = :categoryId")
    List<Product> findByCategoryIdWithCategory(@Param("categoryId") Long categoryId);
}