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

Hemos configurado un pipeline de integración continua en GitHub Actions (ver `.github/workflows/ci.yml`). El pipeline ejecuta, por cada push o PR, las etapas principales para validar y publicar artefactos:

- Checkout del código
- Build con Maven (`./mvnw clean package`)
- Ejecución de tests unitarios e integrados
- Análisis estático y envío a SonarCloud (`mvn sonar:sonar`)
- Packaging/publicación de artefactos (JARs, imágenes Docker) cuando procede
- Etapas de despliegue opcionales para staging/producción, protegidas por approvals o ejecutadas desde `main`/releases

Comandos para reproducir localmente las etapas principales:

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
- Integramos SonarCloud para análisis estático y Quality Gates que impiden merges si se incumplen umbrales críticos.
- Recolectamos cobertura de pruebas (por ejemplo con JaCoCo) y, cuando procede, configuramos umbrales mínimos.
- Aplicamos políticas de protección de ramas y revisiones de código obligatorias.
- Recomendamos integrar SCA (Dependabot, OWASP Dependency-Check u otras) para escaneo de dependencias.

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

## Ejecutar / construir individualmente

Linux / macOS:

```bash
cd api_cursos
./mvnw clean package -DskipTests

cd ../servicio_usuarios
./mvnw clean package -DskipTests
```

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

## Variables y secretos

- No dejar secretos en ficheros: mover `jwt.secret` y otros valores sensibles a variables de entorno o gestores de secretos.
- Configurar `SONAR_TOKEN` en los secretos del repositorio para análisis SonarCloud.

## Contribuir

Usamos GitFlow como modelo de trabajo: ramas `feature/*`, `hotfix/*`, `develop` y `main`.

## Licencia

Consulta el archivo `LICENSE` en la raíz del repositorio.
