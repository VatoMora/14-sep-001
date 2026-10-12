package com.bootcamp.pruebas.tests;

import com.bootcamp.pruebas.config.BaseApiTest;
import com.bootcamp.pruebas.config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Pruebas funcionales del endpoint GET /api/v1/products/{id}.
 *
 * Datos esperados (seed.sql):
 *   producto 1 -> Laptop Pro 14, 25000.00, 10% de descuento, stock 15
 *   producto 2 -> Mouse Inalambrico, 450.00, 25% de descuento
 *   producto 3 -> Teclado Mecanico, 1800.00, sin descuento, stock 40
 */
@DisplayName("API Productos: GET /api/v1/products/{id}")
class ProductosApiTest extends BaseApiTest {

    private static final String ENDPOINT = "/api/v1/products/{id}";
    private static final double DELTA = 0.001;

    @Test
    @DisplayName("Producto con descuento: 200 y detalle consolidado")
    void productoConDescuento() {
        JsonPath json = given().spec(spec).pathParam("id", 1)
                .when().get(ENDPOINT)
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract().jsonPath();

        assertAll("detalle del producto 1",
                () -> assertEquals(1, json.getInt("id")),
                () -> assertEquals("Laptop Pro 14", json.getString("name")),
                () -> assertEquals(25000.0, json.getDouble("basePrice"), DELTA),
                () -> assertEquals(10.0, json.getDouble("discountPercentage"), DELTA),
                () -> assertEquals(22500.0, json.getDouble("finalPrice"), DELTA),
                () -> assertEquals(15, json.getInt("stock")));
    }

    @Test
    @DisplayName("Producto sin descuento: descuento 0 y precio final igual al base")
    void productoSinDescuento() {
        JsonPath json = given().spec(spec).pathParam("id", 3)
                .when().get(ENDPOINT)
                .then()
                    .statusCode(200)
                    .extract().jsonPath();

        assertAll("detalle del producto 3",
                () -> assertEquals("Teclado Mecanico", json.getString("name")),
                () -> assertEquals(0.0, json.getDouble("discountPercentage"), DELTA),
                () -> assertEquals(json.getDouble("basePrice"), json.getDouble("finalPrice"), DELTA));
    }

    @Test
    @DisplayName("Producto inexistente: 404")
    void productoInexistente() {
        given().spec(spec).pathParam("id", 999999)
                .when().get(ENDPOINT)
                .then()
                    .statusCode(404);
    }

    @Test
    @DisplayName("Contrato: la respuesta incluye todos los campos")
    void contratoDeRespuesta() {
        given().spec(spec).pathParam("id", 1)
                .when().get(ENDPOINT)
                .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", not(emptyOrNullString()))
                    .body("basePrice", notNullValue())
                    .body("discountPercentage", notNullValue())
                    .body("finalPrice", notNullValue())
                    .body("stock", notNullValue());
    }

    @ParameterizedTest(name = "Regla de negocio: precio final = base - descuento, producto {0}")
    @ValueSource(longs = {1, 2, 3})
    void precioFinalConsistente(long id) {
        JsonPath json = given().spec(spec).pathParam("id", id)
                .when().get(ENDPOINT)
                .then()
                    .statusCode(200)
                    .extract().jsonPath();

        double base = json.getDouble("basePrice");
        double descuento = json.getDouble("discountPercentage");
        double esperado = base - (base * descuento / 100.0);

        assertEquals(esperado, json.getDouble("finalPrice"), DELTA,
                "finalPrice debe ser basePrice menos el porcentaje de descuento");
    }

    @Test
    @DisplayName("Seguridad: HTTP redirige (301) a HTTPS")
    void redireccionaAHttps() {
        assumeTrue(TestConfig.HTTP_URL.startsWith("http://"),
            "Solo aplica cuando existe una URL http:// para probar");

        given().spec(spec).baseUri(TestConfig.HTTP_URL)
            .redirects().follow(false).pathParam("id", 1)
            .when().get(ENDPOINT)
            .then()
                .statusCode(301)
                .header("Location", startsWith("https://"));
    }

    @Test
    @DisplayName("Rendimiento: responde en menos de 3 segundos")
    void tiempoDeRespuesta() {
        given().spec(spec).pathParam("id", 1)
                .when().get(ENDPOINT)
                .then()
                    .statusCode(200)
                    .time(lessThan(3000L));
    }
}