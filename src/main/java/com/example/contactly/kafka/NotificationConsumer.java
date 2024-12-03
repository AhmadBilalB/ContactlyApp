package com.example.contactly.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "notifly-notification", groupId = "contactly-group")
    public void consume(String message) {
        System.out.println("Received notification acknowledgment: " + message);
        // Process the notification acknowledgment here
    }
}
