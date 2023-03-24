package com.workshop.epamworkshop.model;

import com.workshop.epamworkshop.errors.NegativeException;

public class ConverterLogic {
    public static Double convertToInches(Double meters) {
        return Math.round(meters * 39.37 * 1000) / 1000.;
    }
    public static Double convertToMeters(Double inches) {
        return Math.round(inches / 39.37 * 1000) / 1000.;
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
}
