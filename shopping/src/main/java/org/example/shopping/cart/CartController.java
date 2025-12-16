package org.example.shopping.cart;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping.cartItem.CartItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;

    // 장바구니 아이템 목록 화면 요청
    // http://localhost:8080/cart/list
    @GetMapping("/cart/list")
    public String cartItemList(Long cartId, Model model, HttpSession session) {
        List<CartResponse.CartItemListDTO> cartItems = cartService.getCartItems(cartId);

        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        model.addAttribute("cartItems", cartItems);
        return "cart/list";
    }

    // 아이템 추가
    @PostMapping("/cart/{id}/add")
    public String addProc(Long cartId, CartRequest.AddDTO addDTO, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        cartService.addCartItem(cartId, addDTO);

        return "redirect:/cart/list";
    }

    // 아이템 제거
    @PostMapping("/cart/{cartId}/delete")
    public String delete(@PathVariable Long cartId, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        cartService.removeCartItem(cartId);

        return "redirect:/cart/list";
    }

    // 아이템 선택
    @PostMapping("/cart/{cartId}/{cartItemId}/update/check")
    public String updateCheck(@PathVariable Long cartId, @PathVariable Long cartItemId, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        cartService.checkItem(cartId, cartItemId);

        return "redirect:/cart/list";
    }

    // 아이템 개수/옵션 변경
    @PostMapping("/cart/{cartId}/{cartItemId}/update/option")
    public String updateOption(CartRequest.UpdateOptionDTO updateOptionDTO,@PathVariable Long cartId, @PathVariable Long cartItemId, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        cartService.updateOption(updateOptionDTO, cartId, cartItemId);

        return "redirect:/cart/list";
    }
}
