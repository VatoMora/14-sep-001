
# Proyecto Monorepo: Monolito y Microservicios

Este repositorio contiene la estructura unificada para la gestión del sistema heredado y los microservicios extraídos en el proceso de migración.

## Estructura del Repositorio

- `monolith/`: Código fuente del sistema monolítico heredado.
- `services/`: Microservicios extraídos (auth-service, user-service, order-service).
- `gateway/`: Configuraciones declarativas de Kong API Gateway.
- `docs/`: Documentación técnica y arquitectura (ADRs, modelos de amenazas).
- `pom.xml`: POM maestro para la gestión de dependencias.

## Requisitos Previos
- Java JDK 21
- Apache Maven 3.9+
- Docker / Podman y Docker Compose

## Instrucciones de Inicio Rápido
1. Clonar el repositorio localmente.
2. Levantar la infraestructura local (Kong, PostgreSQL, Keycloak) con `docker-compose up -d`.
3. Compilar el proyecto completo ejecutando `mvn clean install`.

