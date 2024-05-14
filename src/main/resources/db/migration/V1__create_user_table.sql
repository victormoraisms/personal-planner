CREATE TABLE usuario (
                         idUsuario UUID PRIMARY KEY,
                         username VARCHAR(255) NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         dicaSenha VARCHAR(255),
                         isAdmin BOOLEAN
);