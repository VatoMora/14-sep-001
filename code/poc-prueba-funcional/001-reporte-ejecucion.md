# Reporte de Ejecución — Pruebas Automatizadas REST Assured

**Autor:** Arturo Mora  
**Fecha:** 01/10/2026  
**Módulo:** Módulo 4 — Sesión 6 (Ejercicio 14 - Complemento Ejercicio 13)  
**Proyecto:** `poc-prueba-funcional`  

---

## 1. Modificación de la Clase `ProductApiTest.java`
Se actualizó la prueba funcional en Java con **REST Assured** incorporando la aserción para validar la propiedad `"name"` con el valor esperado `"Laptop Pro 14"` en la respuesta JSON del endpoint `/api/v1/products/1`:

```java
package com.bootcamp;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductApiTest {

    @Test
    public void testGetProductById() {
        // Ignorar la validación de certificado SSL para la IP directa del entorno de pruebas
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
```

---

## 2. Reporte de Ejecución (`mvn clean test`)

```text
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------< com.bootcamp:poc-prueba-funcional >------------------
[INFO] Building poc-prueba-funcional 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ poc-prueba-funcional ---
[INFO] Deleting C:\Github\Curso\Bootcamp\code\poc-prueba-funcional\target
[INFO]
[INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ poc-prueba-funcional ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO]
[INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ poc-prueba-funcional ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-resources-plugin:3.0.2:testResources (default-testResources) @ poc-prueba-funcional ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO]
[INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ poc-prueba-funcional ---
[INFO] Compiling 1 source file to C:\Github\Curso\Bootcamp\code\poc-prueba-funcional\target\test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ poc-prueba-funcional ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.bootcamp.ProductApiTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.842 s - in com.bootcamp.ProductApiTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.521 s
[INFO] Finished at: 2026-10-01T18:14:02-06:00
[INFO] ------------------------------------------------------------------------
```

---

## 3. Conclusión
La aserción `.body("name", equalTo("Laptop Pro 14"))` valida satisfactoriamente la integridad del contrato de la API REST para el producto ID 1.
