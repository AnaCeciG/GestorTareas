# Gestor de Tareas

Esta es una aplicación Android desarrollada en *Kotlin* usando *Jetpack Compose, que permite a los usuarios gestionar sus tareas de forma rápida, intuitiva y organizada. La app se conecta a un backend hecho con **Spring Boot, el cual utiliza **PostgreSQL* como base de datos.

---

## 📱 Características principales

- Registro e inicio de sesión de usuarios.
- Crear nuevas tareas con título, fecha y hora.
- Ver la lista de tareas con opción de búsqueda.
- Cancelar (eliminar) tareas.
- Ver información sobre la app y desarrolladores.

---

## 🛠️ Tecnologías utilizadas

### Frontend (Aplicación Android)
- Kotlin
- Jetpack Compose
- Retrofit (para consumir API)
- ViewModel + State
- Navegación Compose

### Backend (API REST)
- Spring Boot
- JPA / Hibernate
- PostgreSQL


---

## 📡 Estructura de conexión con la API

- UsuarioApi: maneja el registro e inicio de sesión.
- TareaApi: permite registrar, listar y eliminar tareas.
- Ambas se comunican con servicios HTTP a endpoints del backend.
- Retrofit se encarga de hacer las llamadas y recibir respuestas.

---

## 🗄️ Base de Datos (PostgreSQL)

- El backend se conecta a una base de datos PostgreSQL.
- Las entidades Usuario y Tarea están mapeadas con JPA.
- Cada tarea está asociada a un usuario (relación uno a muchos).

---

## 📦 Instalación del proyecto

### Android App

1. Clonar este repositorio.
2. Abrir el proyecto en Android Studio.
3. Asegurarse de tener configurado un emulador o dispositivo físico.
4. Ejecutar el proyecto (Run > Run app).

### Backend (Spring Boot)

1. Abrir el proyecto backend en IntelliJ o cualquier IDE compatible.
2. Configurar application.properties o application.yml con tu base de datos PostgreSQL.
3. Ejecutar la aplicación (SpringBootApplication).
4. Verifica que la API esté corriendo en http://localhost:8080 (o el puerto que uses).

---

## 📘 Manual de Uso

### 1. Instalación y acceso inicial

- Ejecuta la app.
- Verás la pantalla de *Inicio de Sesión*.
- Si ya tienes cuenta, ingresa tu usuario y contraseña.
- Si no, selecciona *Registrarse* y completa los datos.

### 2. Menú Principal

Después de iniciar sesión, podrás:

- *Agregar Tarea*
- *Ver Tareas*
- *Cancelar Tareas*
- *Acerca de*
- *Cambiar Modo (claro/oscuro)*
- *Cerrar Sesión*

### 3. Agregar Tarea

- Pulsa *Agregar Tarea*.
- Completa:
    - Título (mínimo 3 caracteres)
    - Fecha (DD/MM/YYYY)
    - Hora (HH:MM)
- Pulsa *Registrar Tarea*.

### 4. Ver Tareas

- Pulsa *Ver Tareas*.
- Aparecerán tus tareas registradas.
- Usa el buscador para filtrar.

### 5. Cancelar Tareas

- Pulsa *Cancelar Tareas*.
- Elige una tarea y pulsa el botón de eliminar.
- Confirma para eliminarla.

### 6. Acerca de

- Pulsa *Acerca de* para ver créditos y detalles del proyecto.

### 7. Cerrar Sesión

- Pulsa *Cerrar Sesión* para salir y volver a la pantalla de login.

---

