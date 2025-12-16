package org.example.shopping.testing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingController {

    // 테스트 홈 화면
    // http://localhost:8080/home
    @GetMapping({"/home"})
    public String test1() {
        return "layout/index";
    }

    // http://localhost:8080/test
    @GetMapping("/test")
    public String test(){
        return "layout/header-test";
    }
}
