-- Active: 1729614686330@@127.0.0.1@3306@ligaparanabasquet
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

ALTER TABLE equipo
ADD COLUMN lat DOUBLE,
ADD COLUMN lng DOUBLE;

ALTER TABLE partido
ADD COLUMN estado ENUM('proximo', 'en_vivo', 'terminado');

CREATE TABLE Auditoria (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    accion VARCHAR(50),
    tabla_afectada VARCHAR(50),
    id_registro_afectado INT,
    detalle VARCHAR(255)
);

DELIMITER //

CREATE TRIGGER trg_usuario_insert
AFTER INSERT ON Usuario
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'ALTA', 
        'Usuario', 
        NEW.id_usuario, 
        CONCAT('Se creó el usuario: ', NEW.nombre)
    );
END;
//

CREATE TRIGGER trg_usuario_update
AFTER UPDATE ON Usuario
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'MODIFICACION',
        'Usuario',
        NEW.id_usuario,
        CONCAT('Se modificó el usuario: ', NEW.nombre)
    );
END;
//

CREATE TRIGGER trg_usuario_delete
BEFORE DELETE ON Usuario
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'BAJA',
        'Usuario',
        OLD.id_usuario,
        CONCAT('Se eliminó el usuario: ', OLD.nombre)
    );
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER trg_equipo_insert
AFTER INSERT ON Equipo
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'ALTA',
        'Equipo',
        NEW.id_equipo,
        CONCAT('Se creó el equipo: ', NEW.nombre)
    );
END;
//

CREATE TRIGGER trg_equipo_update
AFTER UPDATE ON Equipo
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'MODIFICACION',
        'Equipo',
        NEW.id_equipo,
        CONCAT('Se modificó el equipo: ', NEW.nombre)
    );
END;
//

CREATE TRIGGER trg_equipo_delete
BEFORE DELETE ON Equipo
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'BAJA',
        'Equipo',
        OLD.id_equipo,
        CONCAT('Se eliminó el equipo: ', OLD.nombre)
    );
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER trg_noticia_insert
AFTER INSERT ON Noticia
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'ALTA',
        'Noticia',
        NEW.id_noticia,
        CONCAT('Se creó la noticia: ', NEW.titulo)
    );
END;
//

CREATE TRIGGER trg_noticia_update
AFTER UPDATE ON Noticia
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'MODIFICACION',
        'Noticia',
        NEW.id_noticia,
        CONCAT('Se modificó la noticia: ', NEW.titulo)
    );
END;
//

CREATE TRIGGER trg_noticia_delete
BEFORE DELETE ON Noticia
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'BAJA',
        'Noticia',
        OLD.id_noticia,
        CONCAT('Se eliminó la noticia: ', OLD.titulo)
    );
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER trg_partido_insert
AFTER INSERT ON Partido
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'ALTA',
        'Partido',
        NEW.id_partido,
        CONCAT(
            'Se creó el partido entre el equipo local ID ',
            NEW.id_equipo_local,
            ' y el equipo visitante ID ',
            NEW.id_equipo_visitante,
            ' con fecha ',
            DATE_FORMAT(NEW.fecha, '%Y-%m-%d %H:%i')
        )
    );
END;
//

CREATE TRIGGER trg_partido_update
AFTER UPDATE ON Partido
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'MODIFICACION',
        'Partido',
        NEW.id_partido,
        CONCAT(
            'Se modificó el partido entre local ID ',
            NEW.id_equipo_local,
            ' y visitante ID ',
            NEW.id_equipo_visitante,
            '. Nuevo estado: ',
            NEW.estado
        )
    );
END;
//

CREATE TRIGGER trg_partido_delete
BEFORE DELETE ON Partido
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (accion, tabla_afectada, id_registro_afectado, detalle)
    VALUES (
        'BAJA',
        'Partido',
        OLD.id_partido,
        CONCAT(
            'Se eliminó el partido entre el equipo local ID ',
            OLD.id_equipo_local,
            ' y el visitante ID ',
            OLD.id_equipo_visitante,
            ' que se jugaba el ',
            DATE_FORMAT(OLD.fecha, '%Y-%m-%d %H:%i')
        )
    );
END;
//

DELIMITER ;