package com.project.Sales.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderResponse implements Serializable {

    private String customerName;

    private String phoneNumber;

    private String productName;

    private BigDecimal productPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

}
