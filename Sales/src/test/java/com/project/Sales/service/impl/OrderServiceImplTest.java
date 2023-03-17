package com.project.Sales.service.impl;

import com.project.Sales.dto.request.OrderRequest;
import com.project.Sales.dto.response.OrderResponse;
import com.project.Sales.enums.Availability;
import com.project.Sales.exception.OutOfStockException;
import com.project.Sales.exception.ProductNotFoundException;
import com.project.Sales.model.Product;
import com.project.Sales.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    Product product;
    String productName;
    Integer quantity;
    BigDecimal productPrice;
    OrderRequest orderRequest;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        productName = "Car";
        quantity = 5;
        productPrice = new BigDecimal(200000);
        product = new Product();
        product.setProductName(productName);
        product.setAvailability(Availability.AVAILABLE);
        product.setProductPrice(productPrice);
        product.setQuantity(quantity);


        orderRequest = new OrderRequest();
        orderRequest.setCustomerName("Ronnie Jr");
        orderRequest.setPhoneNumber("09023432123");
        orderRequest.setProductName(productName);
        orderRequest.setQuantity(quantity);

    }
    @Test
    public void testCreateOrder() {

        when(productRepository.findByProductNameIgnoreCase(productName)).thenReturn(Optional.of(product));

        OrderResponse orderResponse = orderService.createOrder(orderRequest);

        assertEquals(orderRequest.getCustomerName(), orderResponse.getCustomerName());
        assertEquals(orderRequest.getPhoneNumber(), orderResponse.getPhoneNumber());
        assertEquals(productName, orderResponse.getProductName());
        assertEquals(quantity, orderResponse.getQuantity());
        assertEquals(productPrice, orderResponse.getProductPrice());
        assertEquals(productPrice.multiply(new BigDecimal(quantity)), orderResponse.getTotalPrice());

        verify(productRepository).findByProductNameIgnoreCase(productName);
        verify(productRepository).save(product);
    }

    @Test
    public void testCreateOrderWithNonexistentProduct() {
        productName = "not available product";
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerName("Ron");
        orderRequest.setPhoneNumber("0934542134");
        orderRequest.setProductName(productName);
        orderRequest.setQuantity(1);

        when(productRepository.findByProductNameIgnoreCase(productName)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            orderService.createOrder(orderRequest);
        });
    }


    @Test
    public void testCreateOrderWithOutOfStockProduct() {
        String productName = "AnyProduct";
        Integer quantity = 0;
        BigDecimal productPrice = new BigDecimal(20000);
        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setAvailability(Availability.OUT_OF_STOCK);
        product.setQuantity(quantity);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerName("Raymond");
        orderRequest.setPhoneNumber("0870989890");
        orderRequest.setProductName(productName);
        orderRequest.setQuantity(1);

        when(productRepository.findByProductNameIgnoreCase(productName)).thenReturn(Optional.of(product));

        assertThrows(OutOfStockException.class, () -> {
            orderService.createOrder(orderRequest);
        });
    }

    @Test
    public void testCreateOrderWithInsufficientQuantity() {
        String productName = "AnyProduct";
        Integer quantity = 2;
        BigDecimal productPrice = new BigDecimal(20000);
        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setAvailability(Availability.AVAILABLE);
        product.setQuantity(quantity);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerName("Rabiu");
        orderRequest.setPhoneNumber("0898345432");
        orderRequest.setProductName(productName);
        orderRequest.setQuantity(quantity + 1);

        when(productRepository.findByProductNameIgnoreCase(productName)).thenReturn(Optional.of(product));

        assertThrows(OutOfStockException.class, () -> {
            orderService.createOrder(orderRequest);
        });
    }

}
