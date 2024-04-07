package com.ocado.basket.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.exception.DeserializerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

class ConfigJsonDeserializerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testInvalidJson() {
        final Deserializer<Map<String, Set<String>>> deserializer = new ConfigJsonDeserializer(mapper);

        Assertions.assertThrows(DeserializerException.class, () -> {
            deserializer.deserialize("invalidjson");

        });
    }

}