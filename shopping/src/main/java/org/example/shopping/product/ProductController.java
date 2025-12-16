package org.example.shopping.product;

import lombok.RequiredArgsConstructor;
import org.example.shopping.product.productEnum.ProductStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    // - user
    // 상품 목록 조회
    // http://localhost:8080/products/list-form
    @GetMapping("/list-form")
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list-form";
    }

    // 상품 상세 조회
    // http://localhost:8080/products/status/READY

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
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
    @GetMapping("/category/{categoryId}")
    public String listByCategory(@PathVariable Long categoryId, Model model) {
        model.addAttribute("products", productService.findByCategoryId(categoryId));
        return "product/list-form";
    }


    // - admin
    // 상품 등록 폼
    @GetMapping("/save-form")
    public String saveForm() {
        return "product/save-form";
    }

    // 상품 등록
    @PostMapping("/save")
    public String save(ProductRequest.SaveDTO dto) {
        productService.save(dto);
        return "redirect:/products";
    }

    // 상품 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/edit-form";
    }

    // 상품 수정
    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            ProductRequest.UpdateDTO dto
    ) {
        productService.updateById(id, dto);
        return "redirect:/products/" + id;
    }

    // 상품 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}