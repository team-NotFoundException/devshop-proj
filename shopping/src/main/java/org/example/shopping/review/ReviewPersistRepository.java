package org.example.shopping.review;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewPersistRepository {

    private final EntityManager entityManager;
}
