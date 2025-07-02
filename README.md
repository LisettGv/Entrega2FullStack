
# Proyecto FullStack Spring Boot API

## Descripción
Este proyecto es un backend desarrollado en **Java 17** con **Spring Boot**, que expone una API RESTful para la gestión de un sistema educativo, permitiendo crear y administrar:

- Usuarios
- Cursos
- Contenidos
- Evaluaciones

Integra **Spring Data JPA** con **MySQL**, usa **Swagger OpenAPI** para la documentación interactiva y está preparado para pruebas unitarias con **JUnit 5** y **Mockito**.

---

## Estructura del proyecto

```
src
 └── main
     └── java
         └── cl.duoc.ligranadillo.proyectoprueba
             ├── controller
             ├── model
             ├── repository
             └── service
 └── test
     └── java
         └── cl.duoc.ligranadillo.proyectoprueba
```

### Paquetes principales

| Paquete       | Contenido                                      |
|---------------|-----------------------------------------------|
| `controller`  | Controladores REST (UserController, etc)      |
| `model`       | Entidades JPA (User, Curso, Contenido, etc)   |
| `repository`  | Interfaces JpaRepository                     |
| `service`     | Servicios con lógica de negocio              |

---

## Endpoints principales

### Usuarios
- `POST /api/v2/users/crear` - Crear usuario
- `GET /api/v2/users/listar` - Listar usuarios
- `GET /api/v2/users/{id}` - Obtener usuario por ID
- `PUT /api/v2/users/{id}` - Actualizar usuario
- `DELETE /api/v2/users/{id}` - Eliminar usuario
- `POST /api/v2/users/login` - Login (retorna mensaje si usuario y contraseña son correctos)

### Cursos
- `POST /api/v2/cursos/crear` - Crear curso
- `GET /api/v2/cursos/listar` - Listar cursos
- `GET /api/v2/cursos/{id}` - Obtener curso por ID
- `PUT /api/v2/cursos/{id}` - Actualizar curso
- `DELETE /api/v2/cursos/{id}` - Eliminar curso

### Contenidos
- `POST /api/v2/contenidos/crear` - Crear contenido
- `GET /api/v2/contenidos/listar` - Listar contenidos
- `GET /api/v2/contenidos/{id}` - Obtener contenido por ID
- `PUT /api/v2/contenidos/{id}` - Actualizar contenido
- `DELETE /api/v2/contenidos/{id}` - Eliminar contenido

### Evaluaciones
- `POST /api/v2/evaluaciones/crear` - Crear evaluación
- `GET /api/v2/evaluaciones/listar` - Listar evaluaciones
- `GET /api/v2/evaluaciones/{id}` - Obtener evaluación por ID
- `PUT /api/v2/evaluaciones/{id}` - Actualizar evaluación
- `DELETE /api/v2/evaluaciones/{id}` - Eliminar evaluación

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- MySQL 8
- Lombok
- Swagger OpenAPI (springdoc)
- JUnit 5 + Mockito

---

## Configuración del proyecto

1. Clona el repositorio
   ```
   git clone https://github.com/usuario/proyecto.git
   cd proyecto
   ```

2. Configura el archivo `application.properties` o `application.yml` para tu base de datos MySQL.

   Ejemplo `application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/educacion
   spring.datasource.username=root
   spring.datasource.password=tu_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. Ejecuta la aplicación:
   ```
   ./mvnw spring-boot:run
   ```

---

## Acceso a la documentación Swagger

Una vez corriendo en `localhost:8080`, accede a:

```
http://localhost:8080/swagger-ui/index.html
```

Para probar cada endpoint, ver parámetros y respuestas esperadas.

---

## Pruebas unitarias

El proyecto incluye tests con JUnit 5 y Mockito, ubicados en `src/test/java`.

Para ejecutarlos:

```
./mvnw test
```

---