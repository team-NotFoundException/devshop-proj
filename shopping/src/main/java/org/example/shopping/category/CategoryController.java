package org.example.shopping.category;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;


    // 카테고리 등록 폼
    // http://localhost:8080/categories/save
    @GetMapping("/admin/categories/save")
    public String saveForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/save-form";
    }

    // 하위 카테고리 등록
    @PostMapping("/admin/categories/child-save")
    public String childSave(CategoryRequest.SaveChildDTO childDTO) {
        categoryService.childSave(childDTO);
        return "redirect:/categories/list";
    }


    // 상위 카테고리 등록
    @PostMapping("/admin/categories/parent-save")
    public String parentSave(CategoryRequest.SaveParentDTO parentDTO) {
        categoryService.parentSave(parentDTO);
        return "redirect:/categories/list";
    }



    @GetMapping("/categories/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }
}