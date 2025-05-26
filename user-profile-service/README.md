# User Profile Service - VitalConnect
Este microservicio forma parte del ecosistema de la plataforma VitalConnect, diseñada para la gestión descentralizada, segura y trazable de historiales médicos.

El user-profile-service es responsable de la administración de los perfiles de usuario, incluyendo información personal, especialidades médicas asociadas, rol en el sistema y trazabilidad de actividad básica.

## Funcionalidad
- Gestión de datos de usuario (nombre, rut, especialidad, etc.).
- Definición de roles (paciente, médico, profesional de la salud).
- Exposición de endpoints RESTful.
- Base de datos relacional (MySQL).

## Tecnologías a utilizar
- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Lombok
- Maven
- MySQL
- AWS - EC2

## Requisitos para ejecutar localmente
- Java 17
- Maven 3.8.1 o superior
- IntelliJ IDEA (recomendado)
- MySQL 8.x instalado localmente (Workbench opcional para visualización)

## Cómo ejecutar el microservicio
Desde el directorio raíz del proyecto VitalConnect:
```bash
cd user-profile-service
mvn spring-boot:run
```
O desde IntelliJ IDEA:
- Click derecho sobre la clase principal → Run

## Estructura del proyecto
```
user-profile-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.vitalconnect.user_profile
│   │   │       ├── controller
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── UserProfileServiceApplication.java
│   │   └── resources
│   └── test
│       └── java
│           └── com.vitalconnect.user_profile
│               └── UserProfileServiceApplicationTests.java
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Endpoints disponibles:

- `POST /api/v1/users` → Crear perfil
- `GET /api/v1/users` → Listar todos los perfiles
- `GET /api/v1/users/id/{id}` → Buscar perfil por ID
- `GET /api/v1/users/rut/{rut}` → Buscar perfil por RUT
- `GET /api/v1/users/active` → Listar perfiles activos
- `GET /api/v1/users/{id}/especialidades` → Ver especialidades de un usuario
- `PUT /api/v1/users/{id}` → Actualizar perfil
- `DELETE /api/v1/users/{id}` → Eliminar perfil

## Formato de los datos en JSON
{
"nombre": "Ana",
"apellido": "Pérez",
"rut": "12345678-9",
"email": "ana.perez@mail.com",
"rol": "MEDICO",
"especialidades": ["Cardiología"],
"activo": true
}

*Si el rol es "PACIENTE", no se requieren especialidades.*

---
## Autoría
Proyecto propuesto y desarrollado como parte de la asignatura DSY1103 – Desarrollo Fullstack I (DuocUC) por Milenka Guerra.

## Licencia
Uso educativo. Código desarrollado con fines académicos.