package org.example.shopping.category;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    // ==================== 카테고리 등록 ====================

    @GetMapping("/admin/categories/save")
    public String saveForm(Model model, HttpSession session) {
        validateAdmin(session);
        model.addAttribute("categories", categoryService.findRootCategories());
        return "category/save-form";
    }

    @PostMapping("/admin/categories/child-save")
    public String childSave(CategoryRequest.SaveChildDTO childDTO, HttpSession session,
                            @RequestParam("imageUrl") MultipartFile imageUrl) {
        validateAdmin(session);
        categoryService.childSave(childDTO, imageUrl);
        return "redirect:/admin/categories/list";
    }

    @PostMapping("/admin/categories/parent-save")
    public String parentSave(CategoryRequest.SaveParentDTO parentDTO, HttpSession session,
                             @RequestParam("imageUrl") MultipartFile imageUrl) {
        validateAdmin(session);
        categoryService.parentSave(parentDTO, imageUrl);
        return "redirect:/admin/categories/list";
    }

    // ==================== 카테고리 목록 ====================

    @GetMapping("/admin/categories/list")
    public String list(Model model, HttpSession session) {
        validateAdmin(session);

        List<CategoryResponse.ListDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("children", categoryService.findAllByDepth());

        return "category/list";
    }

    // ==================== 카테고리 수정 ====================

    @GetMapping("/admin/categories/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        validateAdmin(session);

        CategoryResponse.DetailDTO category = categoryService.findById(id);
        List<CategoryResponse.ListDTO> categories = categoryService.findRootCategories();

        model.addAttribute("category", category);
        model.addAttribute("categories", categories);

        return "category/edit-form";
    }

    @PostMapping("/admin/categories/{id}/edit")
    public String edit(@PathVariable Long id,
                       CategoryRequest.UpdateDTO dto,
                       HttpSession session) {
        validateAdmin(session);
        categoryService.updateById(id, dto);
        return "redirect:/admin/categories/list";
    }

    // ==================== 카테고리 삭제 ====================

    @PostMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        validateAdmin(session);
        categoryService.deleteById(id);
        return "redirect:/admin/categories/list";
    }

    // ==================== 사용자용 네비게이션 ====================

    @GetMapping("/categories/nav")
    public String getCategoryTree(Model model) {
        List<CategoryResponse.NavbarDTO> categoryTree = categoryService.getCategoryTree();
        model.addAttribute("categories", categoryTree);
        return "category/category-nav";
    }

    // ==================== 공통 메서드 ====================

    private void validateAdmin(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null || !sessionUser.isAdmin()) {
            throw new Exception403("관리자만 접근할 수 있습니다");
        }
    }
}