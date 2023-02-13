package com.workshop.epam_workshop.model;

public class Converter {

    private Double inches;
    private Double meters;

    public Converter(Double value) {
        this.meters = value;
        this.inches = value * 39.37;
    }

    public Converter(Integer value) {
        this.meters = Double.valueOf(value);
        this.inches = value * 39.37;
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

}
