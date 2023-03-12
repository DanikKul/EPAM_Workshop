package com.workshop.epam_workshop.cache;

import com.workshop.epam_workshop.model.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CacheResult {
    private final HashMap<Double, Converter> cache = new HashMap<>();

    public Converter getCache(Double value) {
        return cache.get(value);
    }

    public void putCache(Converter object) {
        this.cache.put(object.getUserInput(), object);
    }

    public Boolean contains(Double value) {
        return this.cache.containsKey(value);
    }

}
