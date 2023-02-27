package com.workshop.epam_workshop.converter;

import com.workshop.epam_workshop.errors.NegativeException;
import com.workshop.epam_workshop.model.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    public void testIntegerConverter() {
        Converter converter = new Converter(12);
        Assertions.assertEquals(12, converter.getMetersToInches().get("meters"));
        Assertions.assertEquals(472.44, converter.getMetersToInches().get("inches"));
    }

    @Test
    public void testZerosConverter() {
        Converter converter = new Converter(0);
        Assertions.assertEquals(0, converter.getMetersToInches().get("meters"));
        Assertions.assertEquals(0, converter.getInchesToMeters().get("inches"));
    }

    @Test
    public void testDoubleConverter() {
        Converter converter = new Converter(100.0);
        Assertions.assertEquals(100, converter.getInchesToMeters().get("inches"));
        Assertions.assertEquals(2.54, converter.getInchesToMeters().get("meters"));
    }

    @Test
    public void testStringConverter() {
        Exception exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("asd"));
        assertEquals(NumberFormatException.class, exception.getClass());
    }

    @Test
    public void testEmptyConverter() {
        Exception exception = assertThrows(NumberFormatException.class, () -> Converter.validateData(""));
        assertEquals(NumberFormatException.class, exception.getClass());
    }

    @Test
    public void testSpacesConverter() {
        Exception exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("   "));
        assertEquals(NumberFormatException.class, exception.getClass());
    }

    @Test
    public void testNumberWithStringConverter() {
        Exception exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("12a"));
        assertEquals(NumberFormatException.class, exception.getClass());
    }

    @Test
    public void testNegativeIntegernConverter() {
        Exception exception = assertThrows(NegativeException.class, () -> Converter.validateData("-12"));
        assertEquals(NegativeException.class, exception.getClass());
    }

    @Test
    public void testNegativeDoubleConverter() {
        Exception exception = assertThrows(NegativeException.class, () -> Converter.validateData("-0.001"));
        assertEquals(NegativeException.class, exception.getClass());
    }

}