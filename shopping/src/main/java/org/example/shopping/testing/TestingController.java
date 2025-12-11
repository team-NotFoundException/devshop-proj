package org.example.shopping.testing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingController {
    // http://localhost:8080/test
    @GetMapping("/test")
    public String test(){
        return "layout/header-test";
    }
}
