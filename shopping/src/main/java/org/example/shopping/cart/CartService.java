package org.example.shopping.cart;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니 아이템 목록
    public List<CartResponse.CartItemListDTO> getCartItems(Long cartId) {

         List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

         return cartItems.stream()
                 .map(CartResponse.CartItemListDTO::new)
                 .collect(Collectors.toList());
    }

    // 아이템 추가
    @Transactional
    public void addCartItem(Long cartItemId, CartRequest.AddDTO addDTO) {

        Cart cartEntity = cartRepository.findById(addDTO.getCart().getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        CartItem item = null;

        if (item != null) {
            throw new Exception400("이미 장바구니에 있습니다.");
        } else {
            CartItem newItem = cartEntity.addItem(addDTO.getProductId(), addDTO.getQuantity());
            cartItemRepository.save(newItem);
        }
    }

    // 선택된 아이템 삭제
    @Transactional
    public void removeCheckedCartItem(Long cartId) {

        cartRepository.findById(cartId)
                        .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        cartItemRepository.deleteByCartIdAndIsChecked(cartId);
    }

    // 아이템 개별 삭제
    @Transactional
    public void removeCartItem(Long cartId, Long cartItemId) {
        CartItem cartItemEntity = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("아이템을 찾을 수 없습니다."));

        if (!cartItemEntity.getCart().getId().equals(cartId))
            throw new Exception400("잘못된 요청입니다.");

        cartItemRepository.delete(cartItemEntity);
    }

    // 아이템 선택
    @Transactional
    public void checkItem(Long cartId ,Long cartItemId) {

        CartItem cartItemEntity = cartItemRepository.findByCart_IdAndId(cartId, cartItemId)
                .orElseThrow(() -> new Exception404("아이템을 찾을 수 없습니다."));

        cartItemEntity.updateCheckItem();
    }

    // 아이템 개수/옵션 변경
    @Transactional
    public void updateOption(CartRequest.UpdateOptionDTO updateOptionDTO, Long cartId ,Long cartItemId) {

        CartItem cartItemEntity = cartItemRepository.findByCart_IdAndId(cartId, cartItemId)
                .orElseThrow(() -> new Exception404("아이템을 찾을 수 없습니다."));

        cartItemEntity.updateItemOption(updateOptionDTO.getQuantity());
    }
}
