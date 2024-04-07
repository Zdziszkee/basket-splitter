package com.ocado.basket.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.exception.DeserializerException;

import java.util.Map;
import java.util.Set;

public class ConfigJsonDeserializer implements Deserializer<Map<String, Set<String>>> {
    private final ObjectMapper mapper;

    public ConfigJsonDeserializer(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Map<String, Set<String>> deserialize(final String json) throws DeserializerException {
        try {
            final Map<String, Set<String>> config = mapper.readValue(json, new TypeReference<>() {
            });
            return config;
        } catch (JsonProcessingException exception) {
            throw new DeserializerException("Could not deserialize config from json!", exception);
        }
    }
}
