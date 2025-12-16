package org.example.shopping.category;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 전체 조회 (부모 카테고리 포함)
     */
    public List<CategoryResponse.ListDTO> findAll() {
        return categoryRepository.findAllWithParent()
                .stream()
                .map(CategoryResponse.ListDTO::new)
                .toList();
    }

    /**
     * 카테고리 단건 조회
     */
    public CategoryResponse.DetailDTO findById(Long id) {
        Category category = categoryRepository.findByIdWithParent(id)
                .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));

        return new CategoryResponse.DetailDTO(category);
    }

    /**
     * 부모 카테고리 기준 하위 카테고리 조회
     */
    public List<CategoryResponse.ListDTO> findByParentId(Long parentId) {
        return categoryRepository.findByParentId(parentId)
                .stream()
                .map(CategoryResponse.ListDTO::new)
                .toList();
    }

    /**
     * 최상위 카테고리 조회
     */
    public List<CategoryResponse.ListDTO> findRootCategories() {
        return categoryRepository.findRootCategories()
                .stream()
                .map(CategoryResponse.ListDTO::new)
                .toList();
    }

    /**
     * 카테고리 등록
     */
    @Transactional
    public void save(CategoryRequest.SaveDTO dto) {
        Category parent = null;

        if (dto.getParentId() != null) {
            parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new Exception404("부모 카테고리를 찾을 수 없습니다"));
        }

        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .depth(dto.getDepth())
                .displayOrder(dto.getDisplayOrder())
                .parent(parent)
                .build();

        categoryRepository.save(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public void updateById(Long id, CategoryRequest.UpdateDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));

        category.setCategoryName(dto.getCategoryName());
        category.setDisplayOrder(dto.getDisplayOrder());
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));

        categoryRepository.delete(category);
    }
}