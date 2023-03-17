package com.project.Sales.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
        private String productName;
        private String description;
        private Integer quantity;
        private BigDecimal productPrice;
}
