package org.example.shopping.review;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.utils.ValidationGroups;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductService;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    // 리뷰 저장 화면 요청 (로그인 필요)
    // http://localhost:8080/review/save
    @GetMapping("/review/{productId}/save")
    public String saveForm(@PathVariable Long productId, Model model,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        model.addAttribute("productId", productId);

        return "review/save-form";
    }


    @PostMapping("/review/{productId}/save")
    public String saveReview(@PathVariable Long productId, @Validated(ValidationGroups.ReviewOrderGroup.class) ReviewRequest.SaveDTO saveDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        reviewService.createReview(productId, saveDTO, sessionUser);

        return "redirect:/review/list";
    }


    // 특정 상품에 대한 리뷰 목록 조회
    @GetMapping("/products/{productId}/review")
    public String reviewInProduct(@PathVariable Long productId ,Model model) {
        List<ReviewResponse.InProductReviewDTO> inProductReviewList = reviewService.reviewInProduct(productId);

        model.addAttribute("inProductReviewList", inProductReviewList);
        return "/review/review-in-product";
    }

//    @GetMapping("/products/{productId}/statistics")
//    public String ratingStatistics(@PathVariable Long productId, Model model) {
//        ReviewResponse.RatingStatisticsDTO statisticsDTO = reviewService.findByGetRating(productId);
//
//        model.addAttribute("statistic", statisticsDTO);
//        return "statistics/rating-in-products";
//    }


    // 내 리뷰 목록 조회
    // http://localhost:8080/review/list
    @GetMapping("/review/list")
    public String findAll(Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ReviewResponse.ListDTO> reviewList = reviewService.getReviews(sessionUser.getId());

        model.addAttribute("reviewList", reviewList);
        return "review/list";
    }

    // 리뷰 상세 조회
    // http://localhost:8080/review/1
    @GetMapping("/review/{reviewId}")
    public String findById(@PathVariable Long reviewId, Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        ReviewResponse.DetailDTO review = reviewService.getDetailView(reviewId);

        boolean isOwner = false;
        if (sessionUser != null && review.getUserId() != null) {
            isOwner = review.getUserId().equals(sessionUser.getId());
        }

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("review", review);
        return "user/mypage-reviewDetail";
    }

    // 리뷰 수정 화면 요청 (로그인 필요)
    // http://localhost:8080/review/1/update
    @GetMapping("/review/{reviewId}/update")
    public String updateForm(@PathVariable Long reviewId, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ReviewResponse.UpdateFormDTO review = reviewService.updateReviewView(reviewId, sessionUser.getId());

        model.addAttribute("review", review);
        return "user/mypage-reviewUpdate";
    }

    // 리뷰 수정 (로그인 필요)
    // http://localhost:8080/review/1/update
    @PostMapping("/review/{reviewId}/update")
    public String updateProc(
            @PathVariable Long reviewId,
            ReviewRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        updateDTO.validate();

        reviewService.updateReview(updateDTO, reviewId, sessionUser.getId());

        return "redirect:/review/list";
    }

    // 리뷰 삭제 (로그인 필요)
    @PostMapping("/review/{reviewId}/delete")
    public String delete(
            @PathVariable Long reviewId,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        reviewService.deleteReview(reviewId, sessionUser.getId());
        return "redirect:/review/list";
    }

    // 리뷰 이미지 삭제
    @PostMapping("/review/{reviewId}/review-image/delete")
    public String deleteReviewImage(
            @PathVariable Long reviewId,
            HttpSession session,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Review updateReview = reviewService.deleteReviewImage(reviewId, sessionUser.getId());
        model.addAttribute("review", updateReview);
        return "redirect:/review/{id}";
    }
}