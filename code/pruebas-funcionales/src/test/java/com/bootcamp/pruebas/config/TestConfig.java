package com.bootcamp.pruebas.config;

/**
 * Configuración central de las pruebas.
 * La URL base sale de la propiedad del sistema "base.url" (ver pom.xml / -Dbase.url=...).
 */
public final class TestConfig {

    public static final String BASE_URL = System.getProperty("base.url", "https://54.161.157.90");

// URL http:// usada solo por la prueba de redirección (derivada de BASE_URL)
public static final String HTTP_URL = System.getProperty("http.url", BASE_URL.replaceFirst("^https://", "http://"));

    private TestConfig() {
    }
}