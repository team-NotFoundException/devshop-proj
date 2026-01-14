package org.example.shopping._core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping.users.User;
import org.example.shopping.users.enums.OwnerStatus;
import org.example.shopping.users.owner.Owner;
import org.example.shopping.users.owner.OwnerRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class OwnerInterceptor implements HandlerInterceptor {
    private final OwnerRepository ownerRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new Exception401("로그인 요망");
        }

        if (!sessionUser.getIsOwner()) {
            throw new Exception403("입점자 권한이 없어요");
        }

        Owner owner = ownerRepository.findByUserIdUseInterceptor(sessionUser.getId())
                .orElseThrow(() -> new Exception403("입점사 정보를 찾을 수 없습니다."));

        if (owner.getStatus() == OwnerStatus.SUSPENSION) {
            throw new Exception403("관리자에 의해 활동이 정지된 입점사입니다.");
        }

        return true;
    }
}
