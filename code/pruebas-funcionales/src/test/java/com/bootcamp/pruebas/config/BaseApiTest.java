package com.bootcamp.pruebas.config;

import com.bootcamp.pruebas.reporting.ExtentLogFilter;
import com.bootcamp.pruebas.reporting.ExtentReportExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Clase base: prepara la especificación común de REST Assured y
 * registra la extensión que escribe los resultados en ExtentReports.
 */
@ExtendWith(ExtentReportExtension.class)
public abstract class BaseApiTest {

    protected static RequestSpecification spec;

    @BeforeAll
    protected static void configurarRestAssured() {
        spec = new RequestSpecBuilder()
                .setBaseUri(TestConfig.BASE_URL)
                .setAccept(ContentType.JSON)
                // El laboratorio usa un certificado autofirmado en Kong.
                // En un entorno real, usa un truststore en lugar de desactivar la validación.
                .setRelaxedHTTPSValidation()
                // Cada petición/respuesta queda registrada en el reporte.
                .addFilter(new ExtentLogFilter())
                .build();
    }
}