package org.example.shopping.product;

import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 전체 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.createdAt DESC")
    List<Product> findAllWithCategory();

    @Query("SELECT p FROM Product p JOIN FETCH p.owner o WHERE o.id = :id ORDER BY p.createdAt DESC")
    List<Product> findAllWithUser(@Param("id") Long ownerId);

    // 단건 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);

    // 상태별 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.status = :status")
    List<Product> findByStatusWithCategory(@Param("status") ProductStatus status);

    // 카테고리별 조회
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.id = :categoryId")
    List<Product> findByCategoryIdWithCategory(@Param("categoryId") Long categoryId);

    // Owner별 상품명 검색
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.owner o " +
            "WHERE o.id = :ownerId AND p.productName LIKE %:keyword% ORDER BY p.createdAt DESC")
    List<Product> searchByOwnerAndProductName(@Param("ownerId") Long ownerId, @Param("keyword") String keyword);

    // Owner별 상품명 + 상태 검색
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.owner o " +
            "WHERE o.id = :ownerId AND p.productName LIKE %:keyword% AND p.status = :status ORDER BY p.createdAt DESC")
    List<Product> searchByOwnerAndProductNameAndStatus(@Param("ownerId") Long ownerId,
                                                       @Param("keyword") String keyword,
                                                       @Param("status") ProductStatus status);

    // Owner별 상품명 + 카테고리 검색
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.owner o " +
            "WHERE o.id = :ownerId AND p.productName LIKE %:keyword% AND p.category.id = :categoryId ORDER BY p.createdAt DESC")
    List<Product> searchByOwnerAndProductNameAndCategory(@Param("ownerId") Long ownerId,
                                                         @Param("keyword") String keyword,
                                                         @Param("categoryId") Long categoryId);

    // 기존 검색 (전체)
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.productName LIKE %:keyword% ORDER BY p.createdAt DESC")
    List<Product> searchByProductNameWithCategory(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.productName LIKE %:keyword% AND p.status = :status ORDER BY p.createdAt DESC")
    List<Product> searchByProductNameAndStatusWithCategory(@Param("keyword") String keyword, @Param("status") ProductStatus status);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.productName LIKE %:keyword% AND p.category.id = :categoryId ORDER BY p.createdAt DESC")
    List<Product> searchByProductNameAndCategoryIdWithCategory(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

    List<Product> findByStatusOrderByCreatedAtDesc(ProductStatus status);

    List<Product> findByStatusAndCategoryIdOrderByCreatedAtDesc(ProductStatus status, Long categoryId);

    long countByOwnerId(Long ownerId);

    Optional<Product> findByOwnerId(Long ownerId);
    @Query("SELECT p FROM Product p JOIN FETCH p.owner WHERE p.status = :status AND p.owner.id = :id")
    List<Product> findByStatusWithUser(@Param("status") ProductStatus status, @Param("id") Long ownerId);
}