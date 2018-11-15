-- **********************************************************************
-- ************* Creación del Usuario de Configuración ******************
-- **********************************************************************

CREATE USER "MONIT_CONFIG"  PROFILE "DEFAULT" 
    IDENTIFIED BY "12345" DEFAULT TABLESPACE "MONIT_CONFIGURACION_TABLESPACE" 
        ACCOUNT UNLOCK;

GRANT INSERT ON "MONIT_CONFIGOWNER"."USER_MONITORIZA" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."USER_MONITORIZA" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."USER_MONITORIZA" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."USER_MONITORIZA" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_USER_MONITORIZA" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."ALARM_MONITORIZA" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."ALARM_MONITORIZA" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."ALARM_MONITORIZA" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."ALARM_MONITORIZA" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_ALARM_MONITORIZA" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."MAIL_MONITORIZA" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."MAIL_MONITORIZA" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."MAIL_MONITORIZA" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."MAIL_MONITORIZA" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_MAIL_MONITORIZA" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."PLATFORM_MONITORIZA" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."PLATFORM_MONITORIZA" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."PLATFORM_MONITORIZA" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."PLATFORM_MONITORIZA" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_PLATFORM_MONITORIZA" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."SERVICE_MONITORIZA" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."SERVICE_MONITORIZA" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."SERVICE_MONITORIZA" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."SERVICE_MONITORIZA" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_SERVICE_MONITORIZA" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."TIMER_MONITORIZA" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."TIMER_MONITORIZA" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."TIMER_MONITORIZA" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."TIMER_MONITORIZA" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_TIMER_MONITORIZA" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."KEYSTORE" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."KEYSTORE" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."KEYSTORE" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."KEYSTORE" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."SYSTEM_CERTIFICATE" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."SYSTEM_CERTIFICATE" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."SYSTEM_CERTIFICATE" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."SYSTEM_CERTIFICATE" TO "MONIT_CONFIG";


GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_SYSTEM_CERTIFICATE" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DEGRADED" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DEGRADED" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DEGRADED" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DEGRADED" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_R_ALARM_MAIL_DEGRADED" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DOWN" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DOWN" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DOWN" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."R_ALARM_MAIL_DOWN" TO "MONIT_CONFIG";

GRANT SELECT,ALTER ON "MONIT_CONFIGOWNER"."SQ_R_ALARM_MAIL_DOWN" TO "MONIT_CONFIG";

GRANT INSERT ON "MONIT_CONFIGOWNER"."C_PLATFORM_TYPE" TO "MONIT_CONFIG";
GRANT SELECT ON "MONIT_CONFIGOWNER"."C_PLATFORM_TYPE" TO "MONIT_CONFIG";
GRANT UPDATE ON "MONIT_CONFIGOWNER"."C_PLATFORM_TYPE" TO "MONIT_CONFIG";
GRANT DELETE ON "MONIT_CONFIGOWNER"."C_PLATFORM_TYPE" TO "MONIT_CONFIG";



GRANT CREATE SYNONYM TO "MONIT_CONFIG";
GRANT "CONNECT" TO "MONIT_CONFIG";

COMMIT;