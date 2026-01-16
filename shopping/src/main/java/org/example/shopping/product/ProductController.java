package org.example.shopping.product;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping.category.CategoryResponse;
import org.example.shopping.category.CategoryService;
import org.example.shopping.users.User;
import org.example.shopping.users.owner.OwnerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OwnerRepository ownerRepository;

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
        return "user/owner/product-list";
    }

    @GetMapping("/owner/products/save")
    public String saveForm(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        List<CategoryResponse.ListDTO> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        return "user/owner/product-save";
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

        ProductResponse.UpdateFormDTO product = productService.findByIdForUpdate(id);
        List<CategoryResponse.ListDTO> categoryList = categoryService.findAll();

        model.addAttribute("product", product);
        model.addAttribute("category", categoryList);

        return "product/edit-form";
    }

    @PostMapping("/owner/products/{id}/edit")
    public String edit(
            @PathVariable Long id,
            ProductRequest.UpdateDTO dto,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || !sessionUser.isOwner()) {
            throw new Exception403("판매자만 접근할 수 있습니다");
        }

        productService.updateById(id, dto, thumbnail);

        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");


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
    }}