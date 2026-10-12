# Monitoreo de Métricas PromQL & Observabilidad — Productos Service

**Autor:** Arturo Mora  
**Fecha:** 02/10/2026  
**Módulo:** Módulo 5 — Sesión 7 y 8 (Ejercicios 15, 16 y 17)  
**Proyecto:** Microservicio de Productos (`productos-service`)  
**Endpoint de Métricas:** `/actuator/prometheus` (Puerto 8091)  

---

## 1. Configuración de Actuator y Micrometer en Spring Boot

El microservicio `productos-service` expone las métricas para Prometheus a través de Spring Boot Actuator y la librería Micrometer. En el archivo `application.properties` se definió la siguiente configuración:

```properties
# Puerto de gestión dedicado
management.server.port=8091

# Exposición de endpoints de Prometheus y Health
management.endpoints.web.exposure.include=prometheus,health,metrics

# Etiqueta global con el nombre de la aplicación
management.metrics.tags.application=productos-service

# Histogramas para cálculo de percentiles de latencia (P95, P99)
management.metrics.distribution.percentiles-histogram.http.server.requests=true
```

---

## 2. Consultas PromQL Documentadas

A continuación se presentan las consultas **PromQL** diseñadas para monitorear el rendimiento, tráfico y salud del microservicio en **Prometheus** y **Grafana**:

### Metric 1: Tráfico HTTP y Rate de Peticiones (Throughput)
* **Objetivo:** Calcular la tasa de peticiones entrantes por segundo en ventanas de 5 minutos, filtradas por la aplicación `productos-service`.
* **Consulta PromQL:**
  ```promql
  sum(rate(http_server_requests_seconds_count{application="productos-service"}[5m])) by (uri, status, method)
  ```
* **Explicación:** Muestra el número de peticiones por segundo agrupadas por URI, método HTTP (GET/POST) y código de respuesta HTTP (200, 404, 500). Permite identificar picos de tráfico y tasa de errores.

---

### Metric 2: Uso de CPU del Proceso JVM y del Sistema
* **Objetivo:** Monitorear el porcentaje de utilización de CPU del proceso de Java vs el sistema operativo host.
* **Consulta PromQL (CPU Proceso JVM):**
  ```promql
  process_cpu_usage{application="productos-service"} * 100
  ```
* **Consulta PromQL (CPU Sistema):**
  ```promql
  system_cpu_usage{application="productos-service"} * 100
  ```
* **Explicación:** Retorna el uso de CPU en porcentaje (0% - 100%). Un valor continuo superior al 85% indica posible saturación o necesidad de escalamiento horizontal (*HPA* en Kubernetes).

---

### Metric 3: Consumo de Memoria Heap JVM (Usada vs Máxima)
* **Objetivo:** Calcular el espacio consumido en la memoria Heap de Java en Megabytes (MB).
* **Consulta PromQL (Memoria Heap Usada en MB):**
  ```promql
  jvm_memory_used_bytes{application="productos-service", area="heap"} / (1024 * 1024)
  ```
* **Consulta PromQL (Porcentaje de Uso del Heap):**
  ```promql
  (jvm_memory_used_bytes{application="productos-service", area="heap"} / jvm_memory_max_bytes{application="productos-service", area="heap"}) * 100
  ```
* **Explicación:** Ayuda a prevenir errores de `OutOfMemoryError` y monitorear la eficiencia de la recolección de basura (*Garbage Collection*).

---

### Metric 4 (Adicional): Latencia P95 y P99 (Percentiles)
* **Objetivo:** Medir el tiempo de respuesta del 95% y 99% de las solicitudes HTTP recibidas por el servicio.
* **Consulta PromQL (Percentil 95 - P95 en segundos):**
  ```promql
  histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket{application="productos-service"}[5m])) by (le))
  ```
* **Explicación:** Permite verificar los Acuerdos de Nivel de Servicio (*SLA* / *SLO*). Si el P95 excede 500ms, se genera un evento de alerta en Alertmanager.

---

## 3. Evidencia de Salida de Prometheus (`/actuator/prometheus`)

A continuación se muestra una muestra representativa de la salida textual obtenida al invocar el endpoint `http://localhost:8091/actuator/prometheus`:

```text
# HELP process_cpu_usage The "recent cpu usage" for the Java Virtual Machine process
# TYPE process_cpu_usage gauge
process_cpu_usage{application="productos-service"} 0.0235

# HELP jvm_memory_used_bytes The amount of used memory
# TYPE jvm_memory_used_bytes gauge
jvm_memory_used_bytes{application="productos-service",area="heap",id="G1 Eden Space"} 4.8234496E7
jvm_memory_used_bytes{application="productos-service",area="heap",id="G1 Survivor Space"} 4194304.0
jvm_memory_used_bytes{application="productos-service",area="heap",id="G1 Old Gen"} 2.1543824E7

# HELP http_server_requests_seconds
# TYPE http_server_requests_seconds summary
http_server_requests_seconds_count{application="productos-service",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/api/v1/products/{id}"} 142.0
http_server_requests_seconds_sum{application="productos-service",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/api/v1/products/{id}"} 1.842391
```

---

## 4. Instrucciones para la Entrega por Correo
Para realizar el envío de la **Tarea 5**:
1. Copiar las consultas PromQL de la Sección 2 y la muestra de `/actuator/prometheus` de la Sección 3.
2. Adjuntar o pegar en el cuerpo del correo con el asunto: `[Bootcamp AT&T] Entrega Tarea 5 - Métricas PromQL & Observabilidad - Arturo Mora`.
