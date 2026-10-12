package com.bootcamp.pruebas.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

/**
 * Filtro de REST Assured: registra en el reporte la petición y la respuesta
 * de cada llamada HTTP, sin tener que escribir logs a mano en cada prueba.
 */
public class ExtentLogFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        ExtentTest test = ExtentManager.current();
        if (test != null) {
            test.info(MarkupHelper.createCodeBlock(
                    requestSpec.getMethod() + " " + requestSpec.getURI()));
            test.info("Respuesta: " + response.getStatusLine()
                    + " | " + response.getTime() + " ms");

            String cuerpo = response.getBody().asPrettyString();
            if (cuerpo != null && !cuerpo.isBlank()) {
                test.info(MarkupHelper.createCodeBlock(cuerpo, CodeLanguage.JSON));
            }
        }
        return response;
    }
}