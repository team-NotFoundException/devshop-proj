package org.example.shopping.product;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.category.Category;
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

    // 전체 조회
    // http://localhost:8080/products/product/list
    @GetMapping("/product/list")
    public String list(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/listform";
    }

    // 단건 조회
    // http://localhost:8080/products/product/detail/1
    @GetMapping("/product/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
         Product product = productService.findById(id);

         if (product == null) {
             throw new Exception404("상품이 존재하지 않습니다,");
         }

         model.addAttribute("product", product);
         return "product/detail";
    }

    // 상품 등록 페이지
    // http://localhost:8080/products/product/saveform
    @GetMapping("/product/saveform")
    public String saveForm() {
        return "product/save-form";
    }

    // 상품 등록
    // http://localhost:8080/products/product/save
    @PostMapping("/product/save")
    public String save(ProductRequest.SaveDTO dto,Category category) {
        productService.save(dto, category);
        return "redirect:/products";
    }

    // 상품 수정 페이지
    // http://localhost:8080/products/product/1/edit
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);

        if (product == null) {
            throw new Exception404("상품이 존재하지 않습니다.");
        }

        model.addAttribute("product", product);
        return "product/edit-form";
    }

    // 상품 수정
    // http://localhost:8080/products/product/1/edit
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute ProductRequest.UpdateDTO dto
    ) {
        productService.updateById(id, dto);
        return "redirect:/products" + id;
    }

    // 상품 삭제
    // http://localhost:8080/products/product/delete
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    // 상태별 조회
    // http://localhost:8080/products/product/status/1
    @GetMapping("/status/{status}")
    public String listByStatus(@PathVariable ProductStatus status, Model model) {
        List<Product> products = productService.findByStatus(status);
        model.addAttribute("products", products);
        return "listform";
    }

    // 카테고리별 조회
    // http://localhost:8080/products/product/category/1
    @GetMapping("/category/{categoryId}")
    public String listByCategory(
            @PathVariable Long categoryId,
            Model model
    ) {
        Category category = new Category();
        category.setCategoryId(categoryId); // 단순 조회용 객체

        List<Product> products = productService.findByCategory(category);
        model.addAttribute("products", products);

        return "listform";
    }


}
