## Modelo de trabajo

Se implementa GitFlow como modelo de control de versiones.

---

##  Ramas principales

* **main**: contiene la versión estable del sistema
* **develop**: integra todas las funcionalidades en desarrollo

---

##  Ramas de apoyo

* **feature/***: para el desarrollo de nuevas funcionalidades
* **hotfix/***: para la corrección de errores críticos en producción

---

##  Convención de nombres de ramas

 `feature/login-jwt`

 `hotfix/validacion-jwt`

---

##  Convención de commits

Se utiliza una convención basada en prefijos:

* **feat**: nueva funcionalidad
* **hotfix**: corrección crítica

### Feat y Hotfix realizados

* `Feat: implementacion de HATEOAS en endpoints de usuario y unificacion de controladores`
* `Feat: Mejoras en validacion de JWT en datos de autenticacion`
* `Hotfix: Arreglo de no reconocimiento de email en jwt`

---

##  Flujo de trabajo (GitFlow)

* Se crea una rama `feature/*` desde `develop`
* Se desarrollan los cambios y se realizan commits pequeños
* Se crea un Pull Request hacia `develop`

###  Hotfix

* Se crea una rama `hotfix/*` desde `main`
* Se corrige el error
* Se hace merge hacia:

  * `main`
  * `develop`

---

##  Flujo de merge

* Todos los cambios se integran mediante Pull Requests
* No se permite hacer push directo a `main`
* Se realiza revisión antes de aprobar el merge

---

##  Estrategia de revisión de código

* Al menos un integrante del equipo revisa el código
* Se validan:

  * Funcionamiento del código
  * Buenas prácticas
* Se aprueba el Pull Request antes de hacer merge

---

##  Integración Continua (GitHub Actions)

Se implementa una acción continua utilizando GitHub Actions.

###  Ubicación

El archivo de configuración se encuentra en:

```
.github/workflows/ci.yml

```

###  Ejecución automática

La acción se ejecuta en los siguientes eventos:

* Push a la rama `develop`
* Pull Request hacia la rama `main`

###  Proceso que realiza

1. Crea una máquina virtual con Windows (`windows-latest`)
2. Descarga el código del repositorio
3. Configura Java 17 (distribución Temurin)
4. Compila el microservicio `api_cursos` mediante Maven
5. Compila el microservicio `servicios_usuarios` mediante Maven

###  Objetivo

Automatizar la validación del proyecto, asegurando que los microservicios compilen correctamente antes de integrar cambios a la rama principal.


