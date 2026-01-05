# Hello World con Spring Boot, H2 y Docker

Este es un proyecto simple de "Hola Mundo" que demuestra cómo configurar una aplicación Spring Boot con base de datos H2 y Docker.

## Estructura del Proyecto

- **Spring Boot 3.2.0** - Framework principal
- **H2 Database** - Base de datos en memoria
- **Docker** - Contenerización de la aplicación
- **Maven** - Gestión de dependencias

## Endpoints

### API REST

- `GET /` - Mensaje de Hola Mundo
- `GET /api/status` - Estado de la aplicación
- `GET /api/users` - Obtener todos los usuarios
- `POST /api/users` - Crear un nuevo usuario
- `GET /api/users/{id}` - Obtener usuario por ID
- `DELETE /api/users/{id}` - Eliminar usuario

### H2 Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Cómo Ejecutar

### Opción 1: Usando Docker Compose (Recomendado)

```bash
docker-compose up --build
```

La aplicación estará disponible en `http://localhost:8080`

### Opción 2: Usando Maven

```bash
mvn spring-boot:run
```

### Opción 3: Construir y ejecutar el JAR

```bash
mvn clean package
java -jar target/hello-world-spring-h2-1.0.0.jar
```

## Ejemplos de Uso

### Crear un usuario

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","email":"juan@example.com"}'
```

### Obtener todos los usuarios

```bash
curl http://localhost:8080/api/users
```

### Verificar estado

```bash
curl http://localhost:8080/api/status
```

## Características

- Base de datos H2 en memoria
- Consola H2 habilitada para depuración
- API RESTful completa
- Contenerizada con Docker
- Logs SQL habilitados
