-- Active: 1759343905202@@127.0.0.1@3306
DROP DATABASE IF EXISTS LigaParanaBasquet;
CREATE DATABASE LigaParanaBasquet;
USE LigaParanaBasquet;


-- ======================
-- TABLA: Usuario
-- ======================
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(60) NOT NULL,
    rol ENUM('Visitante', 'Registrado', 'Empleado', 'Administrador') NOT NULL
);


-- ======================
-- TABLA: Liga
-- ======================
CREATE TABLE Liga (
    id_liga INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    anio YEAR NOT NULL
);


-- ======================
-- TABLA: Equipo
-- ======================
CREATE TABLE Equipo (
    id_equipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(50),
    direccion VARCHAR(50),
    logo VARCHAR(255),
    descripcion VARCHAR(255),
    id_liga INT NOT NULL,
    FOREIGN KEY (id_liga) REFERENCES Liga(id_liga)
);


-- ======================
-- TABLA: Jornada
-- ======================
CREATE TABLE Jornada (
    id_jornada INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    id_liga INT NOT NULL,
    FOREIGN KEY (id_liga) REFERENCES Liga(id_liga) ON DELETE CASCADE
);


-- ======================
-- TABLA: Partido
-- ======================
CREATE TABLE Partido (
    id_partido INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    id_equipo_local INT NOT NULL,
    puntos_local INT DEFAULT 0,
    id_equipo_visitante INT NOT NULL,
    puntos_visitante INT DEFAULT 0,
    id_jornada INT NOT NULL,
    FOREIGN KEY (id_jornada) REFERENCES Jornada(id_jornada) ON DELETE CASCADE,
    FOREIGN KEY (id_equipo_local) REFERENCES Equipo(id_equipo),
    FOREIGN KEY (id_equipo_visitante) REFERENCES Equipo(id_equipo)
);


-- ======================
-- TABLA: Noticia
-- ======================
CREATE TABLE Noticia (
    id_noticia INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    contenido TEXT NOT NULL,
    fecha_publicacion DATETIME DEFAULT (CURRENT_TIMESTAMP),
    imagen VARCHAR(255)
);


-- ======================
-- TABLA: Suscripción a Equipo (N:M)
-- ======================
CREATE TABLE SuscripcionEquipo (
    id_usuario INT NOT NULL,
    id_equipo INT NOT NULL,
    fecha_suscripcion DATETIME DEFAULT (CURRENT_TIMESTAMP),
    PRIMARY KEY (id_usuario, id_equipo),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo) ON DELETE CASCADE
);


-- ======================
-- TABLA: Producto
-- ======================
CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    precio DECIMAL(10,2) NOT NULL
);


-- ======================
-- TABLA: Inventario
-- ======================
CREATE TABLE Inventario (
    id_producto INT NOT NULL,
    color VARCHAR(50) NOT NULL,
    talle ENUM('S','M','L','XL') NOT NULL,
    stock INT DEFAULT 0,
    PRIMARY KEY (id_producto, color, talle),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);


-- ======================
-- TABLA: Carrito
-- ======================
CREATE TABLE Carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
);


-- ======================
-- TABLA: DetalleCarrito
-- ======================
CREATE TABLE DetalleCarrito (
    id_carrito INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (id_carrito, id_producto),
    FOREIGN KEY (id_carrito) REFERENCES Carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);


-- ======================
-- TABLA: Compra
-- ======================
CREATE TABLE Compra (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_carrito INT NOT NULL,
    fecha_compra DATETIME DEFAULT CURRENT_TIMESTAMP,
    monto_total DECIMAL(10,2) NOT NULL,
    metodo_pago ENUM('Efectivo','Tarjeta','Transferencia','MercadoPago') NOT NULL,
    FOREIGN KEY (id_carrito) REFERENCES Carrito(id_carrito) ON DELETE CASCADE
);
