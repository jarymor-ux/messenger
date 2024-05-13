package ru.ostap.notificationservice.util;

import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ostap.notificationservice.messages.RegistrationMessage;

public class RegistrationMessageDeserializer implements Deserializer<RegistrationMessage> {
    @Override
    public RegistrationMessage deserialize(String topic, byte[] data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, RegistrationMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing RegistrationMessage", e);
        }
    }

    @Override
    public void close() {}
}

