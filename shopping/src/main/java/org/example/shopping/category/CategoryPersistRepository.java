package org.example.shopping.category;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryPersistRepository {

    private final EntityManager entityManager;

    // 카테고리 저장
    @Transactional
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

    // 카테고리 전체 조회 (정렬 포함)
    public List<Category> findAll() {
        return entityManager.createQuery(
                "SELECT c FROM Category c ORDER BY c.depth ASC, c.displayOrder ASC",
                Category.class
        ).getResultList();
    }

    // 카테고리 단건 조회
    public Category findById(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            throw new Exception404("카테고리를 찾을 수 없습니다");
        }
        return category;
    }

    // 최상위 카테고리 조회 (depth = 0)
    public List<Category> findRootCategories() {
        return entityManager.createQuery(
                "SELECT c FROM Category c WHERE c.depth = 0 ORDER BY c.displayOrder ASC",
                Category.class
        ).getResultList();
    }

    // 특정 부모 카테고리의 자식 카테고리 조회
    public List<Category> findChildrenByParentId(Long parentId) {
        return entityManager.createQuery(
                        "SELECT c FROM Category c WHERE c.parent.categoryId = :parentId ORDER BY c.displayOrder ASC",
                        Category.class
                )
                .setParameter("parentId", parentId)
                .getResultList();
    }

    // 카테고리 수정
    @Transactional
    public Category updateById(Long id, String categoryName, int displayOrder) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            throw new Exception404("수정할 카테고리를 찾을 수 없습니다");
        }

        category.setCategoryName(categoryName);
        category.setDisplayOrder(displayOrder);
        // 더티 체킹
        return category;
    }

    // 카테고리 삭제
    @Transactional
    public void deleteById(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            throw new Exception404("삭제할 카테고리가 없습니다");
        }
        entityManager.remove(category);
    }
}