package org.example.shopping._core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping.users.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OwnerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (session == null) {
            throw new Exception401("로그인 요망");
        }

        if (!sessionUser.isOwner()) {
            throw new Exception403("입점자 권한이 없어요");
        }

        return true;
    }
}
