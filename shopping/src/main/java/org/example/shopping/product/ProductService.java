package org.example.shopping.product;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.category.Category;
import org.example.shopping.category.CategoryRepository;
import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    // 조회
    public List<ProductResponse.ListDTO> findAll() {
        return productRepository.findAllWithCategory()
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    public ProductResponse.DetailDTO findById(Long id) {
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        return new ProductResponse.DetailDTO(product);
    }

    public List<ProductResponse.ListDTO> findByStatus(ProductStatus status) {
        return productRepository.findByStatusWithCategory(status)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    public List<ProductResponse.ListDTO> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryIdWithCategory(categoryId)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    // 등록
    @Transactional
    public void save(ProductRequest.SaveDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));

        Product product = dto.toEntity(category);
        productRepository.save(product);
    }


    // 수정
    @Transactional
    public void updateById(Long id, ProductRequest.UpdateDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));
        }

        product.update(dto, category);

    }


    // 삭제
    @Transactional
    public void deleteById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        productRepository.delete(product);
    }
}