package com.project.Sales.model;

import com.project.Sales.enums.Availability;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_tbl")
public class Product extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private Integer quantity;
    @Enumerated(value = EnumType.STRING)
    private Availability availability;
    private BigDecimal productPrice;

    @Override
    public void setCreatedAt(LocalDateTime now) {

    }
    @Override
    public void setUpdatedAt(LocalDateTime now) {

    }
}
