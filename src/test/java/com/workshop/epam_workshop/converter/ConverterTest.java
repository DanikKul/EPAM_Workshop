package com.workshop.epam_workshop.converter;

import com.workshop.epam_workshop.errors.NegativeException;
import com.workshop.epam_workshop.model.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    public void testConverter() {
        Converter converter = new Converter(12);
        Assertions.assertEquals(12, converter.getMetersToInches().get("meters"));
        Assertions.assertEquals(472.44, converter.getMetersToInches().get("inches"));
        converter.setAll(0.);
        Assertions.assertEquals(0, converter.getMetersToInches().get("meters"));
        Assertions.assertEquals(0, converter.getInchesToMeters().get("inches"));
        converter.setAll(100.);
        Assertions.assertEquals(100, converter.getInchesToMeters().get("inches"));
        Assertions.assertEquals(2.54, converter.getInchesToMeters().get("meters"));
    }

    @Test
    public void testParseExceptionConverter() {
        Exception exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("asd"));
        assertEquals(NumberFormatException.class, exception.getClass());
        exception = assertThrows(NumberFormatException.class, () -> Converter.validateData(""));
        assertEquals(NumberFormatException.class, exception.getClass());
        exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("   "));
        assertEquals(NumberFormatException.class, exception.getClass());
        exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("12a"));
        assertEquals(NumberFormatException.class, exception.getClass());
        exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("a12"));
        assertEquals(NumberFormatException.class, exception.getClass());
        exception = assertThrows(NumberFormatException.class, () -> Converter.validateData("-12a"));
        assertEquals(NumberFormatException.class, exception.getClass());
    }

    @Test
    public void testNegativeExceptionConverter() {
        Exception exception = assertThrows(NegativeException.class, () -> Converter.validateData("-12"));
        assertEquals(NegativeException.class, exception.getClass());
        exception = assertThrows(NegativeException.class, () -> Converter.validateData("-0.001"));
        assertEquals(NegativeException.class, exception.getClass());
        exception = assertThrows(NegativeException.class, () -> Converter.validateData("-99"));
        assertEquals(NegativeException.class, exception.getClass());
    }

}