package com.workshop.epam_workshop.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Converter {

    private Double inches;
    private Double meters;
    private static final Logger logger = LogManager.getLogger(Converter.class);

    public Converter(Double value) {
        logger.info("Created Converter with double");
        this.meters = Math.round(value * 1000) / 1000.;
        this.inches = Math.round(value * 39.37 * 1000) / 1000.;
    }

    public Converter(Integer value) {
        logger.info("Created Converter with Integer");
        this.meters = Math.round(Double.valueOf(value) * 1000) / 1000.;
        this.inches =  Math.round(value * 39.37 * 1000) / 1000.;
    }

    public Double getMeters() {
        return meters;
    }

    public Double getInches() {
        return inches;
    }

    public void setInches(Double inches) {
        this.inches = inches;
    }

    public void setMeters(Double value) {
        this.meters = value;
    }

    public void setAll(Double value) {
        this.meters = Math.round(value * 1000) / 1000.;
        this.inches = Math.round(value * 39.37 * 1000) / 1000.;
    }

}
