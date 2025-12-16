package org.example.shopping.category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class CategoryPersistRepository {

    private final EntityManager entityManager;

    @Transactional
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category findById(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            throw new Exception404("해당 ID의 카테고리를 찾을 수 없어요");
        }
        return category;
    }

    @Transactional
    public Category update(Category category) {
        return category;
    }

    @Transactional
    public void delete(Long id) {
        Category category = findById(id);
        entityManager.remove(category);
    }
}
