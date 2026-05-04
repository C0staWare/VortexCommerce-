package com.eventura.order.command.aggregate;

import com.eventura.order.common.event.OrderCreatedEvent;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderAggregate {
    private String id;
    private String status; // CREATED, PAID, CANCELLED
    private List<Object> changes = new ArrayList<>();

    public void apply(OrderCreatedEvent event) {
        this.id = event.getOrderId();
        this.status = "CREATED";
        // Aquí no guardamos en BD, solo actualizamos el estado en memoria del agregado
    }
}