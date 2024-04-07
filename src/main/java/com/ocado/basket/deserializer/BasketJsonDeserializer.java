package com.ocado.basket.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.exception.DeserializerException;

import java.util.List;

public class BasketJsonDeserializer implements Deserializer<List<String>> {

    private final ObjectMapper mapper;

    public BasketJsonDeserializer(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<String> deserialize(String json) throws DeserializerException {
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new DeserializerException("Could not deserialize basket from json!",e);
        }
    }
}
