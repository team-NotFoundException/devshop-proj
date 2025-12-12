package org.example.shopping.review;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReviewPersistRepository {

    private final EntityManager entityManager;

    @Transactional
    public Review save(Review review) {
        entityManager.persist(review);
        return review;
    }

    public List<Review> findAll() {
        return entityManager
                .createQuery("SELECT r FROM Review r ORDER BY r.createdAt DESC")
                .getResultList();
    }

    public Review findById(Long id) {
        Review review = entityManager.find(Review.class, id);
        return review;
    }

    @Transactional
    public Review updateById(Long id, ReviewRequest.UpdateDTO reqDTO) {
        Review review = entityManager.find(Review.class, id);

        if (review == null) {
            throw new IllegalArgumentException("수정할 리뷰를 찾을 수 없어요.");
        }

        review.update(reqDTO);
        return review;
    }

    @Transactional
    public void deleteById(Long id) {
        Review review = entityManager.find(Review.class, id);

        if (review == null) {
            throw new IllegalArgumentException("삭제할 리뷰를 찾을 수 없어요");
        }

        entityManager.remove(review);
    }
}
