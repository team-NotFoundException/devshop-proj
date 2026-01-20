package org.example.shopping.payment.paymentEnum;


/**
 * 결제 상태(Payment Status) - 생명주기 관리
 *
 *      READY => (PG 승인 성공) => SUCCESS
 *      READY => (PG 승인 실패) => FAILED
 *      SUCCESS => (환불 처리 완료) => REFUNDED
 *
 * */
public enum PaymentStatus {
    SUCCESS,
    FAILED,
    CONFIRMED,
    REFUNDED
}
