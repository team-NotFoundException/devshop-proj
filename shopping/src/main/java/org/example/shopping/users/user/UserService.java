package org.example.shopping.users.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping._core.utils.SocialUtils;
import org.example.shopping._core.utils.ValidationUtils;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.enums.OAuthProvider;
import org.example.shopping.users.enums.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${tenco.key}")
    private String tencoKey;

    @Transactional
    public User signUp(@Valid UserRequest.SignUpDTO signUpDTO) {

        User user = signUpDTO.toEntity();
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserRole userRole = new UserRole(user, RoleType.USER);
        user.setRole(userRole);
        return userRepository.save(user);
    }

    @Transactional
    public boolean usernameIsExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public User login(@Valid UserRequest.LoginDTO loginDTO) {
        User userEntity = userRepository
                .findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new Exception401("아이디 또는 비밀번호가 일치하지 않아요"));

        if (userEntity == null) {
            throw new Exception400("회원이 아닙니다.");
        }

        // 비밀번호 검증
//        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
//            throw new Exception401("아이디 또는 비밀번호가 일치하지 않습니다.");
//        }

        return userEntity;
    }

    public User userUpdateView(Long userSessionId) {
        return userRepository.findById(userSessionId)
                .orElseThrow(() -> new Exception404("사용자를 못찾아요"));
    }

    @Transactional
    public User userUpdate(@Valid UserRequest.UpdateDTO updateDTO, Long userId) {
        User userEntity = userRepository
                .findById(userId).orElseThrow(() -> new Exception404("사용자를 찾지 못하겠어요"));

        userEntity.update(updateDTO);

        return userEntity;
    }

    @Transactional
    public User findOrCreateKakaoUser(String username, UserResponse.KakaoProfile profile) {

        User userOrigin = userRepository.findByUsername(username).orElse(null);

        if (userOrigin != null) {
            return userOrigin;
        }

        User newUser = User.builder()
                .username(username)
                .password(tencoKey) // 또는 랜덤값
                .nickname(profile.getKakaoAccount().getName())
                .email(username + "@kakao.com")
                .phoneNumber(profile.getKakaoAccount().getPhoneNumber().replace("+82 ", "0"))
                .gender(SocialUtils.convertGender(profile.getKakaoAccount().getGender()))
                .birthday(SocialUtils.convertBirthday(profile.getKakaoAccount().getBirthyear(), profile.getKakaoAccount().getBirthday()))
                .provider(OAuthProvider.KAKAO)
                .build();

        UserRole userRole = new UserRole(newUser, RoleType.USER);
        newUser.setRole(userRole);
        return userRepository.save(newUser);
    }


    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)
    public List<UserResponse.UserList> userList () {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(UserResponse.UserList::new).toList();
    }

    // 유저 권한 부여
    @Transactional
    public void grantRole(Long userId, RoleType roleType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("유저 찾을 수 없음"));

        if(user.getRole()!= null){
            userRoleRepository.delete(user.getRole());
        }
        UserRole newRole = new UserRole(user, roleType);
        user.setRole(newRole);
        userRoleRepository.save(newRole);
    }




    // 유저 권한 회수
}
