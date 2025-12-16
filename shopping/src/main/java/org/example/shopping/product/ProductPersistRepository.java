package org.example.shopping.product;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.category.Category;
import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository // IoC 컨테이너 등록
public class ProductPersistRepository {

    private final EntityManager em;

    // 저장
    @Transactional
    public Product save(Product product) {
        em.persist(product);
        return product;
    }

    // 전체 조회
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p ORDER BY p.createdAt DESC", Product.class)
                .getResultList();
    }

    // 단건 조회
    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    // 상품 코드로 조회 (unique)
    public Product findByProductCode(String code) {
        List<Product> list = em.createQuery(
                        "SELECT p FROM Product p WHERE p.productCode = :code", Product.class)
                .setParameter("code", code)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    // 카테고리별 조회
    public List<Product> findByCategory(Category category) {
        return em.createQuery(
                        "SELECT p FROM Product p WHERE p.category = :category ORDER BY p.createdAt DESC",
                        Product.class)
                .setParameter("category", category)
                .getResultList();
    }

    // 상태별 조회
    public List<Product> findByStatus(ProductStatus status) {
        return em.createQuery(
                        "SELECT p FROM Product p WHERE p.status = :status ORDER BY p.createdAt DESC",
                        Product.class)
                .setParameter("status", status)
                .getResultList();
    }

    // 카테고리 + 상태 조회
    public List<Product> findByCategoryAndStatus(Category category, ProductStatus status) {
        return em.createQuery(
                        "SELECT p FROM Product p WHERE p.category = :category AND p.status = :status ORDER BY p.createdAt DESC",
                        Product.class)
                .setParameter("category", category)
                .setParameter("status", status)
                .getResultList();
    }

    // 수정
    @Transactional
    public Product updateById(Long id, ProductRequest.UpdateDTO dto) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            throw new Exception404("수정할 상품이 존재하지 않습니다.");
        }


        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setDescription(dto.getDescription());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setStatus(dto.getStatus());

        return product;
    }

    // 삭제
    @Transactional
    public void deleteById(Long id) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            throw new Exception404("삭제할 상품이 존재하지 않습니다.");
        }
        em.remove(product);
    }
}