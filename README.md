# Hello World con Spring Boot, H2 y Docker

Este es un proyecto simple de "Hola Mundo" que demuestra cómo configurar una aplicación Spring Boot con base de datos H2 y Docker. Incluye Spring Boot DevTools para desarrollo con hot reload.

## Características de Desarrollo

- **Spring Boot DevTools** - Habilita el hot reload automático
- **Live Reload** - Recarga automática del navegador
- **Restart automático** - La aplicación se reinicia automáticamente al cambiar el código

## Estructura del Proyecto

- **Spring Boot 3.2.0** - Framework principal
- **H2 Database** - Base de datos en memoria
- **Docker** - Contenerización de la aplicación
- **Maven** - Gestión de dependencias
- **DevTools** - Herramientas de desarrollo con hot reload

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

### Opción 1: Desarrollo con Hot Reload (Recomendado para desarrollo)

```bash
mvn spring-boot:run
```

Con esta opción:
- Los cambios en el código se recargan automáticamente
- Live reload actualiza el navegador automáticamente
- La base de datos H2 se reinicia con cada cambio

### Opción 2: Usando Docker Compose (Producción)

```bash
docker-compose up --build
```

La aplicación estará disponible en `http://localhost:8080`

### Opción 3: Construir y ejecutar el JAR

```bash
mvn clean package
java -jar target/hello-world-spring-h2-1.0.0.jar
```

## Notas sobre DevTools

- DevTools solo funciona en modo desarrollo (no en producción)
- El hot reload se activa automáticamente al guardar cambios
- Para desactivar temporalmente el restart, usa: `spring.devtools.restart.enabled=false`
- Live Reload requiere una extensión del navegador para recargar automáticamente

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
