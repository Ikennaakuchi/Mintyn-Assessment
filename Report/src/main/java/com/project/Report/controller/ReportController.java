package com.project.Report.controller;


import com.project.Report.service.OrderReportService;
import com.project.Sales.dto.response.OrderReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reports")
public class ReportController {
    private final OrderReportService orderReportService;

    @GetMapping("/orders")
    public ResponseEntity<Map<LocalDate, OrderReportResponse>> getOrderReports(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<LocalDate, OrderReportResponse> orderReports = orderReportService.getOrderReports(startDate, endDate);
        return ResponseEntity.ok().body(orderReports);
    }

}

