package org.example.shopping.cart;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;

    // 장바구니 조회
    @GetMapping("/cart/list")
    public String cartItemList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        CartResponse.CartDTO cart = cartService.getCartByUserId(sessionUser.getId());

        model.addAttribute("cart", cart);

        return "cart/list";
    }

    // 아이템 추가
    @PostMapping("/cart/add")
    public String addProc(CartRequest.AddDTO addDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        cartService.addCartItem(sessionUser.getId(), addDTO);

        return "redirect:/cart/list";
    }

    // 선택된 아이템 제거
    @PostMapping("/cart/delete-checked")
    public String deleteCheckedItem(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        cartService.removeCheckedCartItem(sessionUser.getId());
        return "redirect:/cart/list";
    }

    // 아이템 단건 삭제
    @PostMapping("/cart/{cartItemId}/delete")
    public String deleteItem(@PathVariable Long cartItemId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        cartService.removeCartItem(sessionUser.getId(), cartItemId);
        return "redirect:/cart/list";
    }

    // 아이템 선택
    @PostMapping("/cart/{cartItemId}/update-check")
    @ResponseBody
    public CartResponse.AmountDTO updateCheck(@PathVariable Long cartItemId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        return cartService.toggleItem(sessionUser.getId(), cartItemId);
    }

    // 전체 선택/해제
    @PostMapping("/cart/toggle-all")
    @ResponseBody
    public CartResponse.ToggleAllChecksDTO toggleAllChecks(
            HttpSession session,
            @RequestBody CartRequest.ToggleAllChecksDTO reqDTO
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        return cartService.toggleAllChecks(sessionUser.getId(), reqDTO);
    }


    // 아이템 개수/옵션 변경
    @PostMapping("/cart/{cartItemId}/update-option")
    @ResponseBody
    public CartResponse.CartUpdateDTO updateOption(@PathVariable Long cartItemId,
                               @RequestBody CartRequest.UpdateOptionDTO updateOptionDTO,
                               HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        return cartService.updateOption(cartItemId, updateOptionDTO, sessionUser.getId());
    }
}