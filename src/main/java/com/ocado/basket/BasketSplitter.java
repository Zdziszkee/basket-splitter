package com.ocado.basket;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BasketSplitter {
    protected static final ObjectMapper mapper = new ObjectMapper();
    protected final Map<String, Set<String>> productAvailableDeliveries = new HashMap<>();

    public BasketSplitter(String absolutePathToConfigFile) {
        try {
            productAvailableDeliveries.putAll(mapper.readValue(Files.readString(Paths.get(absolutePathToConfigFile)), new TypeReference<Map<String, Set<String>>>() {
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String,List<String>> result = new HashMap<>();

        while(!items.isEmpty()){
            //produkt -> liczba typow dostaw
            //grupa  -> liczba produktow
            Map<String,Integer> map = new TreeMap<>();
            for (String item : items) {
                Set<String> deliveries = productAvailableDeliveries.get(item);
                for (String deliveryType : deliveries) {
                    map.put(deliveryType,  map.getOrDefault(deliveryType,0)+1);

                }
            }

            Map.Entry<String, Integer> max = map.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .get();


            List<String> itemsFromGroup = new ArrayList<>();

            String key = max.getKey();

            for (String item : items) {
                Set<String> deliveries = productAvailableDeliveries.get(item);
                if(deliveries.contains(key))itemsFromGroup.add(item);
            }

            items.removeAll(itemsFromGroup);

            result.put(key,itemsFromGroup);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BasketSplitter basketSplitter = new BasketSplitter("C:\\Users\\Zdziszkee\\IdeaProjects\\basket-splitter\\src\\main\\resources\\config.json");
        List<String> strings = mapper.readValue(Files.readString(Paths.get("C:\\Users\\Zdziszkee\\IdeaProjects\\basket-splitter\\src\\main\\resources\\output.json")), new TypeReference<List<String>>() {
        });
        Map<String, List<String>> split = basketSplitter.split(strings);


        System.out.println(split);
//
//        List<String> products = basketSplitter.productAvailableDeliveries.keySet().stream().toList();
//
//        Set<String> basket = new HashSet<>();
//
//        while(basket.size()!=100){
//            basket.add(products.get( ThreadLocalRandom.current().nextInt(0,products.size())));
//
//        }
//        System.out.println(basket.size());
//        String json = mapper.writeValueAsString(basket);
//        Files.write(Paths.get("C:\\Users\\Zdziszkee\\IdeaProjects\\basket-splitter\\src\\main\\resources\\output.json"), json.getBytes());
    }
}