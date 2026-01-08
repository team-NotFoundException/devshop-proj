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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


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

        ProductResponse.UserDetailDTO product =
                productService.findByIdForUser(productId);

        model.addAttribute("product", product);
        return "product/user-detail";
    }




    // - user
    // 상품 목록 조회
    // http://localhost:8080/products/list-form
    @GetMapping("/products/list-form")
    public String list(Model model, HttpSession session) {

        List<ProductResponse.ListDTO> list = productService.findAll();
        System.out.println(list.size());

        model.addAttribute("products", list);
        return "product/list-form";
    }

    // 상품 상세 조회
    // http://localhost:8080/products/1/detail
    @GetMapping("/products/{id}/detail")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null || sessionUser.getRole().equals("USER")) {
            throw  new Exception403("로그인 먼저 해주세요");
        }

        ProductResponse.DetailDTO product = productService.findById(id);
        model.addAttribute("product",product);
        return "product/detail";
    }

    // 상태별 조회
    // http://localhost:8080/products/status/1
    @GetMapping("/products/status/{status}")
    public String listByStatus(@PathVariable ProductStatus status, Model model, HttpSession session) {
        model.addAttribute("products", productService.findByStatus(status));
        return "product/list-form";
    }

    // 카테고리별 조회
    // http://localhost:8080/products/category/1
    @GetMapping("/products/category/{categoryId}")
    public String listByCategory(@PathVariable Long categoryId, Model model, HttpSession session) {
        model.addAttribute("products", productService.findByCategoryId(categoryId));
        return "product/list-form";
    }


    // - admin
    // 상품 등록 폼
    // http://localhost:8080/products/save
    @GetMapping("/owner/products/save")
    public String saveForm( Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        List<CategoryResponse.ListDTO> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);

        return "product/save-form";
    }

    // 상품 등록
    //http://localhost:8080/products/save
    @PostMapping("/owner/products/save")
    public String save(ProductRequest.SaveDTO dto, HttpSession session) {
        productService.save(dto);
        return "redirect:/products/list-form";
    }

    // 상품 수정 폼
    // http://localhost:8080/products/1/edit
    @GetMapping("/owner/products/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        ProductResponse.UpdateFormDTO dto =
                productService.findByIdForUpdate(id);

        model.addAttribute("product", dto);
        model.addAttribute("category", categoryService.findAll());
        return "product/edit-form";
    }

    // 상품 수정
    @PostMapping("/owner/products/{id}/edit")
    public String edit(
            @PathVariable Long id,
            ProductRequest.UpdateDTO dto, HttpSession session
    ) {
        productService.updateById(id, dto);
        return "redirect:/products/" + id + "/detail";
    }

    // 상품 삭제
    @PostMapping("/owner/products/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        productService.deleteById(id);
        return "redirect:/products/list-form";
    }

}