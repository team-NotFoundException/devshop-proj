package org.example.shopping.order;


import lombok.Data;
import org.example.shopping.orderItem.OrderItem;

import java.util.List;

public class OrderRequest {

    @Data
    public static class CreateDTO {
        private String user;
        private List<OrderItem> items;
        private OrderStatus orderStatus;
        private String paymentInfo;
        private Long totalPrice;

        public Order toEntity(String user) {
            return new Order(items, orderStatus, paymentInfo, totalPrice);
        }
    }

    public static class CartOrderDTO {

        /*
            1. 장바구니에서 아이템을 선택
            2. 구매하기 버튼을 누르면 주문 생성 페이지로 넘어감
            3. cartItem 에서 isChecked가 true인 아이템을 List<orderItem> items에 넣음
            4. List<orderItem> items의 orderItem이 cartItem임
         */
    }
}