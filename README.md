# FitControl Backend

API REST para la gestión de un gimnasio. Permite administrar socios, profesores, actividades e inscripciones.

## Tecnologías

- Java 25
- Spring Boot 4.0.5
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Lombok
- Jakarta Validation

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
├── controller/       # Endpoints REST
├── service/          # Interfaces de servicio
│   └── impl/         # Implementaciones de negocio
├── model/            # Entidades JPA
├── repository/       # Capa de acceso a datos
├── dto/              # Objetos de transferencia de datos
└── exception/        # Manejo centralizado de errores
```

---

## Modelo de datos

### Entidades

**Member (Socio)**
| Campo | Tipo | Restricciones |
|---|---|---|
| id | Long | PK, auto-generado |
| name | String | Obligatorio, máx. 100 |
| lastName | String | Obligatorio, máx. 150 |
| dni | String | Único, patrón `^[0-9]{8}[A-Z]$` |
| registrationYear | Integer | Obligatorio, 1900–año actual |
| isActive | Boolean | Default: `true` |
| imageUrl | String | Máx. 500 |

**Teacher (Profesor)**
| Campo | Tipo | Restricciones |
|---|---|---|
| id | Long | PK, auto-generado |
| name | String | Obligatorio, máx. 100 |
| dni | String | Único, patrón `^[0-9]{8}[A-Z]$` |
| hiringYear | Integer | Obligatorio |
| isActive | Boolean | Default: `true` |
| imageUrl | String | Máx. 500 |

**Activity (Actividad)**
| Campo | Tipo | Restricciones |
|---|---|---|
| id | Long | PK, auto-generado |
| name | String | Obligatorio, máx. 100 |
| description | String | Máx. 255 |
| schedule | String | Obligatorio, máx. 100 |
| capacity | Integer | Mín. 1 |
| isActive | Boolean | Default: `true` |
| startDate | LocalDateTime | — |
| teacher | Teacher | Obligatorio (ManyToOne) |

### Relaciones

- **Teacher → Activity**: OneToMany (cascade delete)
- **Member ↔ Activity**: ManyToMany (tabla `activity_member`)

---

## API Endpoints

### Socios — `/api/members`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/members` | Crear socio |
| `GET` | `/api/members` | Listar socios (`?activeOnly=true/false`) |
| `GET` | `/api/members/{id}` | Obtener socio por ID |
| `PUT` | `/api/members/{id}` | Actualizar socio |
| `DELETE` | `/api/members/{id}` | Baja lógica del socio |

### Profesores — `/api/teachers`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/teachers` | Listar todos los profesores |
| `GET` | `/api/teachers/active` | Listar profesores activos |
| `GET` | `/api/teachers/{id}` | Obtener profesor por ID |
| `POST` | `/api/teachers` | Crear profesor |
| `PUT` | `/api/teachers/{id}` | Actualizar profesor |
| `DELETE` | `/api/teachers/{id}` | Eliminar profesor |
| `GET` | `/api/teachers/{id}/activities` | Actividades de un profesor |

### Actividades — `/api/activities`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/activities` | Listar todas las actividades |
| `GET` | `/api/activities/active` | Listar actividades activas |
| `GET` | `/api/activities/future` | Listar actividades futuras |
| `GET` | `/api/activities/teacher/{teacherId}` | Actividades de un profesor |
| `GET` | `/api/activities/{id}` | Obtener actividad por ID |
| `GET` | `/api/activities/{id}/members` | Socios inscritos en una actividad |
| `POST` | `/api/activities` | Crear actividad |
| `PUT` | `/api/activities/{id}` | Actualizar actividad |
| `DELETE` | `/api/activities/{id}` | Eliminar actividad |

### Inscripciones — `/api/enrollments`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/enrollments/{activityId}/{memberId}` | Inscribir socio en actividad |
| `DELETE` | `/api/enrollments/{activityId}/{memberId}` | Dar de baja inscripción |

---

## Reglas de negocio

**Socios**
- El DNI debe seguir el formato español: 8 dígitos + 1 letra mayúscula.
- El año de inscripción debe estar entre 1900 y el año actual.
- La baja es lógica (`isActive = false`), no se elimina el registro.
- No se permiten DNIs duplicados.

**Profesores**
- Mismo formato de DNI que los socios.
- Un profesor inactivo no puede ser asignado a nuevas actividades.

**Actividades**
- Deben tener un profesor activo asignado.
- No puede haber dos actividades con el mismo nombre para el mismo profesor.

**Inscripciones**
- Solo pueden inscribirse socios activos.
- Solo se permite inscribirse en actividades futuras (`startDate > now`).
- No se permiten inscripciones duplicadas.
- Un socio puede estar inscrito en un máximo de **3 actividades futuras** simultáneamente.

---

## Manejo de errores

El controlador global (`GlobalExceptionHandler`) devuelve respuestas consistentes:

| Excepción | HTTP |
|---|---|
| `ResourceNotFoundException` | 404 Not Found |
| `DuplicateResourceException` | 409 Conflict |
| `BusinessRuleException` | 409 Conflict (o código personalizado) |
| Errores de validación | 400 Bad Request |

**Formato de error:**
```json
{
  "timestamp": "2026-04-16T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Descripción del error"
}
```
