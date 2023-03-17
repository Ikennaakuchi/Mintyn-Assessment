package com.project.Report.service.Impl;

import com.project.Sales.dto.response.OrderResponse;
import com.project.Sales.model.Order;
import com.project.Sales.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class OrderReportServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderReportServiceImpl orderReportService;

    OrderResponse orderResponse;
    ArgumentCaptor<Order> orderCaptor;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        orderResponse = new OrderResponse();
        orderResponse.setCustomerName("Romeo");
        orderResponse.setPhoneNumber("09034565421");
        orderResponse.setProductPrice(BigDecimal.valueOf(3200));
        orderResponse.setQuantity(2);
        orderResponse.setTotalPrice(BigDecimal.valueOf(6400));

        orderReportService.processOrder(orderResponse);
        orderCaptor = ArgumentCaptor.forClass(Order.class);
    }

    @Test
    public void testProcessOrder() {

        verify(orderRepository).save(orderCaptor.capture());
        Order savedOrder = orderCaptor.getValue();

        assertEquals("Romeo", savedOrder.getCustomerName());
        assertEquals("09034565421", savedOrder.getPhoneNumber());
        assertEquals(BigDecimal.valueOf(3200), savedOrder.getProductPrice());
        assertEquals(2, savedOrder.getQuantity());
        assertEquals(BigDecimal.valueOf(6400), savedOrder.getTotalPrice());
    }

}
