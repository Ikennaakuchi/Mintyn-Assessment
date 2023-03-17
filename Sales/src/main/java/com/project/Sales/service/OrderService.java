package com.project.Sales.service;

import com.project.Sales.dto.request.OrderRequest;
import com.project.Sales.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
}
