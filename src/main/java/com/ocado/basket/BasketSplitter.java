package com.ocado.basket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.deserializer.ConfigJsonDeserializer;
import com.ocado.basket.deserializer.Deserializer;
import com.ocado.basket.exception.DeserializerException;
import com.ocado.basket.exception.LoaderException;
import com.ocado.basket.loader.Loader;
import com.ocado.basket.loader.PathLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasketSplitter {
    private static final Logger logger = Logger.getLogger(BasketSplitter.class.getName());
    private final static ObjectMapper mapper = new ObjectMapper();

    private final static Loader<String, Path> loader = new PathLoader();
    protected Map<String, Set<String>> productAvailableDeliveries;

    /**
     * Exceptions here should be handled by caller.
     * but changing constructor signature might break ocado testing tool..
     */
    public BasketSplitter(String absolutePathToConfigFile) {
        try {
            final String json = loader.load(Paths.get(absolutePathToConfigFile));
            final Deserializer<Map<String, Set<String>>> configDeserializer = new ConfigJsonDeserializer(mapper);
            productAvailableDeliveries = configDeserializer.deserialize(json);
        } catch (LoaderException exception) {
            logger.log(Level.SEVERE, "Error loading config!", exception);
        } catch (DeserializerException exception) {
            logger.log(Level.SEVERE, "Error deserializing config!", exception);
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        final List<String> basket = new ArrayList<>(items);
        final Map<String, List<String>> deliveryGroups = new HashMap<>();

        while (!basket.isEmpty()) {
            final Map<String, Integer> deliveryFrequencies = new HashMap<>();

            for (final String item : basket) {
                final Set<String> deliveries = productAvailableDeliveries.get(item);

                if (deliveries == null) {
                    throw new IllegalStateException("Could not find available deliveries for product: " + item);
                }

                for (final String delivery : deliveries) {
                    deliveryFrequencies.put(delivery, deliveryFrequencies.getOrDefault(delivery, 0) + 1);
                }
            }

            final Map.Entry<String, Integer> mostFrequentDeliveryEntry = deliveryFrequencies.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow(() -> new IllegalStateException("Delivery config is empty!"));

            final List<String> deliveryGroup = new ArrayList<>();
            final String mostFrequentDelivery = mostFrequentDeliveryEntry.getKey();

            for (final String item : basket) {
                final Set<String> deliveries = productAvailableDeliveries.get(item);
                if (deliveries.contains(mostFrequentDelivery)) deliveryGroup.add(item);
            }
            basket.removeAll(deliveryGroup);

            deliveryGroups.put(mostFrequentDelivery, deliveryGroup);
        }
        return Collections.unmodifiableMap(deliveryGroups);
    }


}