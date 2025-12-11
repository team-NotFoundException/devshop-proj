package org.example.shopping.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewPersistRepository repository;

    // 테스트 화면
    // http://localhost:8080/test
    @GetMapping("/test")
    public String test() {
        return "layout/header";
    }

    // 리뷰 저장 화면 요청
    // http://localhost:8080/review/save
    @GetMapping("/review/save")
    public String saveForm() {
        return "review/save-form";
    }

    // 리뷰 저장 요청 (기능 요청)
    // http://localhost:8080/review/save
    @Transactional
    @PostMapping("/review/save")
    public String saveProc(ReviewRequest.SaveDTO saveDTO) {
        Review review = saveDTO.toEntity();
        repository.save(review);
        return "redirect:/";
    }

    // 리뷰 조회
    // http://localhost:8080/review/list
    @GetMapping({"/review/list", "/"})
    public String findAll(Model model) {
        List<Review> reviewList = repository.findAll();
        model.addAttribute("reviewList", reviewList);
        return "review/list";
    }

    // 리뷰 단건 조회
    // http://localhost:8080/review/1
    @GetMapping("/review/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Review review = repository.findById(id);

        if (review == null) {
            throw new RuntimeException("해당 리뷰를 찾을 수 없어요");
        }

        model.addAttribute("review", review);
        return "review/detail";
    }

    // 리뷰 수정 화면 요청
    // http://localhost:8080/review/1/update
    @GetMapping("/review/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        Review review = repository.findById(id);

        if (review == null) {
            throw new RuntimeException("수정할 리뷰를 찾을 수 없어요");
        }

        model.addAttribute("review", review);
        return "review/update-form";
    }

    // 리뷰 수정 기능 요청
    // http://localhost:8080/review/1/update
    @PostMapping("/review/{id}/update")
    public String updateProc(
            @PathVariable Long id,
            ReviewRequest.UpdateDTO updateDTO
    ) {
        try {
            repository.updateById(id, updateDTO);
        } catch (Exception e) {
            throw new RuntimeException("리뷰 수정 실패");
        }
        return "redirect:/review/list";
    }

    // 리뷰 삭제
    @PostMapping("/review/{id}/delete")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
