package org.example.shopping.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping._core.errors.exception.Exception500;
import org.example.shopping._core.utils.FileUtil;
import org.example.shopping.category.Category;
import org.example.shopping.category.CategoryRepository;
import org.example.shopping.product.productEnum.ProductStatus;
import org.example.shopping.review.ReviewRepository;
import org.example.shopping.users.owner.Owner;
import org.example.shopping.users.owner.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OwnerRepository ownerRepository;



    // 조회
    public List<ProductResponse.ListDTO> findAll(Long userId) {
        Owner owner = ownerRepository.findByUserId(userId).orElseThrow(() -> new Exception404("찾을수 없음"));
        return productRepository.findAllWithUser(owner.getId())
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
    public void save(ProductRequest.SaveDTO dto, Long userId, MultipartFile thumbnail) {

        dto.validate();

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));

        Owner owner = ownerRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("판매자 정보를 찾을 수 없습니다"));

        String savedFileName = null;

        if (thumbnail != null && !thumbnail.isEmpty()) {
            if (!FileUtil.isImageFile(thumbnail)) {
                throw new Exception400("이미지 파일만 업로드 가능합니다.");
            }
            try {
                savedFileName = FileUtil.saveFile(thumbnail);

                log.info("파일 저장 성공: {}", savedFileName);
            } catch (Exception e) {
                log.error("파일 저장 실패", e);
                throw new Exception500("썸네일 저장 실패");
            }
        }

        Product product = dto.toEntity(category, savedFileName, owner);
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

        log.info("저장정보확인: {} \n 저장카테고리: {}", dto, category);

        product.update(dto, category);

    }


    // 삭제
    @Transactional
    public void deleteById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        productRepository.delete(product);
    }

    public ProductResponse.UpdateFormDTO findByIdForUpdate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception404("상품을 찾을수 없습니다."));

        return new ProductResponse.UpdateFormDTO(product);
    }


    public List<ProductResponse.MainCardDTO> findAllForMain() {
        return productRepository.findByStatusOrderByCreatedAtDesc(ProductStatus.ACTIVE)
                .stream()
                .map(ProductResponse.MainCardDTO::new)
                .toList();
    }

    public long getOwnerProductCount(Long ownerId) {
        return productRepository.countByOwnerId(ownerId);
    }

    public List<ProductResponse.MainCardDTO> findByCategoryIdForMain(Long categoryId) {
        return productRepository.findByStatusAndCategoryIdOrderByCreatedAtDesc(ProductStatus.ACTIVE, categoryId)
                .stream()
                .map(ProductResponse.MainCardDTO::new)
                .toList();
    }

    public ProductResponse.UserDetailDTO findByIdForUser(Long id) {

        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        return new ProductResponse.UserDetailDTO(product);
    }

    // 상품명 검색
    public List<ProductResponse.ListDTO> searchByProductName(String keyword) {
        return productRepository.searchByProductNameWithCategory(keyword)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    // 상품명 + 상태 검색
    public List<ProductResponse.ListDTO> searchByProductNameAndStatus(String keyword, ProductStatus status) {
        return productRepository.searchByProductNameAndStatusWithCategory(keyword, status)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    // 상품명 + 카테고리 검색
    public List<ProductResponse.ListDTO> searchByProductNameAndCategoryId(String keyword, Long categoryId) {
        return productRepository.searchByProductNameAndCategoryIdWithCategory(keyword, categoryId)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }
}