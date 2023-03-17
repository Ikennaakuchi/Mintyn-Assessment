package com.project.Sales.controller;

import com.project.Sales.dto.request.OrderRequest;
import com.project.Sales.dto.response.ApiResponse;
import com.project.Sales.dto.response.OrderResponse;
import com.project.Sales.messaging.OrderProducer;
import com.project.Sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order/")
public class OrderController {
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    @PostMapping("create-order")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request){
        OrderResponse response = orderService.createOrder(request);
        orderProducer.sendOrder(response);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Order placed successfully", response),
                HttpStatus.CREATED);
    }
}
