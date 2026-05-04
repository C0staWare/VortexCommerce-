package com.eventura.order.command.controller;

import com.eventura.order.common.event.OrderCreatedEvent;
import com.eventura.order.command.model.EventModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final ReactiveMongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public Mono<String> createOrder(@RequestBody OrderCreatedEvent event) {
        event.setOrderId(UUID.randomUUID().toString());
        
        EventModel eventModel = EventModel.builder()
                .timestamp(LocalDateTime.now())
                .aggregateIdentifier(event.getOrderId())
                .aggregateType("Order")
                .eventType(event.getClass().getSimpleName())
                .eventData(event)
                .build();

        return mongoTemplate.save(eventModel)
                .doOnNext(e -> kafkaTemplate.send("order-events", event.getOrderId(), event))
                .map(e -> e.getAggregateIdentifier());
    }
}