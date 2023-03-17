package com.project.Sales.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReportResponse {
    private Integer totalOrder;
    private BigDecimal totalOrderAmount;
}
