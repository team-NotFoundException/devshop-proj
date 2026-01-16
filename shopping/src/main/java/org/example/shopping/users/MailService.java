package org.example.shopping.users;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping._core.errors.exception.Exception500;
import org.example.shopping._core.utils.MailUtils;
import org.example.shopping.users.user.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final HttpSession session;
    private final UserRepository userRepository;

    public void sendAuthCode(String email) {

        boolean isExistEmail = userRepository.findByEmail(email).isPresent();
        if (isExistEmail) {
            throw new Exception400("이미 가입된 이메일입니다.");
        }

        String code = MailUtils.generatedRandomCode();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("[DEV-SHOP] 회원가입 이메일 전송");
            helper.setText("<h3>인증 번호는 [" + code + "] 입니다.</h3>", true);

            javaMailSender.send(message);

            session.setAttribute("code_" + email, code);

        } catch (Exception e) {
            log.error("메일 발생 오류: {}", e.getMessage());
            throw new Exception500("서버 문제입니다.");
        }
    }

    public boolean verifyAuthCode(String email, String code) {
        String savedCode = (String) session.getAttribute("code_" + email);

        if (savedCode != null && savedCode.equals(code)) {
            session.removeAttribute("code_" + email);
            return true;
        }
        return false;
    }
}
