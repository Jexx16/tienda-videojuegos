-- ============================================
-- BASE DE DATOS: TIENDA DE VIDEOJUEGOS
-- ============================================
-- Este script crea toda la base de datos y la llena con datos de prueba
-- ============================================

-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS tienda_videojuegos;
USE tienda_videojuegos;

-- 2. Eliminar tablas si existen (para empezar limpio)
DROP TABLE IF EXISTS items_venta;
DROP TABLE IF EXISTS ventas;
DROP TABLE IF EXISTS carrito_items;
DROP TABLE IF EXISTS juegos;
DROP TABLE IF EXISTS usuarios;

-- ============================================
-- TABLA: USUARIOS
-- ============================================
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    rol VARCHAR(20) DEFAULT 'USER',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA: JUEGOS
-- ============================================
CREATE TABLE juegos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    categoria VARCHAR(100),
    plataforma VARCHAR(100),
    precio DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    imagen_url VARCHAR(500),
    stock INT DEFAULT 0,
    disponible BOOLEAN DEFAULT FALSE
);

-- ============================================
-- TABLA: CARRITO_ITEMS
-- ============================================
CREATE TABLE carrito_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    juego_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (juego_id) REFERENCES juegos(id) ON DELETE CASCADE
);

-- ============================================
-- TABLA: VENTAS
-- ============================================
CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'COMPLETADA',
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- ============================================
-- TABLA: ITEMS_VENTA
-- ============================================
CREATE TABLE items_venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id BIGINT,
    juego_titulo VARCHAR(200) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id) ON DELETE CASCADE
);

-- ============================================
-- DATOS DE PRUEBA: USUARIOS
-- ============================================
INSERT INTO usuarios (nombre, password, email, rol) VALUES 
('jesus', '12345', 'jesus@tienda.com', 'ADMIN'),
('usuario1', '123456', 'usuario1@email.com', 'USER');

-- ============================================
-- DATOS DE PRUEBA: JUEGOS
-- ============================================
INSERT INTO juegos (titulo, categoria, plataforma, precio, descripcion, imagen_url, stock, disponible) VALUES
('The Legend of Zelda: Breath of the Wild', 'Aventura', 'Nintendo Switch', 59.99, 'Explora Hyrule en esta aventura épica', 'Zelda Breath of the Wild.jpg', 10, TRUE),
('God of War Ragnarök', 'Acción', 'PlayStation 5', 69.99, 'La conclusión de la saga nórdica de Kratos', 'God Of War.avif', 8, TRUE),
('Cyberpunk 2077', 'RPG', 'PC', 39.99, 'RPG futurista en mundo abierto', 'Cyberpunk 2077.webp', 15, TRUE),
('Super Mario Odyssey', 'Plataformas', 'Nintendo Switch', 49.99, 'Mario viaja por reinos desconocidos', 'SuperMario Odyssey.webp', 5, TRUE),
('Elden Ring', 'RPG', 'PC', 59.99, 'La nueva obra de Hidetaka Miyazaki', 'Elden Ring.jpg', 7, TRUE),
('Red Dead Redemption 2', 'Western', 'PlayStation 4', 49.99, 'Una épica historia en el Lejano Oeste', 'Red Dead Redemption 2.jpg', 3, TRUE),
('Fortnite', 'Battle Royale', 'Multiplataforma', 0.00, 'El Battle Royale más famoso del mundo', 'Fortnite.jpg', 999, TRUE),
('Call of Duty: Warzone', 'Shooter', 'Multiplataforma', 0.00, 'Battle Royale gratuito', 'Call of Duty Warzone.webp', 999, TRUE),
('Minecraft', 'Sandbox', 'Multiplataforma', 29.99, 'Construye y explora mundos infinitos', 'Minecraft.webp', 20, TRUE),
('FIFA 24', 'Deportes', 'Multiplataforma', 59.99, 'El simulador de fútbol más realista', 'FIFA 24.webp', 12, TRUE);

-- ============================================
-- VERIFICACIÓN DE DATOS
-- ============================================
SELECT '=========================================' AS '';
SELECT 'BASE DE DATOS CREADA CORRECTAMENTE' AS mensaje;
SELECT '=========================================' AS '';

SELECT '📊 TABLAS CREADAS:' AS '';
SHOW TABLES;

SELECT '👥 USUARIOS:' AS '';
SELECT id, nombre, email, rol FROM usuarios;

SELECT '🎮 JUEGOS:' AS '';
SELECT id, titulo, precio, stock FROM juegos;

SELECT '=========================================' AS '';
SELECT '¡BASE DE DATOS LISTA PARA USAR!' AS '';
SELECT '=========================================' AS '';