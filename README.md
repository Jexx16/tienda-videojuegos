# 🎮 GameStore - Tienda de Videojuegos

## 📋 Descripción
Tienda online de videojuegos desarrollada con **Spring Boot**, **MySQL**, **HTML**, **CSS** y **JavaScript**. Permite a los usuarios comprar videojuegos y a los administradores gestionar el inventario.

## 🛠️ Tecnologías utilizadas
- **Backend:** Java 25, Spring Boot 3.5.14, Spring Data JPA, Hibernate
- **Frontend:** HTML5, CSS3, JavaScript (ES6)
- **Base de Datos:** MySQL 8.0
- **Build Tool:** Maven
- **Control de versiones:** Git / GitHub


### 1. Clonar el repositorio
git clone https://github.com/Jexx16/tienda-videojuegos.git
cd tienda-videojuegos

## 🚀 Instalación y ejecución
 Crear la base de datos
Abrir MySQL Workbench

Ejecutar el archivo bd_tienda_videojuegos.sql

O en la terminal de MySQL:
source bd_tienda_videojuegos.sql

 Configurar la conexión a MySQL
Editar src/main/resources/application.properties:

properties
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA.PROFE_MYSQL

 Ejecutar la aplicación
mvn clean spring-boot:run

 Acceder a la aplicación
Abrir navegador: http://localhost:8080

 Credenciales de prueba
Rol	Usuario	Contraseña
 Administrador	jesus	12345
 Usuario normal	usuario1	123456
