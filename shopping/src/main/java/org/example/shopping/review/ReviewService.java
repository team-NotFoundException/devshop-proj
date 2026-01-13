package org.example.shopping.review;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping._core.errors.exception.Exception500;
import org.example.shopping._core.utils.FileUtil;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductRepository;
import org.example.shopping.users.User;
import org.example.shopping.users.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Transactional
    public void createReview(Long productId, ReviewRequest.SaveDTO saveDTO, User sessionUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception404("상품이 없음"));

        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("로그인 상태가 아닙니다."));

        String reviewImageFileName = null;

        if (saveDTO.getReviewImage() != null && !saveDTO.getReviewImage().isEmpty()) {
            try {
                if (!FileUtil.isImageFile(saveDTO.getReviewImage())) {
                    throw new Exception400("이미지 파일만 업로드 가능합니다.");
                }
                reviewImageFileName = FileUtil.saveFile(saveDTO.getReviewImage());
            } catch (Exception e) {
                throw new Exception500("서버 오류입니당");
            }
        }

        Review newReview = saveDTO.toEntity(user, product, reviewImageFileName);
        reviewRepository.save(newReview);
    }

    public List<ReviewResponse.ListDTO> getReviews(Long sessionUserId) {
        List<Review> reviewList = reviewRepository.findByUserIdWithProduct(sessionUserId);
        return reviewList.stream()
                .map(ReviewResponse.ListDTO::new)
                .collect(Collectors.toList());
    }


    // 특정 상품의 리뷰 조회
    public List<ReviewResponse.InProductReviewDTO> reviewInProduct(Long productId) {
        List<Review> inProductReview = reviewRepository.findByProductId(productId);
        return inProductReview.stream()
                .map(ReviewResponse.InProductReviewDTO::new)
                .collect(Collectors.toList());
    }

    public ReviewResponse.DetailDTO getDetailView(Long reviewId) {
        Review review = reviewRepository.findByIdWithUser(reviewId)
                .orElseThrow(() -> new Exception404("리뷰를 찾을 수 없습니다."));

        return new ReviewResponse.DetailDTO(review);
    }

    public ReviewResponse.UpdateFormDTO updateReviewView(Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findByIdWithUser(reviewId)
                .orElseThrow(() -> new Exception404("리뷰를 찾을 수 없습니다"));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        return new ReviewResponse.UpdateFormDTO(reviewEntity);
    }

    /**
     * 리뷰 수정로직
     */
    @Transactional
    public Review updateReview(ReviewRequest.UpdateDTO updateDTO, Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception404("리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        MultipartFile image = updateDTO.getReviewImage();
        if (image != null && !image.isEmpty()) {
            String savedFileName = null;
            try {
                savedFileName = FileUtil.saveFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            updateDTO.setProfileImageFilename(savedFileName);
        }

        reviewEntity.update(updateDTO);
        return reviewEntity;
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void deleteReview(Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception404("삭제할 리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public Review deleteReviewImage(Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception404("리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        String reviewImage = reviewEntity.getReviewImage();
        if (reviewImage != null && reviewImage.isEmpty()) {
            try {
                FileUtil.deleteFile(reviewImage);
            } catch (IOException e) {
                System.err.println("리뷰 이미지 삭제 실패");
            }
        }

        // 객체 상태값 변경 (트랜잭션 끝나는 시점 / 더티 체킹)
        reviewEntity.setReviewImage(null);
        return reviewEntity;

    }


//    public ReviewResponse.RatingStatisticsDTO findByGetRating(Long productId) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new Exception404("상품을 찾을 수 없어요"));
//
//        long totalReviews = reviewRepository.countReviewByProductId(productId);
//
//        long firstCount = reviewRepository.countReviewByProductIdWithRating(productId, 5);
//        long secondCount = reviewRepository.countReviewByProductIdWithRating(productId, 4);
//        long thirdCount = reviewRepository.countReviewByProductIdWithRating(productId, 3);
//        long forthCount = reviewRepository.countReviewByProductIdWithRating(productId, 2);
//        long fifthCount = reviewRepository.countReviewByProductIdWithRating(productId, 1);
//
//
//        return null;
//    }
}
