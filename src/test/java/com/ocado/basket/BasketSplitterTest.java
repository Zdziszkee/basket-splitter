package com.ocado.basket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.deserializer.BasketJsonDeserializer;
import com.ocado.basket.deserializer.Deserializer;
import com.ocado.basket.exception.DeserializerException;
import com.ocado.basket.exception.LoaderException;
import com.ocado.basket.loader.Loader;
import com.ocado.basket.loader.PathLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class BasketSplitterTest {
    private final ObjectMapper mapper = new ObjectMapper();
    final Loader<String, Path> loader = new PathLoader();
    final Deserializer<List<String>> basketDeserializer = new BasketJsonDeserializer(mapper);


    @Test
    public void testEmptyConfig() throws URISyntaxException {
        final BasketSplitter basketSplitter = new BasketSplitter(getResourcePathString("emptyConfig.json"));
        Assertions.assertThrows(IllegalStateException.class, () -> basketSplitter.split(List.of("item")));
    }

    @Test
    public void testCorrectBasketAndConfig() throws URISyntaxException, LoaderException, DeserializerException {
        final String json = loader.load(getResourcePath("basket-1.json"));
        final List<String> basket = basketDeserializer.deserialize(json);
        final BasketSplitter basketSplitter = new BasketSplitter(getResourcePathString("config.json"));
        final Map<String, List<String>> deliveryGroups = basketSplitter.split(basket);

        //expected
        Map<String, List<String>> expected = new HashMap<>();
        expected.put("Pick-up point",List.of("Fond - Chocolate"));
        expected.put("Courier",List.of("Cocoa Butter","Tart - Raisin And Pecan","Table Cloth 54x72 White","Flower - Daisies","Cookies - Englishbay Wht"));

        Assertions.assertEquals(deliveryGroups,expected);

    }

    private String getResourcePathString(final String resource) throws URISyntaxException {
        URL resourceURL = getClass().getClassLoader().getResource(resource);
        return Paths.get(resourceURL.toURI()).toAbsolutePath().toString();
    }

    private Path getResourcePath(final String resource) throws URISyntaxException {
        URL resourceURL = getClass().getClassLoader().getResource(resource);
        return Paths.get(resourceURL.toURI());
    }
}