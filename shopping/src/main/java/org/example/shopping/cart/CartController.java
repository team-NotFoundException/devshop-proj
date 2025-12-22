package org.example.shopping.cart;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepository;

    // 장바구니 만들기 (임시 기능)
    @PostMapping("/cart/create")
    public String createCart(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        cartService.createCart(sessionUser);

        return "redirect:/cart/list";
    }

    // 장바구니 아이템 목록 화면 요청
    @GetMapping("/cart/list")
    public String cartItemList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cart.getId();

        List<CartResponse.CartItemListDTO> cartItems = cartService.getCartItems(cartId);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartId", cartId);
        model.addAttribute("cartPrice", cart.getCartPrice() != null ? cart.getCartPrice() : 0);

        return "cart/list";
    }

    // 아이템 추가
    @PostMapping("/cart/add")
    public String addProc(Long productId, CartRequest.AddDTO addDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cart.getId();

        cartService.addCartItem(cartId, productId, addDTO);
        return "redirect:/cart/list";
    }

    // 선택된 아이템 제거
    @PostMapping("/cart/delete-checked")
    public String deleteCheckedItem(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cart.getId();

        cartService.removeCheckedCartItem(cartId);
        return "redirect:/cart/list";
    }

    // 아이템 단건 삭제
    @PostMapping("/cart/{cartItemId}/delete")
    public String deleteItem(@PathVariable Long cartItemId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cart.getId();

        cartService.removeCartItem(cartId, cartItemId);
        return "redirect:/cart/list";
    }

    // 아이템 선택
    @PostMapping("/cart/{cartItemId}/update-check")
    @ResponseBody
    public Map<String, Object> updateCheck(@PathVariable Long cartItemId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cart.getId();

        cartService.checkItem(cartId, cartItemId);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("cartPrice", cart.getCartPrice() != null ? cart.getCartPrice() : 0);

        return result;
    }

    // 아이템 개수/옵션 변경
    @PostMapping("/cart/{cartItemId}/update-option")
    public String updateOption(CartRequest.UpdateOptionDTO updateOptionDTO, @PathVariable Long cartItemId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cart.getId();

        cartService.updateOption(updateOptionDTO, cartId, cartItemId);

        return "redirect:/cart/list";
    }
}
