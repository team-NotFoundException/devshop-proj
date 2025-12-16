package org.example.shopping.review;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductRepository;
import org.example.shopping.product.ProductService;
import org.example.shopping.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductRepository productRepository;

    // 리뷰 저장 화면 요청 (로그인 필요)
    // http://localhost:8080/products/1/review/save
    @GetMapping("/products/{productId}/review/save")
    public String saveForm(
            @PathVariable Long productId,
            HttpSession session,
            Model model
    ) {
//        User sessionUser = (User)session.getAttribute("sessionUser");
//        if (sessionUser == null) {
//            throw new Exception401("로그인 먼저 해주세요");
//        }
        model.addAttribute("productId", productId);
        return "review/save-form";
    }

    // 리뷰 저장 (로그인 필요)
    @PostMapping("/products/{productId}/review/save")
    public String saveProc(
            @PathVariable Long productId,
            ReviewRequest.SaveDTO saveDTO,
            HttpSession session
    ) {
        User sessionUser = (User)session.getAttribute("sessionUser");

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new Exception404("주문 상품을 찾을 수 없습니다."));

        reviewService.create(saveDTO, sessionUser, product);
        return "redirect:/products/{productId}/review/list";
    }

    // 리뷰 목록 조회
    // http://localhost:8080/review/list
    @GetMapping({"/products/{productId}/review/list"})
    public String findAll(Model model) {
        List<ReviewResponse.ListDTO> reviewList = reviewService.getReviews();
        model.addAttribute("reviewList", reviewList);
        return "review/list";
    }

    // 리뷰 상세 조회
    // http://localhost:8080/review/1
    @GetMapping("/products/{productId}/review/{id}")
    public String findById(@PathVariable Long productId, Model model, HttpSession session) {
        ReviewResponse.DetailDTO review = reviewService.getDetailView(productId);

        model.addAttribute("review", review);
        return "review/detail";
    }

    // 리뷰 수정 화면 요청 (로그인 필요)
    // http://localhost:8080/review/1/update
    @GetMapping("/products/{productId}/review/{id}/update")
    public String updateForm(@PathVariable Long productId, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ReviewResponse.UpdateFormDTO review = reviewService.updateReviewView(productId, sessionUser.getId());

        model.addAttribute("review", review);
        return "review/update-form";
    }

    // 리뷰 수정 (로그인 필요)
    // http://localhost:8080/review/1/update
    @PostMapping("/products/{productId}/review/{id}/update")
    public String updateProc(
            @PathVariable Long productId,
            ReviewRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        updateDTO.validate();

        Review review = reviewService.updateReview(updateDTO, productId, sessionUser.getId());

        return "redirect:/products/{productId}/review/list";
    }

    // 리뷰 삭제 (로그인 필요)
    @PostMapping("/products/{productId}/review/{id}/delete")
    public String delete(
            @PathVariable Long productId,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        reviewService.deleteReview(productId, sessionUser.getId());
        return "redirect:/products/{productId}/review/list";
    }
}