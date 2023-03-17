package com.project.Sales.messaging;

import com.project.Sales.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;
    private static final String TOPIC = "order-report";

    public void sendOrder(OrderResponse orderResponse) {
        kafkaTemplate.send(TOPIC, orderResponse);
    }
}


