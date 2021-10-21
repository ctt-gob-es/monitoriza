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
  
  -- Secuencia necesaria para la clave de la tabla CERTIFICATES
CREATE SEQUENCE SQ_CERTIFICATES
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;
  
 -- Secuencia necesaria para la clave de la tabla SYSTEM_CERTIFICATE.
CREATE SEQUENCE SQ_SYSTEM_CERTIFICATE
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

 
  -- Secuencia necesaria para la clave de la tabla R_ALARM_MAIL_DEGRADED.
CREATE SEQUENCE SQ_R_ALARM_MAIL_DEGRADED
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 
  
  -- Secuencia necesaria para la clave de la tabla R_ALARM_MAIL_DOWN.
CREATE SEQUENCE SQ_R_ALARM_MAIL_DOWN
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER; 
  
  -- Secuencia necesaria para la clave de la tabla ALERT_APPLICATIONS.
CREATE SEQUENCE SQ_ALERT_APPLICATIONS 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_TYPES.
 CREATE SEQUENCE SQ_ALERT_TYPES 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_APP_TEMPLATES.
CREATE SEQUENCE SQ_ALERT_APP_TEMPLATES 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_CONFIG.
CREATE SEQUENCE SQ_ALERT_CONFIG 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_RESUMES.
CREATE SEQUENCE SQ_ALERT_RESUMES 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_SYSTEMS.
CREATE SEQUENCE SQ_ALERT_SYSTEMS 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_RESUME_SYSTEMS.
CREATE SEQUENCE SQ_ALERT_RESUME_SYSTEMS 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_RESUME_LIST.
CREATE SEQUENCE SQ_ALERT_RESUME_LIST 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_CONFIG_SYSTEMS.
CREATE SEQUENCE SQ_ALERT_CONFIG_SYSTEMS 
  START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

-- Secuencia necesaria para la clave de la tabla ALERT_RESUME_TYPES.
CREATE SEQUENCE SQ_ALERT_RESUME_TYPES START WITH 1 
  MAXVALUE 999999999999999999999999999 
  MINVALUE 1 
  NOCYCLE 
  CACHE 20 
  ORDER;

