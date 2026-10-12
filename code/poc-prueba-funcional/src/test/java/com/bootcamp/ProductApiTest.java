package com.bootcamp;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductApiTest {

    @Test
    public void testGetProductById() {
        // Ignorar la validación de certificado SSL para la IP directa
        RestAssured.useRelaxedHTTPSValidation();

        given()
            .baseUri("https://54.161.157.90")
            .header("Accept", "application/json")
        .when()
            .get("/api/v1/products/1")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", equalTo(1))
            .body("name", equalTo("Laptop Pro 14"));
    }
}