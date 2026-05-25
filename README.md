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
- MySQL (configurado en los `application.properties`)
Archivos importantes:

- [docker-compose.yml](docker-compose.yml)
- [api_cursos/dockerfile](api_cursos/dockerfile)
- [api_cursos/src/main/resources/application.properties](api_cursos/src/main/resources/application.properties)
- [servicio_usuarios/dockerfile](servicio_usuarios/dockerfile)
- [servicio_usuarios/src/main/resources/application.properties](servicio_usuarios/src/main/resources/application.properties)
Puertos y notas rápidas:

- `docker-compose.yml` mapea:
	- `api_cursos`: `8080:8080`
	- `servicio_usuarios`: `8081:8080`
- En `api_cursos/src/main/resources/application.properties` la aplicación está configurada con `server.port=8081`, así que hay que alinear esa configuración con lo que expone el Dockerfile y lo que mapea `docker-compose`.
Cómo levantar todo (rápido):

```bash
docker-compose up --build
```

Compilar JARs localmente:

```bash

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

Generar imagen Docker de ejemplo:

```bash
docker build -t api_cursos:local -f api_cursos/dockerfile ./api_cursos
docker run -p 8080:8080 api_cursos:local
```

Seguridad y secretos:

- En `servicio_usuarios/src/main/resources/application.properties` hay un `jwt.secret` con valor de prueba. Mover secretos a variables de entorno o un gestor de secretos antes de producción.

Calidad de código (SonarCloud):

- El pipeline CI ejecuta análisis SonarCloud para `api_cursos`.
- Detalles principales: `sonar.host.url=https://sonarcloud.io`, `sonar.projectKey=Heartsmith1_RepoDevops`, `sonar.organization=heartsmith1`. El token se obtiene de `secrets.SONAR_TOKEN` en GitHub Actions.

Recomendaciones rápidas:

- Alinear puertos entre `application.properties`, `Dockerfile` y `docker-compose.yml`.
- No dejar secretos en ficheros de repositorio.
- Considerar añadir SonarCloud también para `servicio_usuarios` si se desea análisis centralizado.
Contribuir

- Usamos GitFlow: ramas `feature/*`, `hotfix/*`, `develop` y `main`.

Licencia

Consulta el archivo `LICENSE` en la raíz del repositorio.
#+ RepoDevops — Microservicios (actualización automática)

Este repositorio contiene dos microservicios Java (Spring Boot) y una orquestación con `docker-compose`.

**Resumen rápido:**
- **Servicios:** `api_cursos`, `servicio_usuarios`
- **Lenguaje / Plataforma:** Java 21, Spring Boot
- **Build:** Maven (incluye `mvnw` / `mvnw.cmd`)
- **Contenedores:** Docker usando `docker-compose.yml`

**Estructura principal:**

- [api_cursos](api_cursos/): microservicio Spring Boot (Dockerfile, `mvnw`)
- [servicio_usuarios](servicio_usuarios/): microservicio Spring Boot (Dockerfile, `mvnw`)
- [docker-compose.yml](docker-compose.yml): orquestación de ambos servicios

**Archivos relevantes detectados:**

- [docker-compose.yml](docker-compose.yml)
- [api_cursos/dockerfile](api_cursos/dockerfile)
- [api_cursos/src/main/resources/application.properties](api_cursos/src/main/resources/application.properties)
- [servicio_usuarios/dockerfile](servicio_usuarios/dockerfile)
- [servicio_usuarios/src/main/resources/application.properties](servicio_usuarios/src/main/resources/application.properties)

## Tecnologías usadas

- Java 21 (Eclipse Temurin)
- Spring Boot
- Maven (con wrapper `mvnw` / `mvnw.cmd`)
- Docker
- Docker Compose
- MySQL (configurado como datasource en los `application.properties`)

## Servicios y puertos (estado actual)

Según `docker-compose.yml`:

- `api_cursos`: mapeo `8080:8080` (host:container)
- `servicio_usuarios`: mapeo `8081:8080` (host:container)

Según los `application.properties`:

- `api_cursos` está configurado con `server.port=8081` (ver [api_cursos/src/main/resources/application.properties](api_cursos/src/main/resources/application.properties)).
- `servicio_usuarios` no define `server.port`, por defecto usa `8080`.

Nota: existe una discrepancia para `api_cursos`: el contenedor y Dockerfile expone `8080`, `docker-compose` mapea `8080:8080`, pero la aplicación está configurada para ejecutarse en `8081`. Se recomienda alinear `server.port`, `Dockerfile` y `docker-compose.yml` para evitar conflictos.

## Ejecutar el proyecto (rápido)

Desde la raíz del repositorio, con Docker y Docker Compose instalados:

```bash
docker-compose up --build
```

Esto construirá las imágenes a partir de los `Dockerfile` y arrancará ambos servicios.

## Ejecutar / construir individualmente

Compilar el JAR con Maven (Linux/macOS):

```bash
cd api_cursos
./mvnw clean package -DskipTests

cd ../servicio_usuarios
./mvnw clean package -DskipTests
```

En Windows, usar `mvnw.cmd`:

```powershell
cd api_cursos
.\\mvnw.cmd clean package -DskipTests

cd ..\\servicio_usuarios
.\\mvnw.cmd clean package -DskipTests
```

Construir la imagen Docker manualmente (ejemplo):

```bash
docker build -t api_cursos:local -f api_cursos/dockerfile ./api_cursos
docker run -p 8080:8080 api_cursos:local
```

## Variables y secretos

- En `servicio_usuarios/src/main/resources/application.properties` se encuentra la propiedad `jwt.secret` con un valor de prueba. Mover secretos a variables de entorno o a un gestor de secretos antes de producción.

## Calidad de código (SonarQube / SonarCloud)

El pipeline de CI ejecuta un análisis de calidad con SonarCloud para `api_cursos`. Los detalles extraídos del workflow son:

- **Proveedor:** SonarCloud (host: `https://sonarcloud.io`)
- **Proyecto:** `Heartsmith1_RepoDevops` (propiedad `sonar.projectKey` en el workflow)
- **Organización:** `heartsmith1` (propiedad `sonar.organization` en el workflow)
- **Autenticación:** usa la variable de entorno/secreto de GitHub Actions `SONAR_TOKEN` (almacenado en `secrets.SONAR_TOKEN`).

El paso en `.github/workflows/ci.yml` ejecuta:

```bash
cd api_cursos
mvn clean verify sonar:sonar \
	-Dsonar.projectKey=Heartsmith1_RepoDevops \
	-Dsonar.organization=heartsmith1 \
	-Dsonar.host.url=https://sonarcloud.io \
	-Dsonar.token=${SONAR_TOKEN}
```

Recomendaciones:

- Asegurar que `SONAR_TOKEN` está configurado en los secretos del repositorio de GitHub.
- Considerar habilitar análisis para `servicio_usuarios` si se desea cobertura de ambos microservicios.
- Configurar reglas de calidad y Quality Gate en SonarCloud para automatizar bloqueos en merges si la calidad baja.

## Prerrequisitos

- Docker
- Docker Compose
- JDK 21 (si se compila localmente sin Docker)

## Notas y recomendaciones

- Revisar la configuración de puertos de `api_cursos` para garantizar que el puerto interno de la aplicación coincide con lo expuesto en el `Dockerfile` y lo mapeado en `docker-compose.yml`.
- No dejar secretos (p. ej. `jwt.secret`) en ficheros de configuración sin protección.

## Contribuir

- Usamos GitFlow como modelo de trabajo: ramas `feature/*`, `hotfix/*`, `develop` y `main`.

## Licencia

Consulta el fichero `LICENSE` en la raíz del repositorio.


