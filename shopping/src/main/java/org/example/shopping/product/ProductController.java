package org.example.shopping.product;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping.category.CategoryResponse;
import org.example.shopping.category.CategoryService;
import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    // - user
    // 상품 목록 조회
    // http://localhost:8080/products/list-form
    @GetMapping("/list-form")
    public String list(Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        List<ProductResponse.ListDTO> list = productService.findAll();

        model.addAttribute("products", productService.findAll());
        return "product/list-form";
    }

    // 상품 상세 조회
    // http://localhost:8080/products/1/detail
    @GetMapping("/{id}/detail")
    public String detail(@PathVariable Long id, Model model) {
        ProductResponse.DetailDTO product = productService.findById(id);
        model.addAttribute("product",product);
        return "product/detail";
    }

    // 상태별 조회
    // http://localhost:8080/products/status/1
    @GetMapping("/status/{status}")
    public String listByStatus(@PathVariable ProductStatus status, Model model) {
        model.addAttribute("products", productService.findByStatus(status));
        return "product/list-form";
    }

    // 카테고리별 조회
    // http://localhost:8080/products/category/1
    @GetMapping("/category/{categoryId}")
    public String listByCategory(@PathVariable Long categoryId, Model model) {
        model.addAttribute("products", productService.findByCategoryId(categoryId));
        return "product/list-form";
    }


    // - admin
    // 상품 등록 폼
    // http://localhost:8080/products/save
    @GetMapping("/save")
    public String saveForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<CategoryResponse.ListDTO> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);

        return "product/save-form";
    }

    // 상품 등록
    //http://localhost:8080/products/save
    @PostMapping("/save")
    public String save(ProductRequest.SaveDTO dto) {
        productService.save(dto);
        return "redirect:/products/list-form";
    }

    // 상품 수정 폼
    // http://localhost:8080/products/1/edit
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ProductResponse.UpdateFormDTO dto =
                productService.findByIdForUpdate(id);

        model.addAttribute("product", dto);
        model.addAttribute("category", categoryService.findAll());
        return "product/edit-form";
    }

    // 상품 수정
    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            ProductRequest.UpdateDTO dto
    ) {
        productService.updateById(id, dto);
        return "redirect:/products/" + id + "/detail";
    }

    // 상품 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products/list-form";
    }

    // 리스트 폼
    @GetMapping("/product/list")
    // http://localhost:8080/products/list
    public String listForm(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list-form";
    }
}