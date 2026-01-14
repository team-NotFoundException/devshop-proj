package org.example.shopping.payment.service.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.payment.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class TossPayGateway implements PaymentGateway {

    private final RestTemplate restTemplate;

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    @Value("${payment.toss.base-url}")
    private String baseUrl;

    @Override
    public PaymentResult approve(PaymentRequest.CreateDTO createDTO) {
        try{
            String url = baseUrl + "/v1/payments/confirm";
            String auth = Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Basic " + auth);

            Map<String, Object> body = Map.of(
                    "paymentKey", createDTO.getPaymentKey(),
                    "orderId", createDTO.getOrderId(),
                    "amount", createDTO.getAmount()
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            String response = restTemplate.postForObject(url, entity, String.class);
            log.info("[TossPay] confirm response={}", response);

            return PaymentResult.ok(createDTO.getPaymentKey());

        } catch (Exception e){
            log.error("[TossPay] ERROR", e);
            return PaymentResult.fail("TOSS_ERROR", e.getMessage());
        }
    }

    @Override
    public PaymentResult refund(PaymentRequest.RefundDTO refundDTO) {
        try {
            String url = baseUrl + "/v1/payments/" + refundDTO.getPaymentKey() + "/cancel";
            String auth = Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Basic " + auth);
            headers.set("Content-Type", "application/json");

            Map<String, Object> body = Map.of(
                    "paymentKey", refundDTO.getPaymentKey(),
                    "cancelReason" ,  refundDTO.getReason(),
                    "cancelAmount", refundDTO.getAmount()
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            String response = restTemplate.postForObject(url, entity, String.class);
            log.info("[TossPay] refund response={}", response);

            return PaymentResult.ok(refundDTO.getPaymentKey());

        } catch (Exception e) {
            log.error("[TossPay] Refund ERROR", e);
            return PaymentResult.fail("TOSS_REFUND_ERROR", e.getMessage());

        }
    }
}
