package org.example.shopping.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {



    // 카테고리 전체 조회

    @Query("SELECT c " +
            "FROM Category c " +
            "LEFT JOIN FETCH c.parent " +
            "ORDER BY c.depth ASC, c.displayOrder ASC, c.displayOrder ASC nulls last ")
    List<Category> findAllWithParent();


    // 카테고리 단건 조회
    @Query("SELECT c " +
            "FROM Category c " +
            "LEFT JOIN FETCH c.parent " +
            "WHERE c.id = :id ")
    Optional<Category> findByIdWithParent(@Param("id") Long id);


    // 특정 상위 카테고리의 하위 카테고리 조회
    @Query("SELECT c " +
            "FROM Category c " +
            "LEFT JOIN FETCH c.parent " +
            "WHERE c.parent.id = :parentId " +
            "ORDER BY c.displayOrder ASC")
    List<Category> findByParentId(@Param("parentId") Long parentId);


    // 최상위 카테고리 조회
    @Query("SELECT c " +
            "FROM Category c" +
            " WHERE c.depth = 1 " +
            "ORDER BY c.displayOrder ASC ")
    List<Category> findRootCategories();

    @Query("SELECT c FROM Category  c WHERE  c.depth = :depth")
    List<Category> findByDepth(@Param("depth") int depth);

}