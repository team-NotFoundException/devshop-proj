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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 장바구니 생성
    public void createCart(User user) {
        cartRepository.save(new Cart(user));
    }

    // 장바구니 아이템 목록
    public List<CartResponse.CartItemListDTO> getCartItems(Long cartId) {

         List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

         return cartItems.stream()
                 .map(CartResponse.CartItemListDTO::new)
                 .collect(Collectors.toList());
    }

    // 아이템 추가
    @Transactional
    public void addCartItem(Long cartId, Long productId, CartRequest.AddDTO addDTO) {

        if (addDTO.getQuantity() <= 0)
            throw new Exception400("상품은 1개 이상이어야 합니다.");

        Cart cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Product productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new Exception404("물품을 찾을 수 없습니다."));

        if (productEntity.getStatus().equals(ProductStatus.SOLD_OUT) ||
                productEntity.getStatus().equals(ProductStatus.INACTIVE))
            throw new Exception400("구매할 수 없는 상품입니다.");

        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        Optional<CartItem> existingItem = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
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
            cartItemRepository.save(newItem);
        }

        updateTotalPrice(cartId);
    }

    // 선택된 아이템 삭제
    @Transactional
    public void removeCheckedCartItem(Long cartId) {

        cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        cartItemRepository.deleteByCartIdAndIsChecked(cartId);

        updateTotalPrice(cartId);
    }

    // 아이템 개별 삭제
    @Transactional
    public void removeCartItem(Long cartId, Long cartItemId) {
        CartItem cartItemEntity = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("아이템을 찾을 수 없습니다."));

        if (!cartItemEntity.getCart().getId().equals(cartId))
            throw new Exception400("잘못된 요청입니다.");

        cartItemRepository.delete(cartItemEntity);

        updateTotalPrice(cartId);
    }

    // 아이템 선택
    @Transactional
    public void checkItem(Long cartId ,Long cartItemId) {

        CartItem cartItemEntity = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("아이템을 찾을 수 없습니다."));

        if (!cartItemEntity.getCart().getId().equals(cartId))
            throw new Exception400("잘못된 요청입니다.");

        cartItemEntity.updateCheckItem();
        updateTotalPrice(cartId);
    }

    // 아이템 개수/옵션 변경
    @Transactional
    public void updateOption(CartRequest.UpdateOptionDTO updateOptionDTO, Long cartId ,Long cartItemId) {

        CartItem cartItemEntity = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception404("아이템을 찾을 수 없습니다."));

        if (!cartItemEntity.getCart().getId().equals(cartId))
            throw new Exception400("잘못된 요청입니다.");

        cartItemEntity.updateItemOption(updateOptionDTO.getQuantity());
    }

    // 카트 총액 업데이트
    public void updateTotalPrice(Long cartId) {
        Cart cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        // 총액 계산
        Long totalPrice = cartItems.stream()
                .filter(CartItem::isItemChecked)
                        .mapToLong(CartItem::getTotalPrice)
                                .sum();

        cartEntity.setCartPrice(totalPrice);
    }
}