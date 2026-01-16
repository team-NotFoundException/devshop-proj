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
                if (savedFileName == null) {
                    log.warn("파일 저장 결과가 null입니다. 파일이 비어있거나 이미지가 아닙니다.");
                } else {
                    log.info("파일 저장 완료: {}", savedFileName);
                }
            } catch (Exception e) {
                log.error("파일 저장 실패: {}", e.getMessage());
                throw new Exception500("썸네일 저장 실패");
            }
        }

        Product product = dto.toEntity(category, savedFileName, owner);
        productRepository.save(product);
    }

    // 수정
    @Transactional
    public void updateById(Long id, ProductRequest.UpdateDTO dto, MultipartFile thumbnail) {
        log.info("=== 상품 수정 시작 ===");
        log.info("상품 ID: {}", id);
        log.info("DTO: {}", dto);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        log.info("수정 전 상품명: {}", product.getProductName());
        log.info("수정 전 가격: {}", product.getPrice());

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new Exception404("카테고리를 찾을 수 없습니다"));
        }

        // 새 썸네일이 있으면 저장
        if (thumbnail != null && !thumbnail.isEmpty()) {
            if (!FileUtil.isImageFile(thumbnail)) {
                throw new Exception400("이미지 파일만 업로드 가능합니다.");
            }
            try {
                String savedFileName = FileUtil.saveFile(thumbnail);
                if (savedFileName != null) {
                    dto.setThumbnailUrl(savedFileName);
                    log.info("새 썸네일 저장 완료: {}", savedFileName);
                }
            } catch (Exception e) {
                log.error("썸네일 저장 실패: {}", e.getMessage());
                throw new Exception500("썸네일 저장 실패");
            }
        }

        product.update(dto, category);

        log.info("수정 후 상품명: {}", product.getProductName());
        log.info("수정 후 가격: {}", product.getPrice());
        log.info("=== 상품 수정 완료 ===");
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

    // Owner별 상품명 검색
    public List<ProductResponse.ListDTO> searchByOwnerAndProductName(Long ownerId, String keyword) {
        return productRepository.searchByOwnerAndProductName(ownerId, keyword)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    // Owner별 상품명 + 상태 검색
    public List<ProductResponse.ListDTO> searchByOwnerAndProductNameAndStatus(Long ownerId, String keyword, ProductStatus status) {
        return productRepository.searchByOwnerAndProductNameAndStatus(ownerId, keyword, status)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

    // Owner별 상품명 + 카테고리 검색
    public List<ProductResponse.ListDTO> searchByOwnerAndProductNameAndCategory(Long ownerId, String keyword, Long categoryId) {
        return productRepository.searchByOwnerAndProductNameAndCategory(ownerId, keyword, categoryId)
                .stream()
                .map(ProductResponse.ListDTO::new)
                .toList();
    }

}