package org.example.shopping.users;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping._core.infra.client.KakaoOAuthClient;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cart.CartService;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final KakaoOAuthClient kakaoOAuthClient;
    private final UserService userService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public User loginWithKakao(String code) {
        String accessToken = kakaoOAuthClient.getAccessToken(code);

        UserResponse.KakaoProfile kakaoProfile = kakaoOAuthClient.getUserProfile(accessToken);

        String username = kakaoProfile.getProperties().getNickname() + "_" + kakaoProfile.getId();

        User user = userService.findOrCreateKakaoUser(username, kakaoProfile);

        if(cartRepository.findByUserId(user.getId()).isEmpty()){
            cartService.createCart(user);
        }

        return user;
    }

    public void loginWithGoogle(String code) {
        // 추후 구현 예정
    }
}
