package com.project.Report.messaging;

import com.project.Report.service.OrderReportService;
import com.project.Sales.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderReportService orderReportService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(ConsumerRecord<String, OrderResponse> consumerRecord) {
        OrderResponse order = consumerRecord.value();
        orderReportService.processOrder(order);
    }
}


