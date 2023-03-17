package com.project.Sales.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String customerName;

    private String phoneNumber;

    private String productName;

    private Integer quantity;

}
