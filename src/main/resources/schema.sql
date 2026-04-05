ALTER TABLE IF EXISTS wcontact DROP COLUMN IF EXISTS alias;

-- Agregar columna password a wusuario si no existe
ALTER TABLE IF EXISTS wusuario 
ADD COLUMN IF NOT EXISTS password VARCHAR(255);

-- Actualizar registros con NULL a una contraseña por defecto
UPDATE wusuario 
SET password = '$2a$10$slYQmyNdGzin7olVXiemBe4P6LRxbRNvL9LcpGN4YuXyAVP4UZxHK'
WHERE password IS NULL;