package com.ocado.basket.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ocado.basket.exception.DeserializerException;

public interface Deserializer<T> {
    T deserialize(String json) throws DeserializerException;
}
