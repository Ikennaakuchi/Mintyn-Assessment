package com.project.Sales.dto.response;

import com.project.Sales.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class AvailableProductsResponse {
    private List<Product> products;

    public AvailableProductsResponse(List<Product> products) {
        this.products = products;
    }
}
