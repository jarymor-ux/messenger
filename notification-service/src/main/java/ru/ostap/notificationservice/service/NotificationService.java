package ru.ostap.notificationservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.ostap.notificationservice.messages.RegistrationMessage;

@Service
@EnableKafka
@Slf4j
@AllArgsConstructor
public class NotificationService {

    private MailService mailService;

    @KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
    public void listen(RegistrationMessage message) {
        mailService.sendSimpleEmail(message.getEmail(),"Registration on messenger",
                "Dear " + message.getUsername() + " your registration on MyMessengerApp successfully processed");
        log.info("Send mail to: {}", message);
    }
}

