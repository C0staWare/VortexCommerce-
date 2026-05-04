package com.eventura.order.query.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class OrderView {
    @Id
    private String id;
    private String customerId;
    private String status;
    private BigDecimal totalAmount;
}