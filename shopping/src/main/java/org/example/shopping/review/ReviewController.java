package org.example.shopping.review;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
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

    private final ReviewRepository reviewRepository;

    // 리뷰 저장 화면 요청
    // http://localhost:8080/review/save
    @GetMapping("/review/save")
    public String saveForm(HttpSession session) {
        User sessionUser = (User)session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인 먼저 해주세요");
        }
        return "review/save-form";
    }

    // 리뷰 저장 요청 (기능 요청)
    // http://localhost:8080/review/save

    @PostMapping("/review/save")
    public String saveProc(
            ReviewRequest.SaveDTO saveDTO,
            HttpSession session
    ) {
        // 1. 인증 처리 확인
        User sessionUser = (User)session.getAttribute("sessionUser");

        Review review = saveDTO.toEntity(sessionUser);
        reviewRepository.save(review);
        return "redirect:/";
    }

    // 리뷰 조회
    // http://localhost:8080/review/list
    @GetMapping({"/review/list", "/"})
    public String findAll(Model model) {
        List<Review> reviewList = reviewRepository.findAll();
        model.addAttribute("reviewList", reviewList);
        return "review/list";
    }

    // 리뷰 상세 조회
    // http://localhost:8080/review/1
    @GetMapping("/review/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new Exception404("수정할 리뷰를 찾을 수 없어요"));

        model.addAttribute("review", review);
        return "review/detail";
    }

    // 리뷰 수정 화면 요청
    // http://localhost:8080/review/1/update
    @GetMapping("/review/{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // sessionUser -> 상수

        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new Exception404("수정할 리뷰를 찾을 수 없어요"));

        if (!review.isOwner(sessionUser.getId())) {
            throw new Exception403("리뷰 수정 권한이 없어요");
        }

        model.addAttribute("review", review);
        return "review/update-form";
    }

    // 리뷰 수정 기능 요청
    // http://localhost:8080/review/1/update
    @PostMapping("/review/{id}/update")
    public String updateProc(
            @PathVariable Long id,
            ReviewRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new Exception404("수정할 리뷰를 찾을 수 없어요."));

        if (!review.isOwner(sessionUser.getId())) {
            throw new Exception403("리뷰 수정 권한이 없어요.");
        }
        try {
//            reviewRepository.updateById(id, updateDTO);
        } catch (Exception e) {
            throw new RuntimeException("리뷰 수정 실패");
        }
        return "redirect:/review/list";
    }

    // 리뷰 삭제
    @PostMapping("/review/{id}/delete")
    public String delete(
            @PathVariable Long id,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new Exception404("삭제할 리뷰를 찾을 수 없어요."));

        if (!review.isOwner(sessionUser.getId())) {
            throw new Exception403("삭제할 권한이 없어요.");
        }
        reviewRepository.deleteById(id);
        return "redirect:/";
    }
}