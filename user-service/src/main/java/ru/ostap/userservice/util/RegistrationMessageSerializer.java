package ru.ostap.userservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import ru.ostap.userservice.messages.RegistrationMessage;

@Slf4j
public class RegistrationMessageSerializer implements Serializer<RegistrationMessage> {
    @Override
    public byte[] serialize(String topic, RegistrationMessage data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void close() {
    }
}