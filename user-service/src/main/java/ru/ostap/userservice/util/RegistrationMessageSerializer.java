package ru.ostap.userservice.util;

import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ostap.userservice.messages.RegistrationMessage;

public class RegistrationMessageSerializer implements Serializer<RegistrationMessage> {
    @Override
    public byte[] serialize(String topic, RegistrationMessage data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing RegistrationMessage", e);
        }
    }

    @Override
    public void close() {}
}

