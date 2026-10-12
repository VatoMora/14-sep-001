package com.bootcamp.pruebas.reporting;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * Extensión de JUnit 5: abre un ExtentTest antes de cada prueba
 * y registra el resultado (pass / fail / skip) al terminar.
 */
public class ExtentReportExtension implements BeforeEachCallback, TestWatcher {

    @Override
    public void beforeEach(ExtensionContext context) {
        String categoria = context.getRequiredTestClass().getSimpleName();
        ExtentManager.startTest(context.getDisplayName(), categoria);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentManager.current().pass("La prueba terminó correctamente");
        ExtentManager.endTest();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentManager.current().fail(cause);
        ExtentManager.endTest();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        ExtentManager.current().skip("Prueba omitida: " + cause.getMessage());
        ExtentManager.endTest();
    }
}