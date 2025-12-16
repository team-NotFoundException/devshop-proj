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
                 .map(cartItem -> new CartResponse.CartItemListDTO(cartItem))
                 .collect(Collectors.toList());
    }

    // 아이템 추가
    // 1. 트랜잭션 처리
    // 2. DB 에서 카트 조회 (cartId) db를 덜 조회하는 방향
    // 3. 조회된 cart에 아이템 추가
    // +) 추가하는 아이템이 이미 장바구니에 있으면 [상품 id와 옵션(사이즈, 색깔 등) 확인]
    // -> 오류 발생(이미 장바구니에 있는 아이템입니다.)
    @Transactional
    public void addCartItem(Long cartItemId, CartRequest.AddDTO addDTO) {

        Cart cartEntity = cartRepository.findById(addDTO.getCart().getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        // 나중에 product option 까지 확인해서 찾는 로직으로 변경
        CartItem item = cartItemRepository.findByCart_IdAndId(addDTO.getCart().getId(), cartItemId)
                        .orElse(null);

        if (item != null) {
            throw new Exception400("이미 장바구니에 있습니다.");
        } else {
            CartItem newItem = cartEntity.addItem(addDTO.getProductId(), addDTO.getQuantity());
            cartItemRepository.save(newItem);
        }
    }

    // 아이템 삭제
    @Transactional
    public void removeCartItem(Long cartId) {

        cartRepository.findById(cartId)
                        .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        cartItemRepository.deleteByCartIdAndIsChecked(cartId);
    }

    // 아이템 선택
    // 1. 카트 조회
    // 2. 카트 아이템 조회
    // 3. 카트 아이템의 체크 상태 업데이트
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
