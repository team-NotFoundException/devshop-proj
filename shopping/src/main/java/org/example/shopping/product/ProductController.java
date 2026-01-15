package org.example.shopping.product;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping.category.CategoryResponse;
import org.example.shopping.category.CategoryService;
import org.example.shopping.product.productEnum.ProductStatus;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    // ==================== USER 영역 ====================

    @GetMapping("/")
    public String home(Model model) {
        List<ProductResponse.MainCardDTO> products = productService.findAllForMain();
        model.addAttribute("products", products);
        return "layout/index";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryMain(@PathVariable Long categoryId, Model model) {
        List<ProductResponse.MainCardDTO> products = productService.findByCategoryIdForMain(categoryId);
        CategoryResponse.DetailDTO category = categoryService.findById(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "category/main";
    }

    @GetMapping("/products/{productId}")
    public String userProductDetail(@PathVariable Long productId, Model model) {
        ProductResponse.UserDetailDTO product = productService.findByIdForUser(productId);
        model.addAttribute("product", product);
        return "product/user-detail";
    }


    // ==================== OWNER 영역 ====================

    @GetMapping("/owner/products")
    public String ownerList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        List<ProductResponse.ListDTO> list = productService.findAll(sessionUser.getId());
        model.addAttribute("products", list);
        model.addAttribute("keyword", "");
        return "product/list-form";
    }


    @GetMapping("/owner/products/status/{status}")
    public String ownerListByStatus(@PathVariable ProductStatus status, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        model.addAttribute("products", productService.findByStatus(status));
        model.addAttribute("keyword", "");
        return "product/list-form";
    }

    // 상품 검색
    @GetMapping("/owner/products/search")
    public String search(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "status", required = false) ProductStatus status,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            Model model,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        List<ProductResponse.ListDTO> products;

        // 검색 조건에 따라 분기
        if (status != null) {
            products = productService.searchByProductNameAndStatus(keyword, status);
        } else if (categoryId != null) {
            products = productService.searchByProductNameAndCategoryId(keyword, categoryId);
        } else {
            products = productService.searchByProductName(keyword);
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "product/list-form";
    }

    @GetMapping("/owner/products/category/{categoryId}")
    public String ownerListByCategory(@PathVariable Long categoryId, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        model.addAttribute("products", productService.findByCategoryId(categoryId));
        model.addAttribute("keyword", "");
        return "product/list-form";
    }


    @GetMapping("/owner/products/save")
    public String saveForm(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        List<CategoryResponse.ListDTO> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        return "product/save-form";
    }

    @PostMapping("/owner/products/save")
    public String save(
            ProductRequest.SaveDTO dto,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        productService.save(dto, sessionUser.getId(), thumbnail);
        return "redirect:/owner/products";
    }

    @GetMapping("/owner/products/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        ProductResponse.UpdateFormDTO dto = productService.findByIdForUpdate(id);
        model.addAttribute("product", dto);
        model.addAttribute("category", categoryService.findAll());
        return "product/edit-form";
    }

    @PostMapping("/owner/products/{id}/edit")
    public String edit(@PathVariable Long id, ProductRequest.UpdateDTO dto, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        productService.updateById(id, dto);
        return "redirect:/owner/products/" + id;
    }

    @PostMapping("/owner/products/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        productService.deleteById(id);
        return "redirect:/owner/products";
    }

    @GetMapping("/owner/products/{id}")
    public String ownerDetail(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        ProductResponse.DetailDTO product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

}