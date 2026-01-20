package org.example.shopping.users;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.infra.client.KakaoOAuthClient;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final KakaoOAuthClient kakaoOAuthClient;
    private final UserService userService;

    public User loginWithKakao(String code) {
        String accessToken = kakaoOAuthClient.getAccessToken(code);

        UserResponse.KakaoProfile kakaoProfile = kakaoOAuthClient.getUserProfile(accessToken);

        String username = kakaoProfile.getProperties().getNickname() + "_" + kakaoProfile.getId();

        return userService.findOrCreateKakaoUser(username, kakaoProfile);
    }

    public void loginWithGoogle(String code) {
        // 추후 구현 예정
    }
}
