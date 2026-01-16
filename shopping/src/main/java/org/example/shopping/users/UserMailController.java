package org.example.shopping.users;

import lombok.RequiredArgsConstructor;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserMailController {

    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/user/email/send")
    public ResponseEntity<?> sendAuthCode(@RequestBody UserRequest.EmailCheckDTO emailCheckDTO) {
        mailService.sendAuthCode(emailCheckDTO.getEmail());

        return ResponseEntity.ok().body(Map.of("message", "인증번호 발송, 확인부탁드립니다."));
    }

    @PostMapping("/user/email/verify")
    public ResponseEntity<?> verifyAuthCode(@RequestBody UserRequest.EmailCheckDTO emailCheckDTO) {
        if (emailCheckDTO.getCode() == null || emailCheckDTO.getCode().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "인증번호를 입력해주세요"));
        }

        boolean isVerified = mailService.verifyAuthCode(emailCheckDTO.getEmail(), emailCheckDTO.getCode());

        if (isVerified) {
            return ResponseEntity.ok().body(Map.of("message", "인증되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "인증번호가 일치하지 않습니다."));
        }


    }
}
