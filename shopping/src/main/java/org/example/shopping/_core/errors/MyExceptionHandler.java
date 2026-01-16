package org.example.shopping._core.errors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping._core.errors.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

// @ControllerAdvice - 모든 컨트롤러에서 발생하는 예외를 이 클래스에서 중앙 집중화 시킴
// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {


    // 400 에러: 유효성 검사 실패 등
//    @ExceptionHandler(Exception400.class)
//    public String ex400(Exception400 e, Model model, HttpServletRequest request) {
//        model.addAttribute("alertMessage", e.getMessage());
//        // url을 안 보내면 mustache에서 history.back() 처리
//        return "alert";
//    }

    @ExceptionHandler(Exception400.class)
    public Object ex400(Exception400 e, Model model, HttpServletRequest request) {
        String message = e.getMessage();

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.debug("API 요청 에러 발생: {}", e.getMessage());
            Map<String , String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("alertMessage", e.getMessage());
        // url을 안 보내면 mustache에서 history.back() 처리
        return "alert";
    }

    // 401 에러: 인증 실패 (로그인 페이지로 이동)
    @ExceptionHandler(Exception401.class)
    public String ex401(Exception401 e, Model model) {
        model.addAttribute("alertMessage", e.getMessage());
        model.addAttribute("url", "/user/login"); // 로그인 폼으로 유도
        return "alert";
    }

    // 403 에러: 권한 없음
    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e, Model model) {
        model.addAttribute("alertMessage", e.getMessage());
        return "alert"; // history.back()
    }

    // 404 에러: 찾을 수 없음
    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, Model model) {
        model.addAttribute("alertMessage", e.getMessage());
        return "alert";
    }

    // 데이터베이스 제약조건 위반 (PK 중복, FK 위반 등)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataViolationException(DataIntegrityViolationException e, Model model) {
        String errorMessage = e.getMessage();
        String displayMsg = "데이터베이스 오류가 발생했습니다.";

        if(errorMessage != null && errorMessage.contains("FOREIGN KEY")) {
            displayMsg = "관련된 데이터가 존재하여 삭제하거나 수정할 수 없습니다.";
        }

        model.addAttribute("alertMessage", displayMsg);
        return "alert";
    }

    // 모든 RuntimeException (예상치 못한 서버 에러)
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model) {
        log.error("예상치 못한 에러: {}", e.getMessage());
        model.addAttribute("alertMessage", "서버 내부 오류가 발생했습니다: " + e.getMessage());
        return "alert";
    }
}