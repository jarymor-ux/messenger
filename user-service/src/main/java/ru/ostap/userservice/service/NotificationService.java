package ru.ostap.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.messages.RegistrationMessage;

@AllArgsConstructor
@Service
public class NotificationService {
    private KafkaTemplate<String, RegistrationMessage> kafkaTemplate;

    public void sendNotification(RegistrationMessage message) {
        kafkaTemplate.send("notificationTopic", message);
    }
}
