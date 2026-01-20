package org.example.shopping._core.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping._core.errors.exception.Exception400;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Slf4j
@Component
public class ValidationUtils{

    public void validationChecker(
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            String field = bindingResult.getFieldErrors().get(0).getField();

            System.out.println("검증 실패 필드: " + field);
            System.out.println("검증 실패 메시지: " + message);

            log.debug("검증 실패: {} \n 메시지: {}", field, message);

            throw new Exception400(message);

        }
    }
}
