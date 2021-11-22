﻿-- ********************************************************
-- **************** Creación de Tablas ********************
-- ********************************************************

-- ****************** TABLAS DE CONFIGURACIÓN *****************

-- Table USER_MONITORIZA
CREATE TABLE "USER_MONITORIZA"(
  "ID_USER_MONITORIZA" Number(19,0) NOT NULL,
  "LOGIN" Varchar2(100) NOT NULL,
  "PASSWORD" Varchar2(150) NOT NULL,
  "NAME" Varchar2(100) NOT NULL,
  "SURNAMES" Varchar2(150) NOT NULL,
  "EMAIL" Varchar2(150) NOT NULL,
  "IS_BLOCKED" Char(1) NOT NULL,
  "ATTEMPTS_NUMBER" Integer NOT NULL,
  "LAST_ACCESS" Timestamp(6),
  "IP_LAST_ACCESS" Varchar2(15)
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "USER_MONITORIZA" ADD CONSTRAINT "ID_USER_MONITORIZA" PRIMARY KEY ("ID_USER_MONITORIZA");
ALTER TABLE "USER_MONITORIZA" ADD CONSTRAINT "USER_UNIQUE_LOGIN" UNIQUE ("LOGIN");
COMMENT ON TABLE "USER_MONITORIZA" IS 'Tabla que almacena toda la información relativa a usuarios del sistema.';
COMMENT ON COLUMN "USER_MONITORIZA"."ID_USER_MONITORIZA" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "USER_MONITORIZA"."LOGIN" IS 'Valor que representa el nombre de acceso a la plataforma.';
COMMENT ON COLUMN "USER_MONITORIZA"."PASSWORD" IS 'Valor que representa el hash de la contraseña de acceso a la plataforma.';
COMMENT ON COLUMN "USER_MONITORIZA"."NAME" IS 'Valor que representa el nombre del usuario.';
COMMENT ON COLUMN "USER_MONITORIZA"."SURNAMES" IS 'Valor que representa los apellidos del usuario.';
COMMENT ON COLUMN "USER_MONITORIZA"."EMAIL" IS 'Valor que representa el e-mail del usuario.';
COMMENT ON COLUMN "USER_MONITORIZA"."IS_BLOCKED" IS 'Valor que indica si el usuario está bloqueado (Y) o no (N).';
COMMENT ON COLUMN "USER_MONITORIZA"."ATTEMPTS_NUMBER" IS 'Valor que representa el número de intentos fallidos de acceso a la plataforma que lleva acumulados el usuario desde la última vez que accedió correctamente.';
COMMENT ON COLUMN "USER_MONITORIZA"."LAST_ACCESS" IS 'Valor que representa la fecha del último acceso del usuario a la plataforma.';
COMMENT ON COLUMN "USER_MONITORIZA"."IP_LAST_ACCESS" IS 'Valor que representa la dirección IP desde la que accedió el usuario por última vez.';

-- Table TIMER_MONITORIZA
CREATE TABLE "TIMER_MONITORIZA"(
	"ID_TIMER" Number(19,0) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"FREQUENCY" Number(19,0) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "TIMER_MONITORIZA" ADD CONSTRAINT "ID_TIMER" PRIMARY KEY ("ID_TIMER");
ALTER TABLE "TIMER_MONITORIZA" ADD CONSTRAINT "TIMER_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "TIMER_MONITORIZA" IS 'Tabla que almacena la información relativa a los timers que determinan la frecuencia de ejecución de las peticiones a servicios de @Firma y TS@.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."ID_TIMER" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."NAME" IS 'Valor que representa el nombre único del timer.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."FREQUENCY" IS 'Valor que representa el valor en milisegundos de la frecuencia de ejecución de las peticiones asociadas al timer.';

-- Table C_PLATFORM_TYPE
CREATE TABLE "C_PLATFORM_TYPE" ( 
	"ID_PLATFORM_TYPE" NUMBER(19) NOT NULL,
	"NAME" Varchar2(100) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "C_PLATFORM_TYPE" ADD CONSTRAINT "ID_PLATFORM_TYPE" PRIMARY KEY ("ID_PLATFORM_TYPE");
COMMENT ON TABLE "C_PLATFORM_TYPE" IS 'Tabla que almacena los tipos de plataformas en Monitoriz@: @Firma o TS@.';

-- Table PLATFORM_AFIRMA
CREATE TABLE "PLATFORM_MONITORIZA" ( 
	"ID_PLATFORM" NUMBER(19) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"HOST" Varchar2(100) NOT NULL,
	"PORT" Varchar2(10) NOT NULL,
	"IS_SECURE" Char(1) NOT NULL,
	"HTTPS_PORT" Varchar2(10),
	"SERVICE_CONTEXT" Varchar2(100) NOT NULL,
	"ID_PLATFORM_TYPE" NUMBER(19) NOT NULL,
	"OCSP_CONTEXT" Varchar2(100),
	"RFC3161_CONTEXT" Varchar2(100),
	"RFC3161_PORT" NUMBER(10),
	"RFC3161_USE_AUTH" Char(1) NOT NULL,
	"RFC3161_AUTH_CERTIFICATE" NUMBER(19)
	)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_PLATFORM_CONS" PRIMARY KEY ("ID_PLATFORM");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "PLATFORM_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "PLATFORM_MONITORIZA" IS 'Tabla que almacena la información relativa a la conexión con plataformas @Firma/TS@.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."ID_PLATFORM" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."NAME" IS 'Valor que representa el nombre único de la plataforma.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."HOST" IS 'Valor que representa el host para la conexión con la plataforma.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."PORT" IS 'Valor que representa el puerto para la conexión con la plataforma.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."SERVICE_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso a los servicios web SOAP.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."OCSP_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso al servicio OCSP.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."RFC3161_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso al servicio RFC3161.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."RFC3161_PORT" IS 'Valor que representa el puerto de acceso al servicio RFC3161.';
COMMENT ON COLUMN "PLATFORM_MONITORIZA"."RFC3161_AUTH_CERTIFICATE" IS 'Valor que representa el identificador del certificado para la conexión autenticada con el servicio RFC3161.';

-- Table SERVICE_MONITORIZA
CREATE TABLE "SERVICE_MONITORIZA" (
	"ID_SERVICE" Number(19,0) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"SERVICE_TIMEOUT" Number(19,0) NOT NULL,
	"NAME_ENDPOINT_WSDL" Varchar2(100) NOT NULL,
	"DEGRADED_THRESHOLD" Number(19,0) NOT NULL,
	"LOST_THRESHOLD" Number(19,0) NOT NULL,
	"ID_TIMER_SERVICE" Number(19,0) NOT NULL,
	"ID_ALARM_SERVICE" Number(19,0) NOT NULL,
	"ID_PLATFORM_SERVICE" Number(19,0),
	"SERVICE_TYPE" Varchar2(100) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "ID_SERVICE" PRIMARY KEY ("ID_SERVICE");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "SERVICE_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "SERVICE_MONITORIZA" IS 'Tabla que almacena la información relativa a los servicios sobre los cuales van a realizarse peticiones para conocer su estado.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."ID_SERVICE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."NAME" IS 'Valor que representa el nombre único del servicio.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."SERVICE_TIMEOUT" IS 'Valor en milisegundos que representa el tiempo máximo que puede transcurrir sin respuesta para que el servicio se considere "caído".';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."NAME_ENDPOINT_WSDL" IS 'Valor que representa la cadena de texto final del endpoint que hace referencia al servicio.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."DEGRADED_THRESHOLD" IS 'Valor que representa el tiempo máximo en milisegundos que pueden tardar de media las peticiones a este servicio antes de que sea considerado "degradado".';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."LOST_THRESHOLD" IS 'Valor que representa el tiempo máximo en milisegundos que pueden tardar de media las peticiones a este servicio antes de que sea considerado "perdido".';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."ID_TIMER_SERVICE" IS 'Identificador del timer asociad a este servicio.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."ID_ALARM_SERVICE" IS 'Identificador de la alarma asociada a este servicio.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."ID_PLATFORM_SERVICE" IS 'Identificador de la plataforma asociada a este servicio.';
COMMENT ON COLUMN "SERVICE_MONITORIZA"."SERVICE_TYPE" IS 'Tipo del servicio.';

-- Table ALARM_MONITORIZA
CREATE TABLE "ALARM_MONITORIZA" (
	"ID_ALARM" Number(19,0) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"BLOCKED_TIME" Number(19,0) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "ALARM_MONITORIZA" ADD CONSTRAINT "ID_ALARM" PRIMARY KEY ("ID_ALARM");
ALTER TABLE "ALARM_MONITORIZA" ADD CONSTRAINT "ALARM_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "ALARM_MONITORIZA" IS 'Tabla que almacena la información relativa a las alarmas de monitoriz@.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."ID_ALARM" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."NAME" IS 'Valor que representa el nombre único de la alarma.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."BLOCKED_TIME" IS 'Valor que representa el tiempo en milisegundos que se bloquean las alarmas de este tipo antes de lanzar un resumen.';

-- Table MAIL_MONITORIZA
CREATE TABLE "MAIL_MONITORIZA" (
	"ID_MAIL" Number(19,0) NOT NULL,
	"EMAIL_ADDRESS" Varchar2(150) NOT NULL
	--"ID_ALARM_MAIL" Number(19,0) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "ID_MAIL" PRIMARY KEY ("ID_MAIL");
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "MAIL_ADDRESS_UNIQUE" UNIQUE ("EMAIL_ADDRESS");
COMMENT ON TABLE "MAIL_MONITORIZA" IS 'Tabla que almacena las direcciones de correo electrónico a utilizar por las alarmas de monitoriz@.';
COMMENT ON COLUMN "MAIL_MONITORIZA"."ID_MAIL" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "MAIL_MONITORIZA"."EMAIL_ADDRESS" IS 'Valor que representa una dirección de correo electrónico.';

-- Table KEYSTORE
CREATE TABLE "KEYSTORE"(
  "ID_KEYSTORE" Number(19,0) NOT NULL,
  "NAME" Varchar2(50) NOT NULL,
  "KEYSTORE" Blob,
  "TOKEN_NAME" Varchar2(30) NOT NULL,
  "PASSWORD" Varchar2(255 ) NOT NULL,
  "KEYSTORE_TYPE" Varchar2(50 ) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "KEYSTORE" ADD CONSTRAINT "ID_KEYSTORE" PRIMARY KEY ("ID_KEYSTORE");
ALTER TABLE "KEYSTORE" ADD CONSTRAINT "KEYSTORE_UNIQUE_NAME" UNIQUE ("NAME");
COMMENT ON TABLE "KEYSTORE" IS 'Tabla que almacena toda la información relativa a almacenes de certificados.';
COMMENT ON COLUMN "KEYSTORE"."ID_KEYSTORE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "KEYSTORE"."NAME" IS 'Valor que representa el nombre del almacén de claves.';
COMMENT ON COLUMN "KEYSTORE"."KEYSTORE" IS 'Valor que representa el almacén de claves como archivo físico.';
COMMENT ON COLUMN "KEYSTORE"."TOKEN_NAME" IS 'Valor que representa el token con la descripción almacenada en cada archivo de propiedades por idioma.';
COMMENT ON COLUMN "KEYSTORE"."PASSWORD" IS 'Valor que representa la contraseña de acceso al almacén de claves.';
COMMENT ON COLUMN "KEYSTORE"."KEYSTORE_TYPE" IS 'Valor que representa el tipo del almacén de claves.';

-- Table SYSTEM_CERTIFICATE
CREATE TABLE "SYSTEM_CERTIFICATE"(
  "ID_SYSTEM_CERTIFICATE" Number(19,0) NOT NULL,
  "ALIAS" Varchar2(4000) NOT NULL,
  "ID_KEYSTORE" Number(19,0) NOT NULL,
  "IS_KEY" Char(1) NOT NULL,
  "ISSUER" Varchar2(4000) NOT NULL,
  "SUBJECT" Varchar2(4000) NOT NULL
)
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_SYSTEM_CERTIFICATE" PRIMARY KEY ("ID_SYSTEM_CERTIFICATE");
COMMENT ON TABLE "SYSTEM_CERTIFICATE" IS 'Tabla que almacena toda la información relativa a certificados de uso por el sistema.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ID_SYSTEM_CERTIFICATE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ALIAS" IS 'Valor que representa el alias del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ID_KEYSTORE" IS 'Valor que representa el almacén de claves donde se encuentra almacenado el certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."IS_KEY" IS 'Valor que indica si el alias hace referencia a una clave (Y) o no (N).';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ISSUER" IS 'Valor que representa el emisor del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."SUBJECT" IS 'Valor que representa el asunto del certificado.';

-- Table R_ALARM_MAIL_DEGRADED
CREATE TABLE "R_ALARM_MAIL_DEGRADED"(
  "ID_MAIL" Number(19,0) NOT NULL, 
  "ID_ALARM" Number(19,0) NOT NULL 
) 
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
COMMENT ON COLUMN "R_ALARM_MAIL_DEGRADED"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL_DEGRADED"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';
ALTER TABLE "R_ALARM_MAIL_DEGRADED" ADD CONSTRAINT "R_ALARM_MAIL_DEGRADED" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL_DEGRADED" ADD CONSTRAINT "R_MAIL_ALARM_DEGRADED" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");

-- Table R_ALARM_MAIL_DOWN
CREATE TABLE "R_ALARM_MAIL_DOWN"(
  "ID_MAIL" Number(19,0) NOT NULL, 
  "ID_ALARM" Number(19,0) NOT NULL 
) 
TABLESPACE MONIT_CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
COMMENT ON COLUMN "R_ALARM_MAIL_DOWN"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL_DOWN"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';
ALTER TABLE "R_ALARM_MAIL_DOWN" ADD CONSTRAINT "R_ALARM_MAIL_DOWN" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL_DOWN" ADD CONSTRAINT "R_MAIL_ALARM_DOWN" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");

ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_TIMER_SERVICE" FOREIGN KEY ("ID_TIMER_SERVICE") REFERENCES "TIMER_MONITORIZA" ("ID_TIMER");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_ALARM_SERVICE" FOREIGN KEY ("ID_ALARM_SERVICE") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_PLATFORM_SERVICE" FOREIGN KEY ("ID_PLATFORM_SERVICE") REFERENCES "PLATFORM_MONITORIZA" ("ID_PLATFORM");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_PLATFORM_TYPE_FK" FOREIGN KEY ("ID_PLATFORM_TYPE") REFERENCES "C_PLATFORM_TYPE" ("ID_PLATFORM_TYPE");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_SYSTEM_CERTIFICATE_FK" FOREIGN KEY ("RFC3161_AUTH_CERTIFICATE") REFERENCES "SYSTEM_CERTIFICATE" ("ID_SYSTEM_CERTIFICATE") ;
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "R_SC_K" FOREIGN KEY ("ID_KEYSTORE") REFERENCES "KEYSTORE" ("ID_KEYSTORE");

CREATE TABLE "SPL_MONITORIZA" ( 
	"ID_SPL" NUMERIC(2) NOT NULL,
	"NAME" VARCHAR(30) NOT NULL,
	"DESCRIPTION" VARCHAR(255) NOT NULL,
	"TYPE" VARCHAR(30) NOT NULL,
	"URL" VARCHAR(255) NOT NULL,
	"KEY" VARCHAR(100) NOT NULL
	);
ALTER TABLE "SPL_MONITORIZA" ADD CONSTRAINT "ID_SPL_CONS" PRIMARY KEY ("ID_SPL");

COMMENT ON TABLE "SPL_MONITORIZA" IS 'Tabla que almacena la información relativa a los estados de los servicios SPIE monitorizados en un momento determinado.';
COMMENT ON COLUMN "SPL_MONITORIZA"."ID_SPL" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SPL_MONITORIZA"."NAME" IS 'Valor que representa el nombre del log.';
COMMENT ON COLUMN "SPL_MONITORIZA"."DESCRIPTION" IS 'Valor que representa la descripción de los logs.';
COMMENT ON COLUMN "SPL_MONITORIZA"."TYPE" IS 'Valor que representa el tipo de logs.';
COMMENT ON COLUMN "SPL_MONITORIZA"."URL" IS 'Valor que representa la url que conecta con el servidor.';
COMMENT ON COLUMN "SPL_MONITORIZA"."KEY" IS 'Valor que indica si el alias hace referencia a una clave (Y) o no (N).';

--MODELO DE DATOS DE OPERACION PARA ALERTAS DE APLICACION

-- Table ALERT_APP_TEMPLATES
CREATE TABLE "ALERT_APP_TEMPLATES" ( 
	"TEMPLATE_ID" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(40) NOT NULL
	);
	
ALTER TABLE "ALERT_APP_TEMPLATES" ADD CONSTRAINT "TEMPLATE_ID_CONS" PRIMARY KEY ("TEMPLATE_ID");
COMMENT ON TABLE "ALERT_APP_TEMPLATES" IS 'Datos sobre las plantillas que contienen un conjunto de alarmas';
COMMENT ON COLUMN "ALERT_APP_TEMPLATES"."TEMPLATE_ID" IS 'Identificador de la plantilla.';
COMMENT ON COLUMN "ALERT_APP_TEMPLATES"."NAME" IS 'Nombre asignado a la plantilla.';

-- Table ALERT_APPLICATIONS
CREATE TABLE "ALERT_APPLICATIONS" ( 
	"APP_ID" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(40) NOT NULL,
	"APP_KEY" VARCHAR(20) ,
	"CIPHER_KEY" VARCHAR(45) ,
	"TEMPLATE_ID" NUMERIC(19),
	"RESPONSIBLE_NAME" VARCHAR(45) NOT NULL,
	"RESPONSIBLE_EMAIL" VARCHAR(45) NOT NULL,
	"RESPONSIBLE_PHONE" VARCHAR(24) NOT NULL,
	"ENABLED" CHAR(1) NOT NULL
	);
	
ALTER TABLE "ALERT_APPLICATIONS" ADD CONSTRAINT "APP_ID_CONS" PRIMARY KEY ("APP_ID");
ALTER TABLE "ALERT_APPLICATIONS" ADD CONSTRAINT "ALERT_APP_TEMPLATE_ID_FK" FOREIGN KEY ("TEMPLATE_ID") REFERENCES "ALERT_APP_TEMPLATES" ("TEMPLATE_ID");
COMMENT ON TABLE "ALERT_APPLICATIONS" IS 'Datos de las aplicaciones que enviarÃ¡n alertas al sistema.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."APP_ID" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."NAME" IS 'Nombre de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."APP_KEY" IS 'Cadena que deberÃ¡ indicar la aplicaciÃ³n para identificarse frente al sistema.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."CIPHER_KEY" IS 'Cadena que se usarÃ¡ como contraseÃ±a base para derivar la clave de cifrado.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."TEMPLATE_ID" IS 'Identificador de la plantilla que define las alarmas que puede recibir.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."RESPONSIBLE_NAME" IS 'Nombre del responsable de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."RESPONSIBLE_EMAIL" IS 'DirecciÃ³n de correo electrÃ³nico del responsable de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."RESPONSIBLE_PHONE" IS 'NÃºmero de telÃ©fono del responsable de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_APPLICATIONS"."RESPONSIBLE_PHONE" IS 'Indica si la aplicaciÃ³n estÃ¡ activa o no';

-- Table ALERT_TYPES
CREATE TABLE "ALERT_TYPES" ( 
	"TYPE_ID" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(40) NOT NULL,
	"DESCRIPTION" VARCHAR(45)
	);
	
ALTER TABLE "ALERT_TYPES" ADD CONSTRAINT "TYPE_ID_CONS" PRIMARY KEY ("TYPE_ID");
COMMENT ON TABLE "ALERT_TYPES" IS 'Datos sobre los tipos de alertas dadas de alta en el sistema.';
COMMENT ON COLUMN "ALERT_TYPES"."TYPE_ID" IS 'Identificador del tipo la alerta.';
COMMENT ON COLUMN "ALERT_TYPES"."NAME" IS 'Nombre con el cual se recibirÃ¡ la alerta.';
COMMENT ON COLUMN "ALERT_TYPES"."DESCRIPTION" IS 'DescripciÃ³n de la alerta.';

-- Table ALERT_TEMPLATE_TYPES
CREATE TABLE "ALERT_TEMPLATE_TYPES" ( 
	"TEMPLATE_ID" NUMERIC(19) NOT NULL,
	"TYPE_ID" NUMERIC(19) NOT NULL
	);
	
ALTER TABLE "ALERT_TEMPLATE_TYPES" ADD CONSTRAINT "TEMPLATE_ID-TYPE_ID_CONS" PRIMARY KEY ("TEMPLATE_ID", "TYPE_ID");
ALTER TABLE "ALERT_TEMPLATE_TYPES" ADD CONSTRAINT "TEMPLATE_ID_FK" FOREIGN KEY ("TEMPLATE_ID") REFERENCES "ALERT_APP_TEMPLATES" ("TEMPLATE_ID");
ALTER TABLE "ALERT_TEMPLATE_TYPES" ADD CONSTRAINT "TYPE_ID_FK" FOREIGN KEY ("TYPE_ID") REFERENCES "ALERT_TYPES" ("TYPE_ID");
COMMENT ON TABLE "ALERT_TEMPLATE_TYPES" IS 'Datos sobre la relaciÃ³n de las alertas que pertenecen a cada plantilla';
COMMENT ON COLUMN "ALERT_TEMPLATE_TYPES"."TEMPLATE_ID" IS 'Identificador de la plantilla a la que pertenece la alerta. Corresponde al campo template_ID de la tabla Alert_App_Templates.';
COMMENT ON COLUMN "ALERT_TEMPLATE_TYPES"."TYPE_ID" IS 'Identificador del tipo la alerta. Corresponde al campo type_ID de la tabla Alert_Type.';

-- Table ALERT_CONFIG
CREATE TABLE "ALERT_CONFIG" ( 
	"ALERT_CONFIG_ID" NUMERIC(19) NOT NULL,
	"TYPE_ID" NUMERIC(19) NOT NULL,
	"APP_ID" NUMERIC(19) NOT NULL,
	"ID_SEVERITY_TYPE" NUMERIC(19) NOT NULL,
	"ENABLE" CHAR(1) NOT NULL,
	"ALLOW_BLOCK" CHAR(1) NOT NULL,
	"BLOCK_CONDITION" NUMERIC(19),
	"BLOCK_INTERVAL" NUMERIC(19),
	"BLOCK_PERIOD" NUMERIC(19),
	"BLOCK_TIME" DATE,
	"LAST_TIME" DATE
	);
	
ALTER TABLE "ALERT_CONFIG" ADD CONSTRAINT "ALERT_CONFIG_ID_CONS" PRIMARY KEY ("ALERT_CONFIG_ID");
ALTER TABLE "ALERT_CONFIG" ADD CONSTRAINT "ALERT_CONFIG_TYPE_ID_FK" FOREIGN KEY ("TYPE_ID") REFERENCES "ALERT_TYPES" ("TYPE_ID");
ALTER TABLE "ALERT_CONFIG" ADD CONSTRAINT "APP_ID_FK" FOREIGN KEY ("APP_ID") REFERENCES "ALERT_APPLICATIONS" ("APP_ID");
COMMENT ON TABLE "ALERT_CONFIG" IS 'Datos sobre la configuraciÃ³n asociada a una alerta.';
COMMENT ON COLUMN "ALERT_CONFIG"."ALERT_CONFIG_ID" IS 'Identificador de la configuraciÃ³n.';
COMMENT ON COLUMN "ALERT_CONFIG"."TYPE_ID" IS 'Tipo de alerta a la que corresponde la configuraciÃ³n.';
COMMENT ON COLUMN "ALERT_CONFIG"."APP_ID" IS 'Identificador de la aplicaciÃ³n en la que se ha configurado esta alerta.';
COMMENT ON COLUMN "ALERT_CONFIG"."ID_SEVERITY_TYPE" IS 'Nombre del nivel de criticidad asociado a la alerta.';
COMMENT ON COLUMN "ALERT_CONFIG"."ENABLE" IS 'Indica si se encuentra activada la alerta o no.';
COMMENT ON COLUMN "ALERT_CONFIG"."ALLOW_BLOCK" IS 'Indica si la alerta debe bloquearse cuando se cumplan los requisitos de bloqueo.';
COMMENT ON COLUMN "ALERT_CONFIG"."BLOCK_CONDITION" IS 'NÃºmero de alertas de este tipo que deben recibirse para que se bloquee esta alerta.';
COMMENT ON COLUMN "ALERT_CONFIG"."BLOCK_INTERVAL" IS 'Intervalo de tiempo mÃ¡ximo que deben espaciarse las alertas para que contabilicen para la condiciÃ³n de bloqueo.';
COMMENT ON COLUMN "ALERT_CONFIG"."BLOCK_PERIOD" IS 'Periodo de tiempo durante el cual se mantendrÃ¡ bloqueada la alerta si se cumplen las condiciones de bloqueo.';
COMMENT ON COLUMN "ALERT_CONFIG"."BLOCK_TIME" IS 'Instante del tiempo en el que se bloqueo la alerta.';
COMMENT ON COLUMN "ALERT_CONFIG"."LAST_TIME" IS 'Instante del tiempo en el que se recibiÃ³ la Ãºltima alerta de este tipo.';

-- Table ALERT_SEVERITY
CREATE TABLE "ALERT_SEVERITY" ( 
	"ID_SEVERITY_TYPE" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(30) NOT NULL
	);
	
ALTER TABLE "ALERT_SEVERITY" ADD CONSTRAINT "ID_SEVERITY_TYPE_CONS" PRIMARY KEY ("ID_SEVERITY_TYPE");
ALTER TABLE "ALERT_SEVERITY" ADD CONSTRAINT "ID_SEVERITY_TYPE_FK" FOREIGN KEY ("ID_SEVERITY_TYPE") REFERENCES "ALERT_CONFIG" ("ID_SEVERITY_TYPE");
COMMENT ON TABLE "ALERT_SEVERITY" IS 'Datos sobre los distintos niveles de criticidad para una alerta.';
COMMENT ON COLUMN "ALERT_SEVERITY"."ID_SEVERITY_TYPE" IS 'Identificador del nivel.';
COMMENT ON COLUMN "ALERT_SEVERITY"."NAME" IS 'Nombre asignado al nivel de criticidad.';

-- Table ALERT_SYSTEMS
CREATE TABLE "ALERT_SYSTEMS" ( 
	"SYSTEM_ID" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(30) NOT NULL,
	"TYPE" VARCHAR(15) NOT NULL
	);
	
ALTER TABLE "ALERT_SYSTEMS" ADD CONSTRAINT "SYSTEM_ID_CONS" PRIMARY KEY ("SYSTEM_ID");
COMMENT ON TABLE "ALERT_SYSTEMS" IS 'Datos sobre los sistemas de emisiÃ³n de notificaciones.';
COMMENT ON COLUMN "ALERT_SYSTEMS"."SYSTEM_ID" IS 'Identificador del sistema de notificaciÃ³n.';
COMMENT ON COLUMN "ALERT_SYSTEMS"."NAME" IS 'Nombre asignado al sistema de notificaciÃ³n.';
COMMENT ON COLUMN "ALERT_SYSTEMS"."TYPE" IS 'Cadena con el tipo de sistema de notificaciÃ³n. ';

-- Table ALERT_GRAYLOG_SYSTEM_CONFIG
CREATE TABLE "ALERT_GRAYLOG_SYSTEM_CONFIG" ( 
	"SYSTEM_ID" NUMERIC(19) NOT NULL,
	"HOST" VARCHAR(45) NOT NULL,
	"PORT" NUMERIC(19)
	);
	
ALTER TABLE "ALERT_GRAYLOG_SYSTEM_CONFIG" ADD CONSTRAINT "SYSTEM_ID_CONS" PRIMARY KEY ("SYSTEM_ID");
ALTER TABLE "ALERT_GRAYLOG_SYSTEM_CONFIG" ADD CONSTRAINT "SYSTEM_ID_FK" FOREIGN KEY ("SYSTEM_ID") REFERENCES "ALERT_SYSTEMS" ("SYSTEM_ID");
COMMENT ON TABLE "ALERT_GRAYLOG_SYSTEM_CONFIG" IS 'Datos sobre la configuraciÃ³n asignada a uno de los sistemas de notificaciÃ³n dados de alta para la notificaciÃ³n de una alerta.';
COMMENT ON COLUMN "ALERT_GRAYLOG_SYSTEM_CONFIG"."SYSTEM_ID" IS 'Identificador del sistema de notificaciÃ³n.';
COMMENT ON COLUMN "ALERT_GRAYLOG_SYSTEM_CONFIG"."HOST" IS 'Cadena de host (IP o nombre de dominio) del sistema Graylog al que enviar las notificaciones.';
COMMENT ON COLUMN "ALERT_GRAYLOG_SYSTEM_CONFIG"."PORT" IS 'NÃºmero de puerto en el que Graylog recibe las notificaciones.';

-- Table ALERT_CONFIG_SYSTEMS
CREATE TABLE "ALERT_CONFIG_SYSTEMS" ( 
	"NOT_SYS_CONFIG_ID" NUMERIC(19) NOT NULL,
	"ALERT_CONFIG_ID" NUMERIC(19) NOT NULL,
	"SYSTEM_ID" NUMERIC(19) NOT NULL
	);
	
ALTER TABLE "ALERT_CONFIG_SYSTEMS" ADD CONSTRAINT "NOT_SYS_CONFIG_ID_CONS" PRIMARY KEY ("NOT_SYS_CONFIG_ID");
ALTER TABLE "ALERT_CONFIG_SYSTEMS" ADD CONSTRAINT "ALERT_CONFIG_ID_FK" FOREIGN KEY ("ALERT_CONFIG_ID") REFERENCES "ALERT_CONFIG" ("ALERT_CONFIG_ID");
ALTER TABLE "ALERT_CONFIG_SYSTEMS" ADD CONSTRAINT "ALERT_SYSTEM_ID_FK" FOREIGN KEY ("SYSTEM_ID") REFERENCES "ALERT_SYSTEMS" ("SYSTEM_ID");
COMMENT ON TABLE "ALERT_CONFIG_SYSTEMS" IS 'Tabla de relaciÃ³n que asocia una alerta concreta con el sistema de notificaciÃ³n concreto con el que se desea enviar.';
COMMENT ON COLUMN "ALERT_CONFIG_SYSTEMS"."NOT_SYS_CONFIG_ID" IS 'Identificador de la configuraciÃ³n del sistema de notificaciÃ³n para el envÃ­o de esa alerta concreta.';
COMMENT ON COLUMN "ALERT_CONFIG_SYSTEMS"."ALERT_CONFIG_ID" IS 'Identificador de la configuraciÃ³n de la alerta que se va a notificar.';
COMMENT ON COLUMN "ALERT_CONFIG_SYSTEMS"."SYSTEM_ID" IS 'Identificador del sistema de notificaciÃ³n que se configura.';

-- Table ALERT_MAIL_NOTICE_CONFIG
CREATE TABLE "ALERT_MAIL_NOTICE_CONFIG" ( 
	"NOT_SYS_CONFIG_ID" NUMERIC(19) NOT NULL,
	"MAIL" VARCHAR(45) NOT NULL
	);
	
ALTER TABLE "ALERT_MAIL_NOTICE_CONFIG" ADD CONSTRAINT "NOT_SYS_CONFIG_ID-MAIL_CONS" PRIMARY KEY ("NOT_SYS_CONFIG_ID" , "MAIL");
ALTER TABLE "ALERT_MAIL_NOTICE_CONFIG" ADD CONSTRAINT "ALERT_CONF_SYS_ID_FK" FOREIGN KEY ("NOT_SYS_CONFIG_ID") REFERENCES "ALERT_CONFIG_SYSTEMS" ("NOT_SYS_CONFIG_ID");
COMMENT ON TABLE "ALERT_MAIL_NOTICE_CONFIG" IS 'ConfiguraciÃ³n para el envÃ­o de una alerta concreta a travÃ©s de correo electrÃ³nico.';
COMMENT ON COLUMN "ALERT_MAIL_NOTICE_CONFIG"."NOT_SYS_CONFIG_ID" IS 'Identificador de la configuraciÃ³n del sistema de notificaciÃ³n para el envÃ­o de esa alerta concreta.';
COMMENT ON COLUMN "ALERT_MAIL_NOTICE_CONFIG"."MAIL" IS 'DirecciÃ³n de correo electrÃ³nico a la que enviar la alerta.';

-- Table ALERT_GRAYLOG_NOTICE_CONFIG
CREATE TABLE "ALERT_GRAYLOG_NOTICE_CONFIG" ( 
	"NOT_SYS_CONFIG_ID" NUMERIC(19) NOT NULL,
	"PKEY" VARCHAR(45) NOT NULL,
	"VALUE" VARCHAR(255) NOT NULL
	);
	
ALTER TABLE "ALERT_GRAYLOG_NOTICE_CONFIG" ADD CONSTRAINT "NOT_SYS_CONFIG_ID-KEY_CONS" PRIMARY KEY ("NOT_SYS_CONFIG_ID" , "PKEY");
ALTER TABLE "ALERT_GRAYLOG_NOTICE_CONFIG" ADD CONSTRAINT "ALERT_CONFIG_SYS_ID_FK" FOREIGN KEY ("NOT_SYS_CONFIG_ID") REFERENCES "ALERT_CONFIG_SYSTEMS" ("NOT_SYS_CONFIG_ID");
COMMENT ON TABLE "ALERT_GRAYLOG_NOTICE_CONFIG" IS 'ConfiguraciÃ³n para el envÃ­o de una alerta concreta a un servicio Graylog.';
COMMENT ON COLUMN "ALERT_GRAYLOG_NOTICE_CONFIG"."NOT_SYS_CONFIG_ID" IS 'Identificador de la configuraciÃ³n del sistema de notificaciÃ³n para el envÃ­o de esa alerta concreta.';
COMMENT ON COLUMN "ALERT_GRAYLOG_NOTICE_CONFIG"."PKEY" IS 'Nombre de propiedad a asignar.';
COMMENT ON COLUMN "ALERT_GRAYLOG_NOTICE_CONFIG"."VALUE" IS 'Valor de la propiedad utilizar.';

-- Table ALERT_RESUMES
CREATE TABLE "ALERT_RESUMES" ( 
	"RESUME_ID" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(20) NOT NULL,
	"DESCRIPTION" VARCHAR(100) ,
	"PERIODICITY" NUMERIC(19) NOT NULL,
	"ENABLED" CHAR(1) NOT NULL
	);
	
ALTER TABLE "ALERT_RESUMES" ADD CONSTRAINT "RESUME_ID_CONS" PRIMARY KEY ("RESUME_ID");
COMMENT ON TABLE "ALERT_RESUMES" IS 'ResumÃ©n de peticiones que se debe enviar.';
COMMENT ON COLUMN "ALERT_RESUMES"."NAME" IS 'Nombre del resumen.';
COMMENT ON COLUMN "ALERT_RESUMES"."DESCRIPTION" IS 'DescripciÃ³n legible del resumen.';
COMMENT ON COLUMN "ALERT_RESUMES"."PERIODICITY" IS 'Identificador de la periodicidad con la que se quiere que se emita el resumen.';
COMMENT ON COLUMN "ALERT_RESUMES"."ENABLED" IS 'Indica si el resumen estÃ¡ activo o no.';

-- Table ALERT_RESUME_SYSTEMS
CREATE TABLE "ALERT_RESUME_SYSTEMS" ( 
	"RES_SYS_CONFIG_ID" NUMERIC(19) NOT NULL,
	"RESUME_ID" NUMERIC(19) NOT NULL,
	"SYSTEM_ID" NUMERIC(19) NOT NULL
	);
	
ALTER TABLE "ALERT_RESUME_SYSTEMS" ADD CONSTRAINT "RES_SYS_CONFIG_ID_CONS" PRIMARY KEY ("RES_SYS_CONFIG_ID");
ALTER TABLE "ALERT_RESUME_SYSTEMS" ADD CONSTRAINT "RESUME_ID_FK" FOREIGN KEY ("RESUME_ID") REFERENCES "ALERT_RESUMES" ("RESUME_ID");
ALTER TABLE "ALERT_RESUME_SYSTEMS" ADD CONSTRAINT "ALERT_SYSTEMS_ID_FK" FOREIGN KEY ("SYSTEM_ID") REFERENCES "ALERT_SYSTEMS" ("SYSTEM_ID");
COMMENT ON TABLE "ALERT_RESUME_SYSTEMS" IS 'Tabla de relaciÃ³n que asocia cada resumen con los sistemas de notificaciÃ³n activos a travÃ©s de los que se debe enviar.';
COMMENT ON COLUMN "ALERT_RESUME_SYSTEMS"."RES_SYS_CONFIG_ID" IS 'Identificador de la asociaciÃ³n entre un resumen y uno de sus sistemas de notificaciÃ³n.';
COMMENT ON COLUMN "ALERT_RESUME_SYSTEMS"."RESUME_ID" IS 'Identificador del resumen. Corresponde al campo resume_ID de la tabla Alert_resumes.';
COMMENT ON COLUMN "ALERT_RESUME_SYSTEMS"."SYSTEM_ID" IS 'Identificador del sistema de notificaciÃ³n. Corresponde al campo system_ID de la tabla Alert_Systems.';

-- Table ALERT_MAIL_RESUME_CONFIG
CREATE TABLE "ALERT_MAIL_RESUME_CONFIG" ( 
	"RES_SYS_CONFIG_ID" NUMERIC(19) NOT NULL,
	"MAIL"  VARCHAR(45) NOT NULL
	);
	
ALTER TABLE "ALERT_MAIL_RESUME_CONFIG" ADD CONSTRAINT "NOT_SYS_CONFIG_ID-MAIL_CONS" PRIMARY KEY ("NOT_SYS_CONFIG_ID" ,"MAIL");
ALTER TABLE "ALERT_MAIL_RESUME_CONFIG" ADD CONSTRAINT "NOT_SYS_CONFIG_ID_FK" FOREIGN KEY ("NOT_SYS_CONFIG_ID") REFERENCES "ALERT_RESUME_SYSTEMS" ("RES_SYS_CONFIG_ID");
COMMENT ON TABLE "ALERT_MAIL_RESUME_CONFIG" IS 'ConfiguraciÃ³n para el envÃ­o de un resumen concreto a travÃ©s de correo electrÃ³nico.';
COMMENT ON COLUMN "ALERT_MAIL_RESUME_CONFIG"."RES_SYS_CONFIG_ID" IS 'Identificador de la configuraciÃ³n del sistema de notificaciÃ³n para el envÃ­o de ese resumen concreto.';
COMMENT ON COLUMN "ALERT_MAIL_RESUME_CONFIG"."RESUME_ID" IS 'DirecciÃ³n de correo electrÃ³nico a la que enviar el resumen.';

-- Table ALERT_RESUME_LIST
CREATE TABLE "ALERT_RESUME_LIST" ( 
	"ALERT_ID" NUMERIC(19) NOT NULL,
	"ALERT_CONFIG_ID"  NUMERIC(19) NOT NULL,
	"RESUME_ID" NUMERIC(19) NOT NULL,
	"NODE" VARCHAR(40) NOT NULL,
	"TIMESTAMP" DATE NOT NULL
	);
	
ALTER TABLE "ALERT_RESUME_LIST" ADD CONSTRAINT "ALERT_ID_CONS" PRIMARY KEY ("ALERT_ID");
ALTER TABLE "ALERT_RESUME_LIST" ADD CONSTRAINT "ALERT_RESUME_LIST_ALERT_CONFIG_ID_FK" FOREIGN KEY ("ALERT_CONFIG_ID") REFERENCES "ALERT_CONFIG" ("ALERT_CONFIG_ID");
ALTER TABLE "ALERT_RESUME_LIST" ADD CONSTRAINT "ALERT_RESUME_LIST_RESUME_ID_FK" FOREIGN KEY ("RESUME_ID") REFERENCES "ALERT_RESUMES" ("RESUME_ID");
COMMENT ON TABLE "ALERT_RESUME_LIST" IS 'RelaciÃ³n entre resumenes y alertas.';
COMMENT ON COLUMN "ALERT_RESUME_LIST"."ALERT_ID" IS 'Identificador de la relaciÃ³n.';
COMMENT ON COLUMN "ALERT_RESUME_LIST"."ALERT_CONFIG_ID" IS 'Identificador de la configuraciÃ³n de la alerta que se recibiÃ³.';
COMMENT ON COLUMN "ALERT_RESUME_LIST"."RESUME_ID" IS 'Identificador del tipo de resumen.';
COMMENT ON COLUMN "ALERT_RESUME_LIST"."NODE" IS 'Nombre o direcciÃ³n del nodo desde el que se envÃ­a la alerta.';
COMMENT ON COLUMN "ALERT_RESUME_LIST"."TIMESTAMP" IS 'Fecha en la que se recibiÃ³ la alerta en cuestiÃ³n.';

-- Table ALERT_RESUME_TYPES
CREATE TABLE "ALERT_RESUME_TYPES" ( 
	"RES_TYPE_ID" NUMERIC(19) NOT NULL,
	"APP_ID"  NUMERIC(19) NOT NULL,
	"TYPE_ID" NUMERIC(19) NOT NULL,
	"RESUME_ID" NUMERIC(19) NOT NULL
	);
	
ALTER TABLE "ALERT_RESUME_TYPES" ADD CONSTRAINT "RES_TYPE_ID_CONS" PRIMARY KEY ("RES_TYPE_ID");
ALTER TABLE "ALERT_RESUME_TYPES" ADD CONSTRAINT "ALERT_RESUME_TYPES_APP_ID_FK" FOREIGN KEY ("APP_ID") REFERENCES "ALERT_APPLICATIONS" ("APP_ID");
ALTER TABLE "ALERT_RESUME_TYPES" ADD CONSTRAINT "ALERT_RESUME_TYPES_TYPE_ID_FK" FOREIGN KEY ("TYPE_ID") REFERENCES "ALERT_TYPES" ("TYPE_ID");
ALTER TABLE "ALERT_RESUME_TYPES" ADD CONSTRAINT "ALERT_RESUME_TYPES_RESUME_ID_FK" FOREIGN KEY ("RESUME_ID") REFERENCES "ALERT_RESUMES" ("RESUME_ID");
COMMENT ON TABLE "ALERT_RESUME_TYPES" IS 'RelaciÃ³n entre resÃºmenes y tipos de alerta.';
COMMENT ON COLUMN "ALERT_RESUME_TYPES"."RES_TYPE_ID" IS 'Identificador de la configuraciÃ³n del sistema de notificaciÃ³n para el envÃ­o de ese resumen concreto.';
COMMENT ON COLUMN "ALERT_RESUME_TYPES"."APP_ID" IS 'Identificador de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_RESUME_TYPES"."TYPE_ID" IS 'Identificador del tipo de alerta.';
COMMENT ON COLUMN "ALERT_RESUME_TYPES"."RESUME_ID" IS 'Identificador del tipo de resumen.';

--MODELO DE DATOS ESTADISTICOS PARA ALERTAS DE APLICACION

-- Table ALERT_STATISTICS_TEMP
CREATE TABLE "ALERT_AUDIT" ( 
	"ALERT_ID" NUMERIC(19) NOT NULL,
	"APP_NAME"  VARCHAR(40) NOT NULL,
	"ALERT_NAME" VARCHAR(40) NOT NULL,
	"APP_TEMPLATE_NAME" VARCHAR(40) NOT NULL,
	"NODE" VARCHAR(40) NOT NULL,
	"SEVERITY" VARCHAR(8) NOT NULL,
	"TIMESTAMP" DATE NOT NULL
	);
	
ALTER TABLE "ALERT_AUDIT" ADD CONSTRAINT "ALERT_AUDIT_ID_CONS" PRIMARY KEY ("ALERT_ID");
COMMENT ON TABLE "ALERT_AUDIT" IS 'Alertas almacenadas temporalmente para poderla contabilizar para la generaciÃ³n de los datos de explotaciÃ³n de estadÃ­sticas.';
COMMENT ON COLUMN "ALERT_AUDIT"."ALERT_ID" IS 'Identificador de la alerta.';
COMMENT ON COLUMN "ALERT_AUDIT"."APP_NAME" IS 'Nombre de la aplicaciÃ³n que emitiÃ³ la alerta.';
COMMENT ON COLUMN "ALERT_AUDIT"."ALERT_NAME" IS 'Nombre de la alerta recibida.';
COMMENT ON COLUMN "ALERT_AUDIT"."APP_TEMPLATE_NAME" IS 'Nombre de la plantilla de alertas en la que se basa la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_AUDIT"."NODE" IS 'Nombre del nodo que emitiÃ³ la alerta.';
COMMENT ON COLUMN "ALERT_AUDIT"."SEVERITY" IS 'Nivel de criticidad de la alerta.';
COMMENT ON COLUMN "ALERT_AUDIT"."TIMESTAMP" IS 'Instance del tiempo en el que se recibiÃ³ la alerta.';

-- Table ALERT_DIM_APPS
CREATE TABLE "ALERT_DIM_APPS" ( 
	"APP_ID" NUMERIC(19) NOT NULL,
	"APP_NAME"  VARCHAR(40) NOT NULL
	);
	
ALTER TABLE "ALERT_DIM_APPS" ADD CONSTRAINT "ALERT_DIM_APP_ID_CONS" PRIMARY KEY ("APP_ID");
COMMENT ON TABLE "ALERT_DIM_APPS" IS 'Tabla de dimensiÃ³n del modelo en estrella orientada a almacenar los nombres de las aplicaciones.';
COMMENT ON COLUMN "ALERT_DIM_APPS"."APP_ID" IS 'Identificador de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_DIM_APPS"."APP_NAME" IS 'Nombre de aplicaciÃ³n.';

-- Table ALERT_DIM_TYPES
CREATE TABLE "ALERT_DIM_TYPES" ( 
	"TYPE_ID" NUMERIC(19) NOT NULL,
	"TYPE_NAME"  VARCHAR(40) NOT NULL
	);
	
ALTER TABLE "ALERT_DIM_TYPES" ADD CONSTRAINT "ALERT_DIM_TYPE_ID_CONS" PRIMARY KEY ("TYPE_ID");
COMMENT ON TABLE "ALERT_DIM_TYPES" IS 'Tabla de dimensiÃ³n del modelo en estrella orientada a almacenar los nombres de los tipos de alerta.';
COMMENT ON COLUMN "ALERT_DIM_TYPES"."ALERT_ID" IS 'Identificador de tipo de alerta.';
COMMENT ON COLUMN "ALERT_DIM_TYPES"."TYPE_NAME" IS 'Nombre de tipo de alerta.';

-- Table ALERT_DIM_TEMPLATES
CREATE TABLE "ALERT_DIM_TEMPLATES" ( 
	"TEMPLATE_ID" NUMERIC(19) NOT NULL,
	"TEMPLATE_NAME"  VARCHAR(40) NOT NULL
	);
	
ALTER TABLE "ALERT_DIM_TEMPLATES" ADD CONSTRAINT "ALERT_DIM_TEMPLATE_ID_CONS" PRIMARY KEY ("TEMPLATE_ID");
COMMENT ON TABLE "ALERT_DIM_TEMPLATES" IS 'Tabla de dimensiÃ³n del modelo en estrella orientada a almacenar los nombres de las plantillas de alerta.';
COMMENT ON COLUMN "ALERT_DIM_TEMPLATES"."TEMPLATE_ID" IS 'Identificador de plantilla de alertas.';
COMMENT ON COLUMN "ALERT_DIM_TEMPLATES"."TEMPLATE_NAME" IS 'Nombre de plantilla de alertas.';

-- Table ALERT_DIM_NODES
CREATE TABLE "ALERT_DIM_NODES" ( 
	"NODE_ID" NUMERIC(19) NOT NULL,
	"NODE_NAME"  VARCHAR(40) NOT NULL
	);
	
ALTER TABLE "ALERT_DIM_NODES" ADD CONSTRAINT "ALERT_DIM_NODE_ID_CONS" PRIMARY KEY ("NODE_ID");
COMMENT ON TABLE "ALERT_DIM_NODES" IS 'Tabla de dimensiÃ³n del modelo en estrella orientada a almacenar los nombres de los nodos.';
COMMENT ON COLUMN "ALERT_DIM_NODES"."NODE_ID" IS 'Identificador de nodo.';
COMMENT ON COLUMN "ALERT_DIM_NODES"."NODE_NAME" IS 'Nombre de nodo.';

-- Table ALERT_DIM_LEVELS
CREATE TABLE "ALERT_DIM_LEVELS" ( 
	"LEVEL_ID" NUMERIC(19) NOT NULL,
	"LEVEL_NAME"  VARCHAR(8) NOT NULL
	);
	
ALTER TABLE "ALERT_DIM_LEVELS" ADD CONSTRAINT "ALERT_DIM_LEVEL_ID_CONS" PRIMARY KEY ("LEVEL_ID");
COMMENT ON TABLE "ALERT_DIM_LEVELS" IS 'Tabla de dimensiÃ³n del modelo en estrella orientada a almacenar los nombres de los niveles de criticidad.';
COMMENT ON COLUMN "ALERT_DIM_LEVELS"."LEVEL_ID" IS 'Identificador de nivel de criticidad.';
COMMENT ON COLUMN "ALERT_DIM_LEVELS"."LEVEL_NAME" IS 'Nombre de nivel de criticidad.';

-- Table ALERT_STATISTICS
CREATE TABLE "ALERT_STATISTICS" ( 
	"STATISTIC_ID" NUMERIC(19) NOT NULL,
	"APP_ID" NUMERIC(19) NOT NULL,
	"TYPE_ID" NUMERIC(19) NOT NULL,
	"TEMPLATE_ID" NUMERIC(19) NOT NULL,
	"NODE" NUMERIC(19) NOT NULL,
	"SEVERITY" NUMERIC(19) NOT NULL,
	"TIMESTAMP" DATE NOT NULL,
	"OCURRENCY" NUMERIC(19) NOT NULL
	);
	
ALTER TABLE "ALERT_STATISTICS" ADD CONSTRAINT "STATISTIC_ID_CONS" PRIMARY KEY ("STATISTIC_ID");
ALTER TABLE "ALERT_STATISTICS" ADD CONSTRAINT "ALERT_DIM_APP_ID_FK" FOREIGN KEY ("APP_ID") REFERENCES "ALERT_DIM_APPS" ("APP_ID");
ALTER TABLE "ALERT_STATISTICS" ADD CONSTRAINT "ALERT_DIM_TYPE_ID_FK" FOREIGN KEY ("TYPE_ID") REFERENCES "ALERT_DIM_TYPES" ("TYPE_ID");
ALTER TABLE "ALERT_STATISTICS" ADD CONSTRAINT "ALERT_DIM_TEMPLATE_ID_FK" FOREIGN KEY ("TEMPLATE_ID") REFERENCES "ALERT_DIM_TEMPLATES" ("TEMPLATE_ID");
ALTER TABLE "ALERT_STATISTICS" ADD CONSTRAINT "ALERT_DIM_NODE_ID_FK" FOREIGN KEY ("NODE") REFERENCES "ALERT_DIM_NODES" ("NODE_ID");
ALTER TABLE "ALERT_STATISTICS" ADD CONSTRAINT "ALERT_DIM_LEVEL_ID_FK" FOREIGN KEY ("SEVERITY") REFERENCES "ALERT_DIM_LEVELS" ("LEVEL_ID");
COMMENT ON TABLE "ALERT_STATISTICS" IS 'Conjunto de datos agrupados para su explotaciÃ³n estadÃ­stica.';
COMMENT ON COLUMN "ALERT_STATISTICS"."STATISTIC_ID" IS 'Identificador de la estadÃ­stica.';
COMMENT ON COLUMN "ALERT_STATISTICS"."APP_ID" IS 'Identificador de la aplicaciÃ³n.';
COMMENT ON COLUMN "ALERT_STATISTICS"."TYPE_ID" IS 'Identificador del tipo de alarma.';
COMMENT ON COLUMN "ALERT_STATISTICS"."TEMPLATE_ID" IS 'Identificador de la plantilla.';
COMMENT ON COLUMN "ALERT_STATISTICS"."NODE" IS 'Identificador del nodo.';
COMMENT ON COLUMN "ALERT_STATISTICS"."SEVERITY" IS 'Nombre de nivel de criticidad.';
COMMENT ON COLUMN "ALERT_STATISTICS"."TIMESTAMP" IS 'Fecha (con precisiÃ³n de dÃ­a) a la que corresponde el computo.';
COMMENT ON COLUMN "ALERT_STATISTICS"."OCURRENCY" IS 'NÃºmero de ocurrencias de esta configuraciÃ³n de alerta en la fecha indicada.';

-- Table ALERT_AUDIT_CONTROL
CREATE TABLE "ALERT_AUDIT_CONTROL" ( 
	"ALERT_AUDIT_CONTROL_ID" NUMERIC(19) NOT NULL,
	"EXECUTION_BEGIN" DATE,
	"AUDIT_BEGIN" DATE,
	"AUDIT_END" DATE,
	"EXECUTION_END" DATE,
	"RESULT" NUMERIC(1)
	);
	
ALTER TABLE "ALERT_AUDIT_CONTROL" ADD CONSTRAINT "ALERT_AUDIT_CONTROL_ID_CONS" PRIMARY KEY ("ALERT_AUDIT_CONTROL_ID");

COMMENT ON TABLE "ALERT_AUDIT_CONTROL" IS 'Tabla para controlar el estado de las ejecuciones del proceso de volcado de datos desde la auditoria a las estadisticas.';
COMMENT ON COLUMN "ALERT_AUDIT_CONTROL"."ALERT_AUDIT_CONTROL_ID" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "ALERT_AUDIT_CONTROL"."EXECUTION_BEGIN" IS 'Fecha y hora de ejecucion del proceso';
COMMENT ON COLUMN "ALERT_AUDIT_CONTROL"."AUDIT_BEGIN" IS 'Fecha desde donde se empiezan a procesar registros de la auditoria';
COMMENT ON COLUMN "ALERT_AUDIT_CONTROL"."AUDIT_END" IS 'Fecha hasta donde se procesan registros de la auditoria.';
COMMENT ON COLUMN "ALERT_AUDIT_CONTROL"."EXECUTION_END" IS 'Fecha y hora de finalizacion de la ejecucion del proceso.';
COMMENT ON COLUMN "ALERT_AUDIT_CONTROL"."RESULT" IS 'Codigo con el resultado del proceso: 0 - OK; 1 - ERROR';

COMMIT;