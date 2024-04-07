package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

class BasketSplitterTest {
    @Test
    public void testEmptyConfig() throws URISyntaxException{
        final BasketSplitter basketSplitter = new BasketSplitter(getAbsolutePath(getAbsolutePath("emptyConfig.json")));

    }

    private String getAbsolutePath(String resource) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(resource);

        URI uri = url.toURI();

        String path = uri.getPath();
        return  path;
    }
}