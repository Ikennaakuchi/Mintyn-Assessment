package com.project.Sales.controller;

import com.project.Sales.dto.request.NewProductRequest;
import com.project.Sales.dto.request.ProductUpdateRequest;
import com.project.Sales.dto.response.ApiResponse;
import com.project.Sales.dto.response.AvailableProductsResponse;
import com.project.Sales.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("add-new-product")
    public ResponseEntity<ApiResponse> addNewProduct(@RequestBody NewProductRequest request){
       ApiResponse response = productService.addNewProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("update-product/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId,
                                                     @RequestBody ProductUpdateRequest request){
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @GetMapping("get-available-products")
    public ResponseEntity<ApiResponse<AvailableProductsResponse>> getAvailableProducts(){
        return ResponseEntity.ok(productService.getAvailableProducts());
    }
}
