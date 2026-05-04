package com.eventura.order.command.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "order_events")
public class EventModel {
    @Id
    private String id;
    private LocalDateTime timestamp;
    private String aggregateIdentifier;
    private String aggregateType;
    private String eventType;
    private Object eventData;
}