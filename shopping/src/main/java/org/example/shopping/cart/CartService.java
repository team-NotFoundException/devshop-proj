package org.example.shopping.cart;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductRepository;
import org.example.shopping.product.productEnum.ProductStatus;
import org.example.shopping.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // 장바구니 생성
    @Transactional
    public void createCart(User user) {
        cartRepository.save(new Cart(user));
    }

    // 유저 아이디로 장바구니 조회
    public CartResponse.CartDTO getCartByUserId(Long userId) {
        Cart cartEntity = cartRepository.findByUserId(userId).orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        return new CartResponse.CartDTO(cartEntity);
    }

    // 아이템 추가
    @Transactional
    public void addCartItem(Long userId, CartRequest.AddDTO addDTO) {

        addDTO.validate();

        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Product productEntity = productRepository.findById(addDTO.getProductId())
                .orElseThrow(() -> new Exception404("물품을 찾을 수 없습니다."));

        if (productEntity.getStatus().equals(ProductStatus.SOLD_OUT) ||
                productEntity.getStatus().equals(ProductStatus.INACTIVE))
            throw new Exception400("구매할 수 없는 상품입니다.");

        Optional<CartItem> existingItem = cartEntity.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(addDTO.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.updateQuantity(item.getQuantity() + addDTO.getQuantity());
            item.updateTotalPrice();

        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cartEntity)
                    .product(productEntity)
                    .quantity(addDTO.getQuantity())
                    .build();
            cartEntity.addItem(newItem);
        }

        cartEntity.updateAmount();
    }


    // 선택된 아이템 삭제
    @Transactional
    public void removeCheckedCartItem(Long userId) {

        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        cartEntity.removeCheckedItem();
        cartEntity.updateAmount();
    }

    // 아이템 개별 삭제
    @Transactional
    public void removeCartItem(Long userId, Long cartItemId) {
        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        cartEntity.removeItem(cartItemId);
        cartEntity.updateAmount();
    }

    // 아이템 선택
    @Transactional
    public CartResponse.AmountDTO toggleItem(Long userId, Long cartItemId) {

        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        cartEntity.toggleItem(cartItemId);
        cartEntity.updateAmount();

        return new CartResponse.AmountDTO(cartEntity.getAmount());
    }

    @Transactional
    public CartResponse.ToggleAllChecksDTO toggleAllChecks(Long userId, CartRequest.ToggleAllChecksDTO reqDTO) {
        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        boolean newState = reqDTO.getIsChecked();

        cartEntity.getCartItems().forEach(
                cartItem -> cartItem.setIsChecked(newState)
        );

        cartEntity.updateAmount();

        return new CartResponse.ToggleAllChecksDTO(newState, cartEntity.getAmount());
    }

    // 아이템 개수/옵션 변경
    @Transactional
    public CartResponse.CartUpdateDTO updateOption(Long cartItemId, CartRequest.UpdateOptionDTO updateOptionDTO, Long userId) {
        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        CartItem cartItem = cartEntity.getCartItem(cartItemId);

        cartItem.updateQuantity(cartItem.getQuantity() + updateOptionDTO.getQuantity());
        cartItem.updateTotalPrice();
        cartEntity.updateAmount();

        Integer newQuantity = cartItem.getQuantity();
        Long newTotalPrice = cartItem.getTotalPrice();
        Long newAmount = cartEntity.getAmount();

        return new CartResponse.CartUpdateDTO(cartItemId, newQuantity, newTotalPrice, newAmount);
    }
}