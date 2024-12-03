package com.example.contactly.kafka;

import com.example.contactly.exception.KafkaProducerException;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.errors.UnknownTopicOrPartitionException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class ContactProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ContactProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendNewContact(String contactJson) {
//        if (contactJson != null && !contactJson.isEmpty()) {
//            kafkaTemplate.send("new-contacts", contactJson);
//            System.out.println("Sent new contact: " + contactJson);
//        } else {
//            System.out.println("Contact JSON is empty or null");
//        }


        if (contactJson != null && !contactJson.isEmpty()) {
            try {
                SendResult<String, String> result = kafkaTemplate.send("new-contacts", contactJson).get();
                System.out.println("Sent new contact: " + contactJson);
                return true;
            } catch (UnknownTopicOrPartitionException e) {
                throw new KafkaProducerException("TOPIC_NOT_FOUND", "Kafka topic 'new-contacts' not found. Please ensure the topic exists.");
            } catch (TimeoutException e) {
                throw new KafkaProducerException("TIMEOUT_ERROR", "Timeout occurred while sending message to Kafka. Please check the Kafka broker status.");
            } catch (Exception e) {
                throw new KafkaProducerException("GENERAL_ERROR", "An error occurred while sending message to Kafka: " + e.getMessage(), e);
            }
        } else {
            throw new KafkaProducerException("EMPTY_JSON", "Contact JSON is empty or null.");
        }
    }
}
