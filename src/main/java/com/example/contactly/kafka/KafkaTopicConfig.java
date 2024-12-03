package com.example.contactly.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic contactlyNewContactTopic() {
        return new NewTopic("new-contacts", 1, (short) 1); // 1 partition, 1 replica
    }
}
