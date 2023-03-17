package com.project.Sales.service.impl;

import com.project.Sales.dto.request.NewProductRequest;
import com.project.Sales.dto.request.ProductUpdateRequest;
import com.project.Sales.dto.response.ApiResponse;
import com.project.Sales.dto.response.AvailableProductsResponse;
import com.project.Sales.enums.Availability;
import com.project.Sales.exception.OutOfStockException;
import com.project.Sales.exception.ProductNotFoundException;
import com.project.Sales.model.Product;
import com.project.Sales.repository.ProductRepository;
import com.project.Sales.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ApiResponse addNewProduct(NewProductRequest request) {
        Product newProduct = new Product();
        newProduct.setProductName(request.getProductName());
        newProduct.setDescription(request.getDescription());
        newProduct.setProductPrice(request.getProductPrice());
        newProduct.setQuantity(request.getQuantity());
        newProduct.setAvailability(Availability.valueOf("AVAILABLE"));
        newProduct.setCreatedAt(LocalDateTime.now());
        productRepository.save(newProduct);

        return new ApiResponse("Success", "Product added successfully", null);
    }

    @Override
    public ApiResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getProductPrice() != null) {
            product.setProductPrice(request.getProductPrice());
        }
        if (request.getQuantity() != null) {
            product.setQuantity(request.getQuantity());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return new ApiResponse("Success", "Product updated successfully", null);
    }

    @Override
    public ApiResponse<AvailableProductsResponse> getAvailableProducts() {
        List<Product> availableProducts = productRepository.findAllByAvailability(Availability.AVAILABLE);
        if(availableProducts.isEmpty()){
            throw new OutOfStockException("There is no product available");
        }
        AvailableProductsResponse availableProductsResponse = new AvailableProductsResponse(availableProducts);

        return new ApiResponse<>("Success", "List of available products", availableProductsResponse);
    }
}
