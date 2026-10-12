package com.bootcamp.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxCalculatorTest {

    @Test
    void shouldCalculateIvaCorrectly() {
        double basePrice = 100.0;
        TaxCalculator helper = new TaxCalculator();
        
        double iva = helper.calculateIva(basePrice);
        
        assertEquals(16.0, iva, 0.001);
    }

    @Test
    void shouldReturnZeroIvaWhenPriceIsZeroOrNegative() {
        TaxCalculator helper = new TaxCalculator();

        assertEquals(0.0, helper.calculateIva(0.0), 0.001);
        
        assertEquals(0.0, helper.calculateIva(-50.0), 0.001);
    }
}