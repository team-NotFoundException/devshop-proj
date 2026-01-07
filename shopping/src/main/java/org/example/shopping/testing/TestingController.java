package org.example.shopping.testing;

import jakarta.servlet.http.HttpSession;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingController {

    // 테스트 홈 화면
    // http://localhost:8080/
//    @GetMapping({"/"})
//    public String home(Model model) {
//        return "layout/index";
//    }

    // http://localhost:8080/test
    @GetMapping("/test")
    public String test(){
        return "layout/header-test";
    }
}
