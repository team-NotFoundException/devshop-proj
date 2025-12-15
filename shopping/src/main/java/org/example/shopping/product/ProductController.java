package org.example.shopping.product;

import lombok.RequiredArgsConstructor;
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

    private final ProductPersistRepository productRepository;

    // 전체 조회
    @GetMapping
    public String list(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }

    // 단건 조회
    public String detail(@PathVariable Long id, Model model) {
         Product product = productRepository.findById(id);

         if (product == null) {
             throw new IllegalArgumentException("상품이 존재하지 않습니다,");
         }

         model.addAttribute("product", product);
         return "product/detail";
    }

    // 상품 등록 페이지
    @GetMapping("/save")
    public String saveForm() {
        return "product/save-form";
    }

    // 상품 등록
    @PostMapping("/save")
    public String save(Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    // 상품 수정 페이지
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id);

        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        model.addAttribute("product", product);
        return "product/edit-form";
    }

    // 상품 수정
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute ProductRequest.UpdateDTO dto
    ) {
        productRepository.updateById(id, dto);
        return "redirect:/products" + id;
    }

    // 상품 삭제
    @PostMapping("/id/delte}")
    public String delet(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    // 상태별 조회
    @GetMapping("/status/{status}")
    public String listByStatus(@PathVariable ProductStatus status, Model model) {
        List<Product> products = productRepository.findByStatus(status);
        model.addAttribute("products", products);
        return "product/list";
    }

    // 카테고리별 조회
    @GetMapping("/category/{categoryId}")
    public String listByCategory(
            @PathVariable Long categoryId,
            Model model
    ) {
        Category category = new Category();
        category.setCategoryId(categoryId); // 단순 조회용 객체

        List<Product> products = productRepository.findByCategory(category);
        model.addAttribute("products", products);

        return "product/list";
    }


}
