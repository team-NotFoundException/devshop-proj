package org.example.shopping.review;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping._core.utils.FileUtil;
import org.example.shopping.product.ProductRepository;
import org.example.shopping.users.User;
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

    @Transactional
    public Review create(ReviewRequest.SaveDTO saveDTO, User sessionsUser) {
        String reviewImageFileName = null;

        // 이미지가 선택되었으면~
        if (saveDTO.getReviewImage() != null) {
            try {

                /**
                 * TODO !!
                 * 이미지 선택 안하고 리뷰 작성하면 예외 터져서 아래 주석처리함
                 * - 후에 처리해야함.
                 * */
//                if (!FileUtil.isImageFile(saveDTO.getReviewImage())) {
//                    throw new Exception400("이미지 파일만 업로드 가능합니다.");
//                }
                reviewImageFileName = FileUtil.saveFile(saveDTO.getReviewImage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Review review = saveDTO.toEntity(sessionsUser, reviewImageFileName);

        return reviewRepository.save(review);
    }

    public List<ReviewResponse.ListDTO> getReviews() {
        List<Review> reviewList = reviewRepository.findAllWithUserOrderByCreatedAtDesc();
        return reviewList.stream()
                .map(ReviewResponse.ListDTO::new)
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
}
