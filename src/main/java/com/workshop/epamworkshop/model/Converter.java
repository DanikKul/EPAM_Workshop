package com.workshop.epamworkshop.model;

import com.workshop.epamworkshop.cache.CacheResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Converter implements Comparable<Converter> {

    private Double userInput;
    private Double meters;
    private Double inches;
    private static final Logger logger = LogManager.getLogger(Converter.class);

    private static final CacheResult cache = new CacheResult();

    public Converter(Double value) {
        logger.info("Created Converter with double");
        this.userInput = value;
        this.meters = ConverterLogic.convertToMeters(value);
        this.inches = ConverterLogic.convertToInches(value);
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
        this.inches = ConverterLogic.convertToInches(value);
        this.meters = ConverterLogic.convertToInches(value);
    }

    @Override
    public int compareTo(Converter converter) {
        return this.getUserInput()
                .compareTo(converter.getUserInput());
    }

}