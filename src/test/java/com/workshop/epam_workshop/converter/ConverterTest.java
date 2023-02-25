package com.workshop.epam_workshop.converter;

import com.workshop.epam_workshop.model.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConverterTest {

    @Test
    public void testConverter() {
        Converter con = new Converter(12);
        Assertions.assertEquals(12, con.getMetersToInches().get("meters"));
        Assertions.assertEquals(472.44, con.getInchesToMeters().get("inches"));
        con.setAll(0.);
        Assertions.assertEquals(0, con.getMetersToInches().get("meters"));
        Assertions.assertEquals(0, con.getInchesToMeters().get("inches"));
        con.setAll(100.);
        Assertions.assertEquals(100, con.getMetersToInches().get("meters"));
        Assertions.assertEquals(3937, con.getInchesToMeters().get("inches"));
    }

}