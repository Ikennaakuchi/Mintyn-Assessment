package com.project.Sales.model;

import com.project.Sales.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_tbl")
public class Order extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String customerName;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal productPrice;

    private Integer quantity;

    private BigDecimal totalPrice;
    @Override
    public void setCreatedAt(LocalDateTime now) {
    }

    @Override
    public void setUpdatedAt(LocalDateTime now) {
    }
}
