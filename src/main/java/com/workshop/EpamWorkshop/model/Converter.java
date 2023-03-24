package com.workshop.EpamWorkshop.model;

import com.workshop.EpamWorkshop.cache.CacheResult;
import com.workshop.EpamWorkshop.errors.NegativeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Converter implements Comparable<Converter> {

    private Double userInput;
    private Double meters;
    private Double inches;
    private static final Logger logger = LogManager.getLogger(Converter.class);

    private static final CacheResult cache = new CacheResult();

    public Converter(Double value) {
        logger.info("Created Converter with double");
        this.userInput = value;
        this.meters = convertToMeters(value);
        this.inches = convertToInches(value);
    }

    public Double getInches() {
        return inches;
    }

    public Double getMeters() {
        return meters;
    }

    public Double getUserInput() {
        return this.userInput;
    }

    public void setInchesToMeters(Double inches) {
        this.inches = inches;
    }

    public void setMetersToInches(Double meters) {
        this.meters = meters;
    }

    public void setUserInput(Double input) {
        this.userInput = input;
    }

    public void setAll(Double value) {
        this.userInput = value;
        this.inches = convertToInches(value);
        this.meters = convertToInches(value);
    }

    public static Double parseData(String value) throws NegativeException, NumberFormatException {
        double d = Double.parseDouble(value);
        if (d < 0) throw new NegativeException("Value can't be negative!");
        return d;
    }

    public static Boolean validateData(String value) {
        double d = 0;
        try {
            d = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return !(d < 0);
    }

    private Double convertToInches(Double meters) {
        return Math.round(meters * 39.37 * 1000) / 1000.;
    }

    private Double convertToMeters(Double inches) {
        return Math.round(inches / 39.37 * 1000) / 1000.;
    }

    @Override
    public int compareTo(Converter converter) {
        return this.getUserInput()
                .compareTo(converter.getUserInput());
    }

}