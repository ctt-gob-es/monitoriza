-- ********************************************************
-- ************** Creación de Secuencias ******************
-- ********************************************************

-- Secuencia necesaria para la clave de la tabla USER_MONITORIZA.
CREATE SEQUENCE SQ_USER_MONITORIZA AS NUMERIC(19)
  START WITH 2 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
 -- Secuencia necesaria para la clave de la tabla ALARM_MONITORIZA.
CREATE SEQUENCE SQ_ALARM_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
 
 -- Secuencia necesaria para la clave de la tabla MAIL_MONITORIZA.
CREATE SEQUENCE SQ_MAIL_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
 -- Secuencia necesaria para la clave de la tabla PLATFORM_AFIRMA.
CREATE SEQUENCE SQ_PLATFORM_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
  -- Secuencia necesaria para la clave de la tabla SERVICE_MONITORIZA.
CREATE SEQUENCE SQ_SERVICE_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
    -- Secuencia necesaria para la clave de la tabla TIMER_MONITORIZA.
CREATE SEQUENCE SQ_TIMER_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
     -- Secuencia necesaria para la clave de la tabla TIMER_MONITORIZA.
CREATE SEQUENCE SQ_R_ALARM_MAIL AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
  -- Secuencia necesaria para la clave de la tabla CERTIFICATES
CREATE SEQUENCE SQ_CERTIFICATES AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
 -- Secuencia necesaria para la clave de la tabla SYSTEM_CERTIFICATE.
CREATE SEQUENCE SQ_SYSTEM_CERTIFICATE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
 
  -- Secuencia necesaria para la clave de la tabla R_ALARM_MAIL_DEGRADED.
CREATE SEQUENCE SQ_R_ALARM_MAIL_DEGRADED AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
  -- Secuencia necesaria para la clave de la tabla R_ALARM_MAIL_DOWN.
CREATE SEQUENCE SQ_R_ALARM_MAIL_DOWN AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
-- Secuencia necesaria para la clave de la tabla C_STATUS_CERTIFICATES.
CREATE SEQUENCE SQ_C_STATUS_CERTIFICATES AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
  -- Secuencia necesaria para la clave de la tabla TIMER_SCHEDULED.
CREATE SEQUENCE SQ_TIMER_SCHEDULED AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;

  -- Secuencia necesaria para la clave de la tabla C_AUTHENTICATION_TYPE.
CREATE SEQUENCE SQ_C_AUTHENTICATION_TYPE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
-- Secuencia necesaria para la clave de la tabla VALID_SERVICE.
CREATE SEQUENCE SQ_VALID_SERVICE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
    
-- Secuencia necesaria para la clave de la tabla TIMER_SCHEDULED.
CREATE SEQUENCE SQ_REQUEST_SERVICE_FILE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
-- Secuencia necesaria para la clave de la tabla NODE_MONITORIZA.
CREATE SEQUENCE SQ_NODE_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
-- Secuencia necesaria para la clave de la tabla CONF_SERVER_MAIL.
CREATE SEQUENCE SQ_CONF_SERVER_MAIL AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
-- Secuencia necesaria para la clave de la tabla CONF_SERVER_MAIL.
CREATE SEQUENCE SQ_METHOD_VALIDATION AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
-- Secuencia necesaria para la clave de la tabla CONF_SERVER_MAIL.
CREATE SEQUENCE SQ_CONF_SPIE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
 -- Secuencia necesaria para la clave de la tabla SPIE_SCHEDULED.
CREATE SEQUENCE SQ_SPIE_SCHEDULED AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
  -- Secuencia necesaria para la clave de la tabla SPIE_TYPE.
CREATE SEQUENCE SQ_SPIE_TYPE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;