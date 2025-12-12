package org.example.shopping.payment;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class PaymentPersistRepository {
    private final EntityManager entityManager;

    @Transactional
    public Payment save(Payment payment){
        entityManager.persist(payment);
        return payment;
    }

//    @Transactional
//    public Payment approveById(Long id, PaymentRequest.ApproveDTO approveDTO){
//        Payment payment = findById(id);
//        payment.paySuccess(approveDTO);
//        return payment;
//    }


    public Payment findById(Long id){
        Payment payment = entityManager.find(Payment.class, id);
        if(payment == null){
            throw new RuntimeException("해당 id 의 결제 정보를 가져올 수 없음");
        }
        return payment;
    }

}
