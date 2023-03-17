package com.project.Sales.service.impl;

import com.project.Sales.dto.request.OrderRequest;
import com.project.Sales.dto.response.OrderResponse;
import com.project.Sales.enums.Availability;
import com.project.Sales.exception.OutOfStockException;
import com.project.Sales.exception.ProductNotFoundException;
import com.project.Sales.model.Product;
import com.project.Sales.repository.ProductRepository;
import com.project.Sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Optional<Product> product = productRepository.findByProductNameIgnoreCase(request.getProductName());
        if (product.isEmpty()){
            throw new ProductNotFoundException(String.format("Product with the name %s not found", request.getProductName()));
        }
        Product foundProduct = product.get();
        if (foundProduct.getAvailability().equals(Availability.OUT_OF_STOCK)){
            throw new OutOfStockException("Product is out of stock");
        }
        if(request.getQuantity() > foundProduct.getQuantity()){
            throw new OutOfStockException(String.format("Insufficient Quantity, Quantity available: %s", foundProduct.getQuantity()));
        }

        BigDecimal totalPrice = foundProduct.getProductPrice().multiply(new BigDecimal(request.getQuantity()));

        updateProduct(request, foundProduct);

        return mapToResponse(request, foundProduct, totalPrice);
    }

    private void updateProduct(OrderRequest request, Product foundProduct) {
        Integer updatedProductQty = foundProduct.getQuantity() - request.getQuantity();
        foundProduct.setQuantity(updatedProductQty);
        if (updatedProductQty == 0){
            foundProduct.setAvailability(Availability.OUT_OF_STOCK);
        }
        productRepository.save(foundProduct);
    }

    private static OrderResponse mapToResponse(OrderRequest request, Product foundProduct,
                                               BigDecimal totalPrice) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCustomerName(request.getCustomerName());
        orderResponse.setPhoneNumber(request.getPhoneNumber());
        orderResponse.setProductName(foundProduct.getProductName());
        orderResponse.setQuantity(request.getQuantity());
        orderResponse.setProductPrice(foundProduct.getProductPrice());
        orderResponse.setTotalPrice(totalPrice);
        return orderResponse;
    }
}



