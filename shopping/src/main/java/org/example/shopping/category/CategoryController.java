package org.example.shopping.category;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 등록 폼
    @GetMapping("/admin/categories/save")
    public String saveForm(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        model.addAttribute("categories", categoryService.findRootCategories());
        return "category/save-form";
    }

    // 하위 카테고리 등록
    @PostMapping("/admin/categories/child-save")
    public String childSave(CategoryRequest.SaveChildDTO childDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        categoryService.childSave(childDTO);
        return "redirect:/admin/categories/list";
    }

    // 상위 카테고리 등록
    @PostMapping("/admin/categories/parent-save")
    public String parentSave(CategoryRequest.SaveParentDTO parentDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        categoryService.parentSave(parentDTO);
        return "redirect:/admin/categories/list";
    }

    @GetMapping("/admin/categories/list")
    public String list(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        List<CategoryResponse.ListDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "category/list";
    }

    // 수정 폼
    @GetMapping("/admin/categories/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        CategoryResponse.DetailDTO category = categoryService.findById(id);
        List<CategoryResponse.ListDTO> categories = categoryService.findRootCategories();

        model.addAttribute("category", category);
        model.addAttribute("categories", categories);

        return "category/edit-form";
    }

    // 수정 처리
    @PostMapping("/admin/categories/{id}/edit")
    public String edit(@PathVariable Long id,
                       CategoryRequest.UpdateDTO dto,
                       HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        categoryService.updateById(id, dto);
        return "redirect:/admin/categories/list";
    }

    // 삭제 처리
    @PostMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }

        categoryService.deleteById(id);
        return "redirect:/admin/categories/list";
    }

    // 사용자용 네비게이션
    @GetMapping("/categories/nav")
    public String getCategoryTree(Model model) {
        List<CategoryResponse.NavbarDTO> categoryTree = categoryService.getCategoryTree();
        model.addAttribute("categories", categoryTree);
        return "category/category-nav";
    }
}