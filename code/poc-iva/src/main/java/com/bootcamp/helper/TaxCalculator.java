package com.bootcamp.helper;

public class TaxCalculator {

    private static final double IVA_RATE = 0.16;

    public double calculateIva(double price) {
        if (price <= 0) {
            return 0.0;
        }
        return price * IVA_RATE;
    }
}