package org.example.shopping._core.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping._core.errors.exception.Exception400;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Component
public class ValidationUtils{

    public void validationChecker(
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            // 여러 에러 중 첫 번째 에러 메시지만 가져와서 사용자에게 알림
            String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            String field = bindingResult.getFieldErrors().get(0).getField();

            // 로그 기록 (디버깅용)
            System.out.println("검증 실패 필드: " + field);
            System.out.println("검증 실패 메시지: " + message);

            log.debug("검증 실패: {} \n 메시지: {}", field, message);

            // 앞서 설정한 MyExceptionHandler가 처리하도록 예외 던지기
            throw new Exception400(message);

        }
    }
}
