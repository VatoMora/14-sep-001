package com.bootcamp.pruebas.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bootcamp.pruebas.config.TestConfig;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Crea una única instancia de ExtentReports por ejecución y guarda
 * el ExtentTest "actual" por hilo para que otras clases puedan escribir en él.
 */
public final class ExtentManager {

    private static final Path REPORT_DIR = Path.of("target", "extent-report");
    private static final ExtentReports EXTENT = crearReporte();
    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();

    private ExtentManager() {
    }

    private static ExtentReports crearReporte() {
        try {
            Files.createDirectories(REPORT_DIR);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo crear el directorio del reporte", e);
        }

        ExtentSparkReporter spark = new ExtentSparkReporter(
                REPORT_DIR.resolve("reporte-funcional.html").toString());
        spark.config().setDocumentTitle("Pruebas funcionales - API Productos");
        spark.config().setReportName("ShopCloud - GET /api/v1/products/{id}");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setEncoding("UTF-8");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("URL base", TestConfig.BASE_URL);
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Sistema operativo", System.getProperty("os.name"));

        // Escribe el HTML al terminar la JVM (aunque haya pruebas fallidas).
        Runtime.getRuntime().addShutdownHook(new Thread(extent::flush));
        return extent;
    }

    public static ExtentTest startTest(String nombre, String categoria) {
        ExtentTest test = EXTENT.createTest(nombre).assignCategory(categoria);
        CURRENT.set(test);
        return test;
    }

    public static ExtentTest current() {
        return CURRENT.get();
    }

    public static void endTest() {
        CURRENT.remove();
    }
}