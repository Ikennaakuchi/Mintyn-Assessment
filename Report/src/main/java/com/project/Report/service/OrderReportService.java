package com.project.Report.service;

import com.project.Sales.dto.response.OrderReportResponse;
import com.project.Sales.dto.response.OrderResponse;

import java.time.LocalDate;
import java.util.Map;

public interface OrderReportService {
    void processOrder(OrderResponse orderResponse);
    Map<LocalDate, OrderReportResponse> getOrderReports(LocalDate startDate, LocalDate endDate);
}
