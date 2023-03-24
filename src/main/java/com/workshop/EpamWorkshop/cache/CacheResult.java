package com.workshop.EpamWorkshop.cache;

import com.workshop.EpamWorkshop.model.Converter;
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
