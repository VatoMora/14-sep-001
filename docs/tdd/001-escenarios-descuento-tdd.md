# Escenarios de Prueba TDD y Pseudocódigo — Servicio de Descuentos

**Autor:** Arturo Mora  
**Fecha:** 24/09/2026  
**Módulo:** Módulo 3 — Sesión 5 (Ejercicio 10 - Complemento Ejercicio 09)  
**Proyecto:** Microservicio de Descuentos (`descuento-service`)  

---

## 1. Descripción de la Tarea
El objetivo de esta actividad es definir los escenarios de prueba unitaria bajo el enfoque **TDD (Test-Driven Development)** para el cálculo y aplicación de descuentos a productos, incluyendo casos de éxito (*happy path*), casos de borde (*edge cases*) y manejo de excepciones, acompañados de la lógica en pseudocódigo.

---

## 2. Especificación de Escenarios de Prueba TDD (Gherkin Format)

### Escenario 1: Cálculo exitoso de descuento estándar (Caso de Éxito)
* **GIVEN (Dado):** Un producto con precio base de `$1000.00` MXN y un porcentaje de descuento válido del `15%`.
* **WHEN (Cuando):** Se invoca la función `calcularMontoConDescuento(1000.00, 15.0)`.
* **THEN (Entonces):** El monto final retornado debe ser `$850.00` MXN y el ahorro calculado debe ser `$150.00` MXN.

### Escenario 2: Producto sin descuento aplicable (Caso de Borde - 0%)
* **GIVEN (Dado):** Un producto con precio base de `$500.00` MXN y un porcentaje de descuento del `0%`.
* **WHEN (Cuando):** Se invoca la función `calcularMontoConDescuento(500.00, 0.0)`.
* **THEN (Entonces):** El monto final retornado debe ser idéntico al precio original (`$500.00` MXN) sin sufrir modificaciones.

### Escenario 3: Producto con descuento total / Promoción 100% (Caso de Borde - 100%)
* **GIVEN (Dado):** Un producto con precio base de `$250.00` MXN y un porcentaje de descuento del `100%`.
* **WHEN (Cuando):** Se invoca la función `calcularMontoConDescuento(250.00, 100.0)`.
* **THEN (Entonces):** El monto final retornado debe ser `$0.00` MXN.

### Escenario 4: Porcentaje de descuento inválido / negativo (Caso de Error)
* **GIVEN (Dado):** Un producto con precio base de `$200.00` MXN y un porcentaje de descuento negativo de `-10%`.
* **WHEN (Cuando):** Se invoca la función `calcularMontoConDescuento(200.00, -10.0)`.
* **THEN (Entonces):** El sistema debe lanzar una excepción `PorcentajeDescuentoInvalidoException` con el mensaje `"El porcentaje de descuento no puede ser negativo ni mayor a 100"`.

### Escenario 5: Precio base del producto inválido / cero o negativo (Caso de Error)
* **GIVEN (Dado):** Un precio de entrada de `-$50.00` MXN y un porcentaje de descuento del `20%`.
* **WHEN (Cuando):** Se invoca la función `calcularMontoConDescuento(-50.00, 20.0)`.
* **THEN (Entonces):** El sistema debe lanzar una excepción `PrecioInvalidoException` con el mensaje `"El precio base del producto debe ser mayor a cero"`.

### Escenario 6: Descuento excede el límite máximo del 100% (Caso de Error)
* **GIVEN (Dado):** Un precio de `$150.00` MXN y un porcentaje de descuento del `120%`.
* **WHEN (Cuando):** Se invoca la función `calcularMontoConDescuento(150.00, 120.0)`.
* **THEN (Entonces):** El sistema debe lanzar una excepción `PorcentajeDescuentoInvalidoException`.

---

## 3. Ciclo de Desarrollo TDD (Red -> Green -> Refactor)

### Fase 1: RED (Escribir la prueba que falla primero)
```
CLASE_PRUEBA TestCalculoDescuento:

    FUNCION test_calcular_descuento_exitoso():
        // Arrange
        DEFINIR precioBase = 1000.00
        DEFINIR porcentaje = 15.0
        DEFINIR esperado = 850.00
        
        // Act
        DEFINIR resultado = DescuentoService.calcularMontoConDescuento(precioBase, porcentaje)
        
        // Assert
        AFIRMAR_IGUAL(esperado, resultado, "El descuento del 15% sobre 1000 debe dar 850")
    FIN_FUNCION

    FUNCION test_descuento_negativo_lanza_excepcion():
        // Act & Assert
        AFIRMAR_LANZA_EXCEPCION(PorcentajeDescuentoInvalidoException):
            DescuentoService.calcularMontoConDescuento(1000.00, -5.0)
    FIN_FUNCION
FIN_CLASE
```

---

### Fase 2: GREEN & REFACTOR (Pseudocódigo de la solución)

```
ALGORITMO ServicioDescuento
    
    ESTRUTURA ResultadoDescuento:
        precioOriginal: REAL
        porcentajeAplicado: REAL
        montoDescuento: REAL
        precioFinal: REAL
    FIN_ESTRUCTURA

    FUNCION calcularMontoConDescuento(precioBase: REAL, porcentajeDescuento: REAL) -> REAL:
        
        // 1. Validaciones de entrada (Guard Clauses)
        SI precioBase <= 0 ENTONCES
            LANZAR ERROR PrecioInvalidoException("El precio base debe ser mayor a cero")
        FIN_SI

        SI porcentajeDescuento < 0 O porcentajeDescuento > 100 ENTONCES
            LANZAR ERROR PorcentajeDescuentoInvalidoException("El porcentaje debe estar entre 0 y 100")
        FIN_SI

        // 2. Cálculo del descuento
        DEFINIR montoAhorro: REAL
        DEFINIR precioConDescuento: REAL

        montoAhorro = precioBase * (porcentajeDescuento / 100.0)
        precioConDescuento = precioBase - montoAhorro

        // 3. Redondeo a 2 decimales para precisión monetaria
        precioConDescuento = REDONDEAR(precioConDescuento, 2)

        RETORNAR precioConDescuento
        
    FIN_FUNCION

FIN_ALGORITMO
```

---

## 4. Instrucciones para envío por correo
Para cumplir con la entrega de la **Tarea 3 / Ejercicio 10**:
1. Copiar el texto de la Sección 2 (Escenarios de Prueba Gherkin) y Sección 3 (Pseudocódigo TDD).
2. Enviar por correo al instructor/evaluador del bootcamp con el asunto: `[Bootcamp AT&T] Entrega Tarea 3 - Escenarios TDD Descuento - Arturo Mora`.
