package com.workshop.epam_workshop.model;

import com.workshop.epam_workshop.cache.CacheResult;
import com.workshop.epam_workshop.errors.NegativeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Converter {

    private final HashMap<String, Double> metersToInches = new HashMap<>();
    private final HashMap<String, Double> inchesToMeters = new HashMap<>();
    private Double userInput;
    private static final Logger logger = LogManager.getLogger(Converter.class);

    private static final CacheResult cache = new CacheResult();

    public Converter(Double value) {
        logger.info("Created Converter with double");
        this.userInput = value;
        this.metersToInches.put("meters", value);
        this.metersToInches.put("inches", Math.round(value * 39.37 * 1000) / 1000.);
        this.inchesToMeters.put("inches", value);
        this.inchesToMeters.put("meters", Math.round(value / 39.37 * 1000) / 1000.);
    }

    public Converter(Integer value) {
        logger.info("Created Converter with Integer");
        this.userInput = Double.valueOf(value);
        this.metersToInches.put("meters", Double.valueOf(value));
        this.metersToInches.put("inches", Math.round(value * 39.37 * 1000) / 1000.);
        this.inchesToMeters.put("inches", Double.valueOf(value));
        this.inchesToMeters.put("meters", Math.round(value / 39.37 * 1000) / 1000.);
    }

    public HashMap<String, Double> getMetersToInches() {
        return metersToInches;
    }

    public HashMap<String, Double> getInchesToMeters() {
        return inchesToMeters;
    }

    public Double getUserInput() {
        return this.userInput;
    }

    public void setInchesToMeters(Double inches) {
        this.inchesToMeters.put("inches", inches);
        this.inchesToMeters.put("meters", Math.round(inches / 39.37 * 1000) / 1000.);
    }

    public void setMetersToInches(Double meters) {
        this.metersToInches.put("meters", meters);
        this.metersToInches.put("inches", Math.round(meters * 39.37 * 1000) / 1000.);
    }

    public void setUserInput(Double input) {
        this.userInput = input;
    }

    public void setAll(Double value) {
        this.metersToInches.put("meters", value);
        this.metersToInches.put("inches", Math.round(value * 39.37 * 1000) / 1000.);
        this.inchesToMeters.put("inches", value);
        this.inchesToMeters.put("meters", Math.round(value / 39.37 * 1000) / 1000.);
    }

    public static double validateData(String value)
            throws NegativeException, NumberFormatException {
        double d = Double.parseDouble(value);
        if (d < 0) throw new NegativeException("Value can't be negative!");
        return d;
    }

}
