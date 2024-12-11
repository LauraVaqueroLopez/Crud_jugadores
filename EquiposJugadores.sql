-- Crear base de datos
CREATE DATABASE gestion_equipos;

-- Usar la base de datos
USE gestion_equipos;

-- Crear la tabla Equipo
CREATE TABLE Equipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estadio VARCHAR(100) NOT NULL
);

-- Crear la tabla Jugador
CREATE TABLE Jugador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estatura FLOAT NOT NULL,
    peso FLOAT NOT NULL,
    idEquipo INT,
    FOREIGN KEY (idEquipo) REFERENCES Equipo(id) ON DELETE CASCADE
);
