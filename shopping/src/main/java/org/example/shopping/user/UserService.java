package org.example.shopping.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception403;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.user.dto.UserRequest;
import org.example.shopping.user.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User signUp(@Valid UserRequest.SignUpDTO signUpDTO) {
        if (userRepository.findByUsername(signUpDTO.getUsername()).isPresent()) {
            throw new Exception400("이미 있는 이름입니다.");
        }
        User user = signUpDTO.toEntity();
        return userRepository.save(user);
    }

    public User login(@Valid UserRequest.LoginDTO loginDTO) {
        User userEntity = userRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElse(null);

        if (userEntity == null) {
            throw new Exception400("아이디 또는 비밀번호가 일치하지 않슷빈다.");
        }

        return userEntity;
    }

    public User userUpdateView(Long userSessionId) {
        User userEntity = userRepository.findById(userSessionId)
                .orElseThrow(() -> new Exception404("사용자를 못찾아요"));

        if (!userEntity.isOwner(userSessionId)) {
            throw new Exception403("당신의 회원정보가 아니에요...");
        }

        return userEntity;
    }

    @Transactional
    public User userUpdate(@Valid UserRequest.UpdateDTO updateDTO, Long userId) {
        User userEntity = userRepository
                .findById(userId).orElseThrow(() -> new Exception404("사용자를 찾지 못하겠어요"));

        if (!userEntity.isOwner(userId)) {
            throw new Exception403("회원정보 수정 권한이 없어요");
        }

        userEntity.update(updateDTO);

        return userEntity;
    }

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)
    public List<User> userList () {
        return null;
    }

    // 유저 권한 부여

    // 유저 권한 회수
}
