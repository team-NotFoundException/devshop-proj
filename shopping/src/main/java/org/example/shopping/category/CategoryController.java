package org.example.shopping.category;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    // 카테고리 등록 폼
    // http://localhost:8080/categories/save-form
    @GetMapping("/save-form")
    public String saveForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/save-form";
    }

    // 카테고리 등록
    @PostMapping("/save")
    public String save(CategoryRequest.SaveDTO dto) {
        categoryService.save(dto);
        return "redirect:/categories/list";
    }
}