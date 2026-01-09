package org.example.shopping.category;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @GetMapping("/admin/categories/list")
    public String list(Model model) {
        List<CategoryResponse.ListDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        System.out.println(categories);
        return "category/list";
    }

    @GetMapping("/categories/nav")
    public String getCategoryTree (Model model) {
        List<CategoryResponse.NavbarDTO> categoryTree = categoryService.getCategoryTree();

        model.addAttribute("categories", categoryTree);

        return "category/category-nav";
    }


}