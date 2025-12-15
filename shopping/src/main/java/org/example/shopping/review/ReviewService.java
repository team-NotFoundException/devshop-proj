package org.example.shopping.review;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review 리뷰작성(ReviewRequest.SaveDTO saveDTO, User sessionsUser) {
        Review review = saveDTO.toEntity(sessionsUser);
        return reviewRepository.save(review);
    }

    public List<Review> 리뷰조회() {
        return reviewRepository.findAll();
    }

    public Review 리뷰상세조회(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception404("리뷰를 찾을 수 없습니다."));
    }

    /** 리뷰 수정화면 */
    // 1. 리뷰 조회
    // 2. 인가 처리
    public Review 리뷰수정화면(Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new Exception404("리뷰를 찾을 수 없습니다"));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("리뷰 수정 권한이 없습니다.");
        }

        return reviewEntity;
    }

    /** 리뷰 수정로직 */
    // 1. 트랜잭션 처리
    // 2. DB 에서 조회
    // 3. 인가 처리
    // 4. 조회된 review 에 상태값 변경 (더티 체킹)
    @Transactional
    public void 리뷰수정(ReviewRequest.UpdateDTO updateDTO, Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new Exception404("리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("리뷰 수정 권한이 없습니다.");
        }

        reviewEntity.update(updateDTO);
    }

    /** 리뷰 삭제 */
    @Transactional
    public void 리뷰삭제(Long reviewId, Long sessionUserId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new Exception404("리뷰를 찾을 수 없습니다."));

        if (!reviewEntity.isOwner(sessionUserId)) {
            throw new Exception403("리뷰 삭제 권한이 없습니다.");
        }

        reviewRepository.deleteById(reviewId);
    }
}
