package org.example.shopping.review;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.product.Product;
import org.example.shopping.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review create(ReviewRequest.SaveDTO saveDTO, User sessionsUser, Product product) {
        Review review = saveDTO.toEntity(sessionsUser, product);
        reviewRepository.save(review);
        return review;
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
                .orElseThrow(()-> new Exception404("리뷰를 찾을 수 없습니다"));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        return new ReviewResponse.UpdateFormDTO(reviewEntity);
    }

    /** 리뷰 수정로직 */
    @Transactional
    public Review updateReview(ReviewRequest.UpdateDTO updateDTO, Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new Exception404("리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        reviewEntity.update(updateDTO);
        return reviewEntity;
    }

    /** 리뷰 삭제 */
    @Transactional
    public void deleteReview(Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new Exception404("삭제할 리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.deleteById(reviewId);
    }
}
