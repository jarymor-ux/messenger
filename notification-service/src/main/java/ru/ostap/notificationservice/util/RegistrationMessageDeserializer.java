package ru.ostap.notificationservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import ru.ostap.notificationservice.messages.RegistrationMessage;

import java.io.IOException;

@Slf4j
public class RegistrationMessageDeserializer implements Deserializer<RegistrationMessage> {
    @Override
    public RegistrationMessage deserialize(String topic, byte[] data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, RegistrationMessage.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new RegistrationMessage(null, null);
    }

    @Override
    public void close() {
    }
}

