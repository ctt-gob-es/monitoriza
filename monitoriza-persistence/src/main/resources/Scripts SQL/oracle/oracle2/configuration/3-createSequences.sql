-- ********************************************************
-- ************** Creación de Secuencias ******************
-- ********************************************************

-- Secuencia necesaria para la clave de la tabla USER_MONITORIZA.
CREATE SEQUENCE SQ_USER_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
 -- Secuencia necesaria para la clave de la tabla ALARM_MONITORIZA.
CREATE SEQUENCE SQ_ALARM_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
 
 -- Secuencia necesaria para la clave de la tabla MAIL_MONITORIZA.
CREATE SEQUENCE SQ_MAIL_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
 -- Secuencia necesaria para la clave de la tabla PLATFORM_AFIRMA.
CREATE SEQUENCE SQ_PLATFORM_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla SERVICE_MONITORIZA.
CREATE SEQUENCE SQ_SERVICE_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
    -- Secuencia necesaria para la clave de la tabla TIMER_MONITORIZA.
CREATE SEQUENCE SQ_TIMER_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
     -- Secuencia necesaria para la clave de la tabla TIMER_MONITORIZA.
CREATE SEQUENCE SQ_R_ALARM_MAIL START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla CERTIFICATES
CREATE SEQUENCE SQ_CERTIFICATES START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
 -- Secuencia necesaria para la clave de la tabla SYSTEM_CERTIFICATE.
CREATE SEQUENCE SQ_SYSTEM_CERTIFICATE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
 
  -- Secuencia necesaria para la clave de la tabla R_ALARM_MAIL_DEGRADED.
CREATE SEQUENCE SQ_R_ALARM_MAIL_DEGRADED START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla R_ALARM_MAIL_DOWN.
CREATE SEQUENCE SQ_R_ALARM_MAIL_DOWN START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
-- Secuencia necesaria para la clave de la tabla C_STATUS_CERTIFICATES.
CREATE SEQUENCE SQ_C_STATUS_CERTIFICATES START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla TIMER_SCHEDULED.
CREATE SEQUENCE SQ_TIMER_SCHEDULED START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;

  -- Secuencia necesaria para la clave de la tabla C_AUTHENTICATION_TYPE.
CREATE SEQUENCE SQ_C_AUTHENTICATION_TYPE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
-- Secuencia necesaria para la clave de la tabla VALID_SERVICE.
CREATE SEQUENCE SQ_VALID_SERVICE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
    
-- Secuencia necesaria para la clave de la tabla TIMER_SCHEDULED.
CREATE SEQUENCE SQ_REQUEST_SERVICE_FILE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
-- Secuencia necesaria para la clave de la tabla NODE_MONITORIZA.
CREATE SEQUENCE SQ_NODE_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
-- Secuencia necesaria para la clave de la tabla CONF_SERVER_MAIL.
CREATE SEQUENCE SQ_CONF_SERVER_MAIL START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
-- Secuencia necesaria para la clave de la tabla CONF_SERVER_MAIL.
CREATE SEQUENCE SQ_METHOD_VALIDATION START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
-- Secuencia necesaria para la clave de la tabla CONF_SERVER_MAIL.
CREATE SEQUENCE SQ_CONF_SPIE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
 -- Secuencia necesaria para la clave de la tabla SPIE_SCHEDULED.
CREATE SEQUENCE SQ_SPIE_SCHEDULED START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla SPIE_TYPE.
CREATE SEQUENCE SQ_SPIE_TYPE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla DAILY_VIP_MONITORING
CREATE SEQUENCE SQ_DAILY_VIP_MONITORING START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
   -- Secuencia necesaria para la clave de la tabla VIP_STATISTICS
CREATE SEQUENCE SQ_VIP_STATISTICS START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
    -- Secuencia necesaria para la clave de la tabla DAILY_SPIE_MONITORING
CREATE SEQUENCE SQ_DAILY_SPIE_MONITORING START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
     -- Secuencia necesaria para la clave de la tabla SPIE_STATISTICS
CREATE SEQUENCE SQ_SPIE_STATISTICS START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
    -- Secuencia necesaria para la clave de la tabla SQ_SYSTEM_NOTIFICATION
CREATE SEQUENCE SQ_SYSTEM_NOTIFICATION START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
  -- Secuencia necesaria para la clave de la tabla SQ_MAINTENANCE_SERVICE
CREATE SEQUENCE SQ_MAINTENANCE_SERVICE START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;
  
   -- Secuencia necesaria para la clave de la tabla SPL_MONITORIZA
CREATE SEQUENCE SQ_SPL_MONITORIZA START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 ORDER;

  