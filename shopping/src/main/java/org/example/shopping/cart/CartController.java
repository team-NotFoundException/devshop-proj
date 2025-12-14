package org.example.shopping.cart;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception401;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemPersistRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartPersistRepository cartPersistRepository;
    private final CartItemPersistRepository cartItemPersistRepository;

    // 장바구니 아이템 목록 화면 요청
    @GetMapping("/cart/list")
    public String cartList(Model model, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        Long cartId = (Long) session.getAttribute("cartId");
        Cart cart = cartPersistRepository.findById(cartId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        model.addAttribute("cart", cart);
        return "cart/list";
    }

    // 아이템 추가
    @PostMapping("/cart/{id}/add")
    public String addProc(CartRequest.AddDTO addDTO, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }
        Long cartId = (Long) session.getAttribute("cartId");

        Cart cart = cartPersistRepository.findById(cartId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        CartItem cartItem = new CartItem(addDTO.toEntity());

        cart.addItem(cartItem); // 추가 아이템 확인하고 같은 아이템이면 수량만 증가하는 로직 추가

        cartPersistRepository.save(cart);

        return "redirect:/cart/list";
    }

    // 아이템 제거
    @PostMapping("/cart/{cartItemId}/delete")
    public String delete(@PathVariable Long cartItemId, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        CartItem cartItem = cartItemPersistRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("물품을 찾을 수 없습니다."));

        Cart cart = cartItem.getCart();

        cart.removeItem(cartItem);

        return "redirect:/cart/list";
    }

    // 아이템 선택
    @PostMapping("/cart/{cartItemId}/update/check")
    public String updateCheck(@PathVariable Long cartItemId, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        CartItem cartItem = cartItemPersistRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("물품을 찾을 수 없습니다."));

        cartItem.updateCheckItem();

        cartItemPersistRepository.save(cartItem);

        return "redirect:/cart/list";
    }

    // 아이템 개수/옵션 변경
    @PostMapping("/cart/{cartItemId}/update/option")
    public String updateOption(@PathVariable Long cartItemId, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        CartItem cartItem = cartItemPersistRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("물품을 찾을 수 없습니다."));

        cartItem.updateItemOption(3); // 아이템 옵션 없어서 수량만 대충

        cartItemPersistRepository.save(cartItem);

        return "redirect:/cart/list";
    }

}
