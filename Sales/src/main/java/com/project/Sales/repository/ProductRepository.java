package com.project.Sales.repository;

import com.project.Sales.enums.Availability;
import com.project.Sales.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByAvailability(Availability availability);
    Optional<Product> findByProductNameIgnoreCase(String productName);
}
