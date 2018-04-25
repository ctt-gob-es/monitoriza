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
  
 -- Secuencia necesaria para la clave de la tabla ALARM_MONITORIZA.
CREATE SEQUENCE SQ_ALARM_MONITORIZA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;
  
 -- Secuencia necesaria para la clave de la tabla MAIL_MONITORIZA.
CREATE SEQUENCE SQ_MAIL_MONITORIZA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 
  
 -- Secuencia necesaria para la clave de la tabla PLATFORM_AFIRMA.
CREATE SEQUENCE SQ_PLATFORM_MONITORIZA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 
  
  -- Secuencia necesaria para la clave de la tabla SERVICE_MONITORIZA.
CREATE SEQUENCE SQ_SERVICE_MONITORIZA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 
  
    -- Secuencia necesaria para la clave de la tabla TIMER_MONITORIZA.
CREATE SEQUENCE SQ_TIMER_MONITORIZA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 
  
     -- Secuencia necesaria para la clave de la tabla TIMER_MONITORIZA.
CREATE SEQUENCE SQ_R_ALARM_MAIL
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 

  