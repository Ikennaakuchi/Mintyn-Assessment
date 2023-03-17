package com.project.Sales.service.impl;

import com.project.Sales.dto.request.NewProductRequest;
import com.project.Sales.dto.request.ProductUpdateRequest;
import com.project.Sales.dto.response.ApiResponse;
import com.project.Sales.dto.response.AvailableProductsResponse;
import com.project.Sales.enums.Availability;
import com.project.Sales.exception.OutOfStockException;
import com.project.Sales.model.Product;
import com.project.Sales.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    NewProductRequest request;
    Product newProduct;
    ProductUpdateRequest updateRequest;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        request = new NewProductRequest();
        request.setProductName("Car");
        request.setDescription("Description of Car");
        request.setProductPrice(BigDecimal.valueOf(100000.0));
        request.setQuantity(100);

        newProduct = new Product();
        newProduct.setProductName(request.getProductName());
        newProduct.setDescription(request.getDescription());
        newProduct.setProductPrice(request.getProductPrice());
        newProduct.setQuantity(request.getQuantity());
        newProduct.setAvailability(Availability.AVAILABLE);
        newProduct.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testAddNewProduct() {

        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        ApiResponse response = productService.addNewProduct(request);

        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals("Success", response.getStatus());
        assertEquals("Product added successfully", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        updateRequest = new ProductUpdateRequest();
        updateRequest.setProductName("Laptop");
        updateRequest.setDescription("Updated description of Laptop");
        updateRequest.setProductPrice(BigDecimal.valueOf(20000.0));
        updateRequest.setQuantity(200);

        Product product = new Product();
        product.setProductName("Laptop");
        product.setDescription("Updated description of Laptop");
        product.setProductPrice(BigDecimal.valueOf(10.0));
        product.setQuantity(100);
        product.setAvailability(Availability.AVAILABLE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ApiResponse response = productService.updateProduct(productId, updateRequest);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals("Success", response.getStatus());
        assertEquals("Product updated successfully", response.getMessage());
        assertNull(response.getData());
        assertEquals(updateRequest.getProductName(), product.getProductName());
        assertEquals(updateRequest.getDescription(), product.getDescription());
        assertEquals(updateRequest.getProductPrice(), product.getProductPrice());
        assertEquals(updateRequest.getQuantity(), product.getQuantity());
    }

    @Test
    public void testGetAvailableProducts() {
        List<Product> availableProducts = Collections.singletonList(newProduct);
        when(productRepository.findAllByAvailability(Availability.AVAILABLE)).thenReturn(availableProducts);

        ApiResponse<AvailableProductsResponse> response = productService.getAvailableProducts();

        assertEquals("Success", response.getStatus());
        assertEquals("List of available products", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(availableProducts.size(), response.getData().getProducts().size());

        verify(productRepository, times(1)).findAllByAvailability(Availability.AVAILABLE);
    }

    @Test
    public void testGetAvailableProductsEmptyList() {
        when(productRepository.findAllByAvailability(Availability.AVAILABLE)).thenReturn(Arrays.asList());

        assertThrows(OutOfStockException.class, () -> {
            productService.getAvailableProducts();
        });

    }
}