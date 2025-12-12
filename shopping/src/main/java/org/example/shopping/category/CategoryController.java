package org.example.shopping.category;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryPersistRepository repository;

    @PostMapping("/category/save")
    public Category saveCategory(Category category) {
        return category;
    }
}
