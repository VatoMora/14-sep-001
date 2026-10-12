package com.bootcamp.helper;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("   CÁLCULO DE IVA - PRODUCTOS SERVICE");
        System.out.println("=========================================");
        
        System.out.print("Ingrese el precio base del producto: $");
        double basePrice = scanner.nextDouble();

        double iva = new TaxCalculator().calculateIva(basePrice);
        double totalPrice = basePrice + iva;

        System.out.println("-----------------------------------------");
        System.out.printf("Precio base: $%.2f%n", basePrice);
        System.out.printf("IVA (16%%):   $%.2f%n", iva);
        System.out.printf("Total:       $%.2f%n", totalPrice);
        System.out.println("=========================================");

        scanner.close();
    }
}