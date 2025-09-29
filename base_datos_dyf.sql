-- Creación de la base de datos
CREATE DATABASE LigaParanaBasquet;
USE LigaParanaBasquet;

-- ======================
-- TABLA: Usuario
-- ======================
CREATE TABLE Usuario (
    IdUsuario INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Correo VARCHAR(50) UNIQUE NOT NULL,
    Contrasena VARCHAR(20) NOT NULL,
    Telefono VARCHAR(20),
    Rol ENUM('Visitante', 'Registrado', 'Empleado', 'Administrador') NOT NULL
);

-- ======================
-- TABLA: Liga
-- ======================
CREATE TABLE Liga (
    IdLiga INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    FechaInicio DATE,
    FechaFin DATE,
    Anio YEAR NOT NULL
);

-- ======================
-- TABLA: Equipo
-- ======================
CREATE TABLE Equipo (
    IdEquipo INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Ciudad VARCHAR(50),
    Direccion VARCHAR(50),
    Logo VARCHAR(255),
    Descripcion VARCHAR(255),
    IdLiga INT NOT NULL,
    FOREIGN KEY (IdLiga) REFERENCES Liga(IdLiga)
);

-- ======================
-- TABLA: Jornada
-- ======================
CREATE TABLE Jornada (
    IdJornada INT AUTO_INCREMENT PRIMARY KEY,
    Numero INT NOT NULL,
    FechaInicio DATE,
    FechaFin DATE,
    IdLiga INT NOT NULL,
    FOREIGN KEY (IdLiga) REFERENCES Liga(IdLiga)
);

-- ======================
-- TABLA: Partido
-- ======================
CREATE TABLE Partido (
    IdPartido INT AUTO_INCREMENT PRIMARY KEY,
    Fecha DATETIME NOT NULL,
    IdEquipoLocal INT NOT NULL,
    PuntosLocal INT DEFAULT 0,
    IdEquipoVisitante INT NOT NULL,
    PuntosVisitante INT DEFAULT 0,
    IdJornada INT NOT NULL,
    FOREIGN KEY (IdJornada) REFERENCES Jornada(IdJornada),
    FOREIGN KEY (IdEquipoLocal) REFERENCES Equipo(IdEquipo),
    FOREIGN KEY (IdEquipoVisitante) REFERENCES Equipo(IdEquipo)
);

-- ======================
-- TABLA: Noticia
-- ======================
CREATE TABLE Noticia (
    IdNoticia INT AUTO_INCREMENT PRIMARY KEY,
    Titulo VARCHAR(150) NOT NULL,
    Contenido TEXT NOT NULL,
    FechaPublicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Imagen VARCHAR(255),
    IdUsuario INT NOT NULL,
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario)
);

-- ======================
-- TABLA: Suscripción a Equipo (N:M)
-- ======================
CREATE TABLE SuscripcionEquipo (
    IdUsuario INT NOT NULL,
    IdEquipo INT NOT NULL,
    FechaSuscripcion DATE DEFAULT (CURRENT_DATE),
    PRIMARY KEY (IdUsuario, IdEquipo),
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario),
    FOREIGN KEY (IdEquipo) REFERENCES Equipo(IdEquipo)
);

-- ======================
-- TABLA: Producto
-- ======================
CREATE TABLE Producto (
    IdProducto INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Descripcion VARCHAR(100),
    Precio DECIMAL(10,2) NOT NULL
);

-- ======================
-- TABLA: Inventario
-- ======================
CREATE TABLE Inventario (
    IdProducto INT NOT NULL,
    Color VARCHAR(50) NOT NULL,
    Talle ENUM('S','M','L','XL') NOT NULL,
    Stock INT DEFAULT 0,
    PRIMARY KEY (IdProducto, Color, Talle),
    FOREIGN KEY (IdProducto) REFERENCES Producto(IdProducto)
);

-- ======================
-- TABLA: Carrito
-- ======================
CREATE TABLE Carrito (
    IdCarrito INT AUTO_INCREMENT PRIMARY KEY,
    IdUsuario INT NOT NULL,
    FechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario)
);

-- ======================
-- TABLA: DetalleCarrito
-- ======================
CREATE TABLE DetalleCarrito (
    IdCarrito INT NOT NULL,
    IdProducto INT NOT NULL,
    Cantidad INT NOT NULL,
    PRIMARY KEY (IdCarrito, IdProducto),
    FOREIGN KEY (IdCarrito) REFERENCES Carrito(IdCarrito),
    FOREIGN KEY (IdProducto) REFERENCES Producto(IdProducto)
);

-- ======================
-- TABLA: Compra
-- ======================
CREATE TABLE Compra (
    IdCompra INT AUTO_INCREMENT PRIMARY KEY,
    IdCarrito INT NOT NULL,
    FechaCompra DATETIME DEFAULT CURRENT_TIMESTAMP,
    MontoTotal DECIMAL(10,2) NOT NULL,
    MetodoPago ENUM('Efectivo','Tarjeta','Transferencia','MercadoPago') NOT NULL,
    FOREIGN KEY (IdCarrito) REFERENCES Carrito(IdCarrito)
);
