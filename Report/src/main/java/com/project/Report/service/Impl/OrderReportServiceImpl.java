package com.project.Report.service.Impl;

import com.project.Report.service.OrderReportService;
import com.project.Sales.dto.response.OrderReportResponse;
import com.project.Sales.dto.response.OrderResponse;
import com.project.Sales.model.Order;
import com.project.Sales.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderReportServiceImpl implements OrderReportService {
    private final OrderRepository orderRepository;

    @Override
    public void processOrder(OrderResponse orderResponse) {
        Order newOrder = new Order();
        newOrder.setCustomerName(orderResponse.getCustomerName());
        newOrder.setPhoneNumber(orderResponse.getPhoneNumber());
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setProductPrice(orderResponse.getProductPrice());
        newOrder.setQuantity(orderResponse.getQuantity());
        newOrder.setTotalPrice(orderResponse.getTotalPrice());
        orderRepository.save(newOrder);
    }
    @Override
    public Map<LocalDate, OrderReportResponse> getOrderReports(LocalDate startDate, LocalDate endDate) {
        List<Order> orders;

        if (startDate != null && endDate != null) {
            orders = orderRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay().minusNanos(1));
        } else {
            orders = orderRepository.findAll();
        }

        Map<LocalDate, OrderReportResponse> orderReportMap = new HashMap<>();

        for (Order order : orders) {
            LocalDate orderDate = order.getCreatedAt().toLocalDate();

            OrderReportResponse orderReport = orderReportMap.getOrDefault(orderDate, OrderReportResponse.builder()
                            .totalOrder(0)
                            .totalOrderAmount(BigDecimal.ZERO)
                    .build());
            orderReport.setTotalOrder(orderReport.getTotalOrder() + 1);
            orderReport.setTotalOrderAmount(orderReport.getTotalOrderAmount().add(order.getTotalPrice()));

            orderReportMap.put(orderDate, orderReport);
        }
        return orderReportMap;
        }

}



