# RepoDevops — Microservicios

Este repositorio agrupa dos microservicios Java y la configuración para ejecutarlos con Docker Compose.

Servicios incluidos:

- `api_cursos` — microservicio Spring Boot
- `servicio_usuarios` — microservicio Spring Boot

Principales tecnologías:

- Java 21 (Eclipse Temurin)
- Spring Boot
- Maven (incluye `mvnw` / `mvnw.cmd`)
- Docker y Docker Compose
- MySQL
- SonarCloud (análisis de calidad)

Estructura principal (resumen):

- [api_cursos](api_cursos/)
- [servicio_usuarios](servicio_usuarios/)
- [docker-compose.yml](docker-compose.yml)

## Servicios y puertos (estado conocido)

- `api_cursos`: mapeo en `docker-compose.yml` `8080:8080` (host:container)
- `servicio_usuarios`: mapeo en `docker-compose.yml` `8081:8080` (host:container)

Nota: en `api_cursos/src/main/resources/application.properties` la aplicación está configurada con `server.port=8081`. Recomendamos alinear `server.port`, `Dockerfile` y `docker-compose.yml` para evitar conflictos.

## Pipeline CI/CD

El repositorio contiene estos workflows en `.github/workflows/`:

- `ci.yml`: compila los microservicios, ejecuta tests, construye imágenes Docker y realiza análisis SonarCloud de `api_cursos`.
- `compliance.yml`: ejecuta análisis SonarCloud para ambos microservicios y revisa la política de protección de ramas en GitHub.
- `deploy-observabilty.yml`: despliega el stack de observabilidad en AWS EC2 usando Docker Compose.

El workflow principal de CI realiza:

- checkout del código.
- configuración del JDK.
- compilación y tests de `api_cursos` y `servicio_usuarios`.
- construcción de imágenes Docker de ambos servicios.
- análisis SonarCloud en el proyecto `api_cursos`.

## Observabilidad y monitoreo

```bash
cd api_cursos
./mvnw clean verify

cd ../servicio_usuarios
./mvnw clean verify
```

Ejecutar el análisis Sonar (requiere `SONAR_TOKEN` en el entorno):

```bash
cd api_cursos
./mvnw clean verify sonar:sonar \
  -Dsonar.projectKey=Heartsmith1_RepoDevops \
  -Dsonar.organization=heartsmith1 \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=${SONAR_TOKEN}
```

## Trazabilidad

Hemos garantizado la trazabilidad mediante:

- Control de versiones con Git (seguimos GitFlow: `feature/*`, `hotfix/*`, `develop`, `main`).
- Integración de cambios mediante PRs que contienen discusión y revisiones; el pipeline CI corre sobre PRs antes del merge.
- Inclusión de identificadores de issues/tickets en commits y PRs para enlazar cambios con requisitos.
- Etiquetado semántico de releases (`git tag`), publicando artefactos con la misma etiqueta.
- Conservación de artefactos y metadatos (commit SHA, número de build, timestamp) para reconstrucción y auditoría.

## Calidad y cómo la garantizamos

Aplicamos estas medidas automatizadas y de proceso para mantener calidad:

- Ejecutamos pruebas automatizadas en el pipeline; fallos bloquean la integración.
- Integramos SonarCloud para análisis estático, seguridad y Quality Gates que impiden merges si se incumplen umbrales críticos.
- Recolectamos cobertura de pruebas (por ejemplo con JaCoCo) y, cuando procede, configuramos umbrales mínimos.
- Aplicamos políticas de protección de ramas y revisiones de código obligatorias.
- Recomendamos integrar SCA (Dependabot, OWASP Dependency-Check u otras) para escaneo de dependencias.

### Bloqueo automático del pipeline ante fallos críticos

Los workflows de CI y compliance ejecutan SonarCloud con `-Dsonar.qualitygate.wait=true`.
Si SonarCloud devuelve un Quality Gate en fallo por vulnerabilidades, seguridad o problemas de calidad críticos, el paso termina con error y GitHub Actions detiene el workflow antes de seguir con etapas posteriores o de permitir el merge.

## Observabilidad y monitoreo

El repositorio incluye una configuración de monitorización basada en Prometheus, Grafana y CloudWatch:

- `observability/docker-compose.yml`: despliega Prometheus, Grafana, cAdvisor y un agente de CloudWatch.
- `observability/prometheus.yml`: configura el scraping de métricas de `servicio-usuarios` y `cadvisor`.
- `observability/grafana/provisioning/datasources/prometheus.yml`: define Prometheus como fuente de datos en Grafana.
- `observability/grafana/provisioning/dashboards/repodevops-dashboards.json`: dashboard con métricas clave de disponibilidad, tasa de peticiones, errores 5xx, latencia, CPU y memoria.

Este monitoreo permite visualizar logs, métricas de uso, errores y disponibilidad de los microservicios y del entorno.

## Despliegue de observabilidad en la nube

Se incluye un workflow para desplegar el stack de observabilidad en AWS (`.github/workflows/deploy-observabilty.yml`).

El workflow crea o reutiliza una instancia EC2, instala Docker y Docker Compose, clona el repositorio y arranca la pila desde `observability/docker-compose.yml`.

Las URLs resultantes son:

- Prometheus: `http://<IP>:9090`
- Grafana: `http://<IP>:3000`

## Dashboard de métricas clave

El dashboard incluido en Grafana recoge métricas relevantes para la operación y el desarrollo.

Paneles principales:

- Disponibilidad de los servicios.
- Peticiones por segundo.
- Errores 5xx por segundo.
- Latencia promedio.
- Uso de CPU por contenedor.
- Uso de memoria por contenedor.

Consultas principales usadas en Prometheus/Grafana:

- `rate(container_cpu_usage_seconds_total[5m]) * 100` para el uso de CPU.
- `container_memory_usage_bytes` para el uso de memoria.
- `http_server_requests_seconds_count` para la tasa de peticiones y el volumen de tráfico.

Estas consultas están implementadas en el dashboard provisionado en `observability/grafana/provisioning/dashboards/repodevops-dashboards.json`.

## Cumplimiento y políticas técnicas

El pipeline documentado en este repositorio incluye cumplimiento técnico mediante SonarCloud y branch protection.

- SonarCloud valida la calidad del código y detecta problemas de seguridad y deuda técnica.
- Las políticas de protección de ramas aseguran que los PRs pasen los checks y las revisiones antes de fusionarse.
- Este enfoque permite decidir cuándo bloquear un merge, cuándo priorizar correcciones y cuándo requerir cambios adicionales.

### Integración con el pipeline CI/CD

La integración de estas herramientas en el pipeline significa que las decisiones técnicas se basan en:

- Resultados de análisis estático que identifican defectos y vulnerabilidades.
- Validaciones automáticas que confirman si el código cumple con las reglas de calidad.
- Datos de monitoreo que permiten correlacionar despliegues con métricas de disponibilidad y errores.

### Recomendaciones

- Mantener el token `SONAR_TOKEN` en los secretos de GitHub Actions.
- Configurar branch protection para `main` y `develop` en el repositorio de GitHub.
- Validar que el workflow de despliegue de observabilidad se ejecute con las credenciales AWS correctas.

### SonarCloud (datos relevantes)

- Host: `https://sonarcloud.io`
- Project key: `Heartsmith1_RepoDevops` (ver workflow para parámetros exactos)
- Organización: `heartsmith1`
- Token: usar `SONAR_TOKEN` en secretos de GitHub Actions

## Ejecutar el proyecto (rápido)

Desde la raíz del repositorio, con Docker y Docker Compose instalados:

```bash
docker-compose up --build
```

## Ejecutar  / construir individualmente

Windows (PowerShell):

```powershell
cd api_cursos
.\mvnw.cmd clean package -DskipTests

cd ..\servicio_usuarios
.\mvnw.cmd clean package -DskipTests
```

Construir imagen Docker de ejemplo:

```bash
docker build -t api_cursos:local -f api_cursos/dockerfile ./api_cursos
docker run -p 8080:8080 api_cursos:local
```

## Contribuir

Usamos GitFlow como modelo de trabajo: ramas `feature/*`, `hotfix/*`, `develop` y `main`.
