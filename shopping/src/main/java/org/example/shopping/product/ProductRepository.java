package org.example.shopping.product;

import org.example.shopping.category.Category;
import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByStatus(ProductStatus status);

    List<Product> findByCategory(Category category);
}
