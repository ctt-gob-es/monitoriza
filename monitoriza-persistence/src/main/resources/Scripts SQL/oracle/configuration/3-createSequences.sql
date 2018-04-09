-- ********************************************************
-- ************** Creación de Secuencias ******************
-- ********************************************************

-- Secuencia necesaria para la clave de la tabla USER_MONITORIZA.
CREATE SEQUENCE SQ_USER_MONITORIZA
  START WITH 2
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 

  