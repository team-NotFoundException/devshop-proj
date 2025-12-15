package org.example.shopping.product;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.category.Category;
import org.example.shopping.product.productEnum.ProductStatus;
import org.example.shopping.user.dto.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;
    private final ProductPersistRepository productRepository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new Exception404("상품이 없습니다"));

    }

    public void save(ProductRequest.SaveDTO dto, Category category) {
        Product product = dto.toEntity(category);
        repository.save(product);
    }

    public void updateById(Long id, ProductRequest.UpdateDTO dto) {
        Product product = findById(id);
        if (product == null) {
            throw new Exception404("상품 을 찾을 수 없습니다");
        }
        product.update(dto);
    }

    public void deleteById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new Exception404("삭제할 상품이 존재하지 않습니다"));
        repository.delete(product);
    }

    public List<Product> findByStatus(ProductStatus status) {

        List<Product> products = repository.findByStatus(status);

        return products;
    }

    public List<Product> findByCategory(Category category) {

        List<Product> products = repository.findByCategory(category);

        return products;
    }
}
