package com.project.Sales.service;

import com.project.Sales.dto.request.NewProductRequest;
import com.project.Sales.dto.request.ProductUpdateRequest;
import com.project.Sales.dto.response.ApiResponse;
import com.project.Sales.dto.response.AvailableProductsResponse;

public interface ProductService {
    ApiResponse addNewProduct(NewProductRequest request);
    ApiResponse updateProduct(Long productId, ProductUpdateRequest request);
    ApiResponse<AvailableProductsResponse> getAvailableProducts();
}
