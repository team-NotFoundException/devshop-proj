package org.example.shopping.review;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductRepository;
import org.example.shopping.product.ProductResponse;
import org.example.shopping.product.ProductService;
import org.example.shopping.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    // 리뷰 저장 화면 요청 (로그인 필요)
    // http://localhost:8080/review/save
    @GetMapping("/review/save")
    public String saveForm(
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인 먼저 해주세요");
        }

        return "user/mypage-reviewSave";
    }

    // 리뷰 저장 (로그인 필요)
    @PostMapping("/review/save")
    public String saveProc(
            ReviewRequest.SaveDTO saveDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        reviewService.create(saveDTO, sessionUser);
        return "redirect:/review/list";
    }

    // 리뷰 목록 조회
    // http://localhost:8080/review/list
    @GetMapping({"/review/list"})
    public String findAll(Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ReviewResponse.ListDTO> reviewList = reviewService.getReviews();

        model.addAttribute("reviewList", reviewList);
        return "user/mypage-reviewList";
    }

    // 리뷰 상세 조회
    // http://localhost:8080/review/1
    @GetMapping("/review/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        ReviewResponse.DetailDTO review = reviewService.getDetailView(id);

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
    @GetMapping("/review/{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ReviewResponse.UpdateFormDTO review = reviewService.updateReviewView(id, sessionUser.getId());

        model.addAttribute("review", review);
        return "user/mypage-reviewUpdate";
    }

    // 리뷰 수정 (로그인 필요)
    // http://localhost:8080/review/1/update
    @PostMapping("/review/{id}/update")
    public String updateProc(
            @PathVariable Long id,
            ReviewRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        updateDTO.validate();

        reviewService.updateReview(updateDTO, id, sessionUser.getId());

        return "redirect:/review/list";
    }

    // 리뷰 삭제 (로그인 필요)
    @PostMapping("/review/{id}/delete")
    public String delete(
            @PathVariable Long id,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        reviewService.deleteReview(id, sessionUser.getId());
        return "redirect:/review/list";
    }

    // 리뷰 이미지 삭제
    @PostMapping("/review/{id}/review-image/delete")
    public String deleteReviewImage(
            @PathVariable Long id,
            HttpSession session,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Review updateReview = reviewService.deleteReviewImage(id, sessionUser.getId());
        model.addAttribute("review", updateReview);
        return "redirect:/review/{id}";
    }
}