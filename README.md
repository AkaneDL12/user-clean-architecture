# User Clean Architecture

Proyecto de ejemplo en **Spring Boot** que implementa una **arquitectura hexagonal (Ports & Adapters / Clean Architecture)** para la gestión de usuarios. El objetivo es demostrar la separación de responsabilidades entre el dominio, la aplicación (casos de uso) y la infraestructura (adaptadores de entrada/salida).

## Tabla de contenidos

- [Stack tecnológico](#stack-tecnológico)
- [Arquitectura](#arquitectura)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Requisitos previos](#requisitos-previos)
- [Puesta en marcha](#puesta-en-marcha)
- [Consola H2](#consola-h2)
- [API](#api)
- [Tests](#tests)

## Stack tecnológico

| Tecnología | Versión / Detalle |
|---|---|
| Java | 21 (toolchain Gradle) |
| Spring Boot | 4.1.0 |
| Spring Data JPA | incluido vía `spring-boot-starter-data-jpa` |
| Spring MVC | `spring-boot-starter-webmvc` |
| Base de datos | H2 (en memoria), con consola habilitada vía `spring-boot-h2console` |
| MapStruct | 1.6.3 (mapeo entre modelo de dominio, DTOs y entidades JPA) |
| Lombok | generación de getters/setters/constructores/builders |
| Build tool | Gradle (wrapper incluido) |
| Testing | JUnit 5 (`spring-boot-starter-data-jpa-test`, `spring-boot-starter-webmvc-test`) |

## Arquitectura

El proyecto sigue el patrón **Puertos y Adaptadores**, organizando el código en tres capas principales:

```
domain/            → Entidades de negocio puras, sin dependencias de frameworks
application/       → Casos de uso y puertos (contratos de entrada/salida)
infrastructure/    → Adaptadores concretos (REST, JPA) que implementan los puertos
```

### Flujo de una petición

```
UserController (adapter de entrada REST)
        │  UserRequest → User (vía UserRequestMapper / MapStruct)
        ▼
IUserUseCase (puerto de entrada)
        │
        ▼
UserUseCaseImpl (caso de uso)
        │  delega en el puerto de salida
        ▼
UserPort (puerto de salida)
        │
        ▼
UserAdapter (adapter de salida JPA)
        │  User → UserEntity (vía UserEntityMapper / MapStruct)
        ▼
UserEntityRepository (Spring Data JPA) → H2
```

La regla de dependencia se respeta en todo momento: el **dominio** no conoce nada de Spring, JPA o HTTP; la **aplicación** solo conoce el dominio y sus propios puertos; la **infraestructura** es la única capa que conoce frameworks externos y es quien implementa esos puertos.

## Estructura del proyecto

```
src/main/java/org/cat/user/clean/architecture/
├── UserCleanArchitectureApplication.java        # Punto de entrada Spring Boot
├── domain/
│   └── model/
│       └── User.java                            # Entidad de dominio (POJO puro)
├── application/
│   ├── ports/
│   │   ├── input/
│   │   │   └── IUserUseCase.java                # Puerto de entrada (contrato del caso de uso)
│   │   └── output/
│   │       └── UserPort.java                    # Puerto de salida (contrato de persistencia)
│   └── usecases/
│       └── UserUseCaseImpl.java                 # Implementación del caso de uso "crear usuario"
└── infrastructure/
    └── adapters/
        ├── input/
        │   ├── UserController.java              # Adaptador REST (expone la API)
        │   ├── dto/
        │   │   ├── UserRequest.java              # DTO de entrada
        │   │   └── UserResponse.java             # DTO de salida
        │   └── mapper/
        │       └── UserRequestMapper.java        # Mapper DTO <-> dominio (MapStruct)
        └── output/
            ├── UserAdapter.java                  # Adaptador de persistencia (implementa UserPort)
            ├── UserEntity.java                   # Entidad JPA
            ├── UserEntityRepository.java         # Repositorio Spring Data JPA
            └── mapper/
                └── UserEntityMapper.java         # Mapper dominio <-> entidad (MapStruct)

src/main/resources/
└── application.yaml                              # Configuración de la aplicación

src/test/java/org/cat/user/clean/architecture/
└── UserCleanArchitectureApplicationTests.java    # Test de contexto Spring Boot
```

## Requisitos previos

- **JDK 21** (el wrapper de Gradle gestiona la versión del toolchain, pero se recomienda tener Java 21 disponible).
- No es necesario instalar Gradle: el proyecto incluye el wrapper (`gradlew` / `gradlew.bat`).

## Puesta en marcha

### Windows (PowerShell)

```powershell
.\gradlew.bat bootRun
```

### Linux / macOS

```bash
./gradlew bootRun
```

La aplicación arrancará por defecto en `http://localhost:8080`.

### Compilar el proyecto

```powershell
.\gradlew.bat build
```

## Consola H2

El proyecto usa una base de datos H2 en memoria. Si la consola está habilitada por defecto en la ruta estándar de Spring Boot, puedes acceder a ella en:

```
http://localhost:8080/h2-console
```

> Nota: la configuración actual (`application.yaml`) solo define el nombre de la aplicación. Si necesitas una URL de conexión específica, credenciales o ruta de consola personalizada, añade las propiedades `spring.h2.console.*` y `spring.datasource.*` correspondientes.

## API

### Crear usuario

```
POST /v1/users
Content-Type: application/json
```

**Request body:**

```json
{
  "firstname": "Ada",
  "lastname": "Lovelace",
  "email": "ada@example.com",
  "phone": "+34600000000"
}
```

**Response `201 Created`:**

```json
{
  "firstname": "Ada",
  "lastname": "Lovelace"
}
```

Ejemplo con `curl`:

```bash
curl -X POST http://localhost:8080/v1/users \
  -H "Content-Type: application/json" \
  -d '{"firstname":"Ada","lastname":"Lovelace","email":"ada@example.com","phone":"+34600000000"}'
```

> Nota: `UserResponse` solo expone `firstname` y `lastname`; `email` y `phone` no se devuelven en la respuesta actual aunque sí se persisten.

## Tests

Ejecutar la suite de tests con:

```powershell
.\gradlew.bat test
```

Actualmente el proyecto incluye un test de arranque de contexto (`UserCleanArchitectureApplicationTests`) que verifica que el contexto de Spring Boot carga correctamente.
