# FitControl — Backend

API REST para la gestión de un gimnasio. Permite administrar socios, profesores, actividades e inscripciones desde la recepción del centro.

## Tecnologías

- Java 25
- Spring Boot 4.0.5
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Lombok
- Jakarta Validation

---

## Configuración y arranque

### Requisitos previos

- JDK 25
- MySQL corriendo en `localhost:3306`
- Base de datos `fitcontrol` creada

### Variables de conexión

Edita `src/main/resources/application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitcontrol
spring.datasource.username=root
spring.datasource.password=root
```

### Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La API quedará disponible en `http://localhost:8080`.

---

## Estructura del proyecto

```
src/main/java/com/fitcontrol/
├── controller/           # Endpoints REST
├── service/              # Interfaces de servicio
│   └── impl/             # Implementaciones con lógica de negocio
├── model/                # Entidades JPA
├── repository/           # Capa de acceso a datos
├── dto/
│   ├── activity/         # ActivityDTORequest, ActivityDTOResponse, ActivityMapper
│   ├── member/           # MemberDTORequest, MemberDTOResponse, MemberMapper
│   ├── teacher/          # TeacherDTORequest, TeacherDTOResponse, TeacherMapper
│   └── enrollment/       # EnrollmentDTOResponse, EnrollmentMapper
└── exception/            # Manejo centralizado de errores
```

### Patrón DTO

Cada entidad tiene tres piezas:

- **`XxxDTORequest`** — `record` con los campos que el frontend puede enviar al crear o editar. Incluye las validaciones (`@NotBlank`, `@Pattern`, etc.).
- **`XxxDTOResponse`** — `record` con los campos que el servidor devuelve. Puede incluir datos resueltos de relaciones (p.ej. `teacherName`).
- **`XxxMapper`** — clase con métodos estáticos `dto2Entity(request)` y `entity2DTO(entity)` que traducen entre ambos mundos.

---

## Modelo de datos

### Member (Socio)

| Campo | Tipo | Restricciones |
|---|---|---|
| id | Long | PK, auto-generado |
| name | String | Obligatorio, máx. 100 |
| lastName | String | Obligatorio, máx. 150 |
| dni | String | Único, formato `12345678A` |
| registrationYear | Integer | Obligatorio, entre 1900 y año actual |
| isActive | Boolean | Default: `true` |
| imageUrl | String | Máx. 500 |
| membershipType | String | Máx. 50 |

### Teacher (Profesor)

| Campo | Tipo | Restricciones |
|---|---|---|
| id | Long | PK, auto-generado |
| name | String | Obligatorio, máx. 100 |
| dni | String | Único, formato `12345678A` |
| hiringYear | Integer | Obligatorio |
| isActive | Boolean | Default: `true` |
| imageUrl | String | Máx. 500 |

### Activity (Actividad)

| Campo | Tipo | Restricciones |
|---|---|---|
| id | Long | PK, auto-generado |
| title | String | Máx. 150 |
| name | String | Obligatorio, máx. 100 |
| description | String | Máx. 255 |
| price | BigDecimal | No negativo |
| imageUrl | String | Máx. 500 |
| schedule | String | Obligatorio, máx. 100 |
| capacity | Integer | Obligatorio, mín. 1 |
| isActive | Boolean | Default: `true` |
| startDate | LocalDateTime | — |
| teacher | Teacher | Obligatorio (ManyToOne) |

### Relaciones

- **Teacher → Activity**: OneToMany
- **Member ↔ Activity**: ManyToMany (tabla `activity_users`)

---

## API Endpoints

### Socios — `/api/users`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/users` | Crear socio |
| `GET` | `/api/users` | Listar socios (`?activeOnly=true/false`) |
| `GET` | `/api/users/active` | Listar socios activos |
| `GET` | `/api/users/{id}` | Obtener socio por ID |
| `GET` | `/api/users/{id}/activities` | Actividades en las que está inscrito un socio |
| `PUT` | `/api/users/{id}` | Actualizar socio |
| `DELETE` | `/api/users/{id}` | Baja lógica del socio (`isActive = false`) |

### Profesores — `/api/teachers`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/teachers` | Listar todos los profesores |
| `GET` | `/api/teachers/active` | Listar profesores activos |
| `GET` | `/api/teachers/{id}` | Obtener profesor por ID |
| `GET` | `/api/teachers/{id}/activities` | Actividades de un profesor |
| `POST` | `/api/teachers` | Crear profesor |
| `PUT` | `/api/teachers/{id}` | Actualizar profesor |
| `DELETE` | `/api/teachers/{id}` | Eliminar profesor |

### Actividades — `/api/activities`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/activities` | Listar todas las actividades |
| `GET` | `/api/activities/active` | Listar actividades activas |
| `GET` | `/api/activities/future` | Listar actividades futuras |
| `GET` | `/api/activities/{id}` | Obtener actividad por ID |
| `GET` | `/api/activities/{id}/users` | Socios inscritos en una actividad |
| `GET` | `/api/activities/teacher/{teacherId}` | Actividades de un profesor |
| `POST` | `/api/activities` | Crear actividad |
| `PUT` | `/api/activities/{id}` | Actualizar actividad |
| `DELETE` | `/api/activities/{id}` | Eliminar actividad |

### Inscripciones — `/api/enrollments`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/enrollments/{activityId}/{userId}` | Inscribir socio en actividad |
| `DELETE` | `/api/enrollments/{activityId}/{userId}` | Cancelar inscripción |

---

## Reglas de negocio

**Socios**
- El DNI debe seguir el formato español: 8 dígitos + 1 letra mayúscula (ej: `12345678A`).
- El año de alta debe estar entre 1900 y el año actual.
- La baja es lógica (`isActive = false`), el registro no se elimina.
- No se permiten DNIs duplicados.

**Profesores**
- Mismo formato de DNI que los socios.
- Un profesor inactivo no puede ser asignado a actividades.
- No se permiten DNIs duplicados.

**Actividades**
- Deben tener un profesor activo asignado.
- No puede haber dos actividades con el mismo nombre para el mismo profesor.

**Inscripciones**
- Solo pueden inscribirse socios activos (con la cuota al día).
- Solo se permite la inscripción en actividades futuras (`startDate > ahora`).
- No se permiten inscripciones duplicadas en la misma actividad.
- Un socio puede estar inscrito en un máximo de **3 actividades futuras** simultáneamente.

---

## Manejo de errores

El handler global (`GlobalExceptionHandler`) devuelve respuestas consistentes en español:

| Excepción | HTTP | Descripción |
|---|---|---|
| `ResourceNotFoundException` | 404 | Recurso no encontrado |
| `DuplicateResourceException` | 409 | DNI o nombre duplicado |
| `BusinessRuleException` | 409 / 403 | Regla de negocio incumplida |
| `DataIntegrityViolationException` | 409 | Violación de constraint en base de datos |
| `MethodArgumentNotValidException` | 400 | Campos del formulario inválidos |
| `ConstraintViolationException` | 400 | Validaciones de parámetros |
| `MethodArgumentTypeMismatchException` | 400 | Tipo incorrecto en parámetro de URL |
| `MissingServletRequestParameterException` | 400 | Parámetro obligatorio ausente |
| `HttpMessageNotReadableException` | 400 | JSON malformado |
| `Exception` | 500 | Error inesperado del servidor |

**Formato de respuesta de error:**
```json
{
  "timestamp": "2026-04-22T10:00:00",
  "status": 404,
  "error": "No encontrado",
  "message": "No se ha encontrado ningún socio con id: 5",
  "path": "/api/users/5"
}
```

**Formato con errores de validación:**
```json
{
  "timestamp": "2026-04-22T10:00:00",
  "status": 400,
  "error": "Solicitud incorrecta",
  "message": "Hay campos incorrectos en el formulario. Revisa los errores indicados.",
  "path": "/api/users",
  "validationErrors": {
    "dni": "El DNI debe tener 8 números seguidos de una letra mayúscula (ej: 12345678A)",
    "name": "El nombre del socio es obligatorio"
  }
}
```

---

## Verificación de errores

Peticiones para comprobar que cada excepción devuelve la respuesta correcta.

### ResourceNotFoundException — 404

```http
GET  http://localhost:8080/api/users/9999
GET  http://localhost:8080/api/teachers/9999
GET  http://localhost:8080/api/activities/9999
DELETE http://localhost:8080/api/teachers/9999
POST http://localhost:8080/api/enrollments/9999/1
POST http://localhost:8080/api/enrollments/1/9999
```

### DuplicateResourceException — 409

Crear dos socios con el mismo DNI:

```http
POST http://localhost:8080/api/users
Content-Type: application/json

{ "name": "Carlos", "lastName": "López", "dni": "12345678A", "registrationYear": 2022 }
```
```http
POST http://localhost:8080/api/users
Content-Type: application/json

{ "name": "Otro", "lastName": "Socio", "dni": "12345678A", "registrationYear": 2023 }
```

Lo mismo aplica para `POST /api/teachers` con DNI repetido.

### BusinessRuleException — 403 / 409

**Socio inactivo intenta inscribirse (403):**
```http
POST http://localhost:8080/api/enrollments/{activityId}/{idSocioInactivo}
```

**Actividad ya comenzada o sin fecha (409):**
```http
POST http://localhost:8080/api/enrollments/{activityIdPasada}/{userId}
```

**Inscripción duplicada — llamar dos veces seguidas (409):**
```http
POST http://localhost:8080/api/enrollments/1/1
POST http://localhost:8080/api/enrollments/1/1
```

**Socio con 3 actividades futuras — cuarta inscripción (409):**
```http
POST http://localhost:8080/api/enrollments/1/1
POST http://localhost:8080/api/enrollments/2/1
POST http://localhost:8080/api/enrollments/3/1
POST http://localhost:8080/api/enrollments/4/1
```

**Cancelar inscripción inexistente (409):**
```http
DELETE http://localhost:8080/api/enrollments/1/1
```
_(sin haber inscrito antes al socio)_

**Asignar actividad a profesor inactivo (409):**
```http
POST http://localhost:8080/api/activities
Content-Type: application/json

{ "name": "Pilates", "schedule": "Martes 18:00", "capacity": 15, "teacherId": {idProfesorInactivo} }
```

**Dar de baja a un socio ya inactivo (409):**
```http
DELETE http://localhost:8080/api/users/{idSocioYaInactivo}
```
