-- ********************************************************
-- **************** Creación de Tablas ********************
-- ********************************************************

-- ****************** TABLAS DE CONFIGURACIÓN *****************

-- Table USER_MONITORIZA
CREATE TABLE "USER_MONITORIZA"(
  "ID_USER_MONITORIZA" NUMERIC(19) NOT NULL,
  "LOGIN" VARCHAR(100) NOT NULL,
  "PASSWORD" VARCHAR(150) NOT NULL,
  "NAME" VARCHAR(100) NOT NULL,
  "SURNAMES" VARCHAR(150) NOT NULL,
  "EMAIL" VARCHAR(150) NOT NULL,
  "IS_BLOCKED" CHAR(1) NOT NULL,
  "ATTEMPTS_NUMBER" INTEGER NOT NULL,
  "LAST_ACCESS" TIMESTAMP,
  "IP_LAST_ACCESS" VARCHAR(15)
);
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
	"ID_TIMER" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(100) NOT NULL,
	"FREQUENCY" NUMERIC(19,0) NOT NULL
);

ALTER TABLE "TIMER_MONITORIZA" ADD CONSTRAINT "ID_TIMER" PRIMARY KEY ("ID_TIMER");
ALTER TABLE "TIMER_MONITORIZA" ADD CONSTRAINT "TIMER_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "TIMER_MONITORIZA" IS 'Tabla que almacena la información relativa a los timers que determinan la frecuencia de ejecución de las peticiones a servicios de @Firma y TS@.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."ID_TIMER" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."NAME" IS 'Valor que representa el nombre único del timer.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."FREQUENCY" IS 'Valor que representa el valor en milisegundos de la frecuencia de ejecución de las peticiones asociadas al timer.';

-- Table KEYSTORE
CREATE TABLE "KEYSTORE"(
  "ID_KEYSTORE" NUMERIC(19) NOT NULL,
  "NAME" VARCHAR(50) NOT NULL,
  "KEYSTORE" BLOB,
  "TOKEN_NAME" VARCHAR(30) NOT NULL,
  "PASSWORD" VARCHAR(255) NOT NULL,
  "KEYSTORE_TYPE" VARCHAR(50) NOT NULL
);
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
  "ID_SYSTEM_CERTIFICATE" NUMERIC(19) NOT NULL,
  "ALIAS" VARCHAR(4000) NOT NULL,
  "ID_KEYSTORE" NUMERIC(19) NOT NULL,
  "IS_KEY" CHAR(1) NOT NULL,
  "ISSUER" VARCHAR(4000) NOT NULL,
  "SUBJECT" VARCHAR(4000) NOT NULL,
  "SERIAL_NUMBER" NUMERIC(100) NOT NULL,
  "ID_STATUS_CERTIFICATE" NUMERIC(19) NOT NULL,
  "ID_USER_MONITORIZA" NUMERIC(19) NOT NULL
);
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_SYSTEM_CERTIFICATE" PRIMARY KEY ("ID_SYSTEM_CERTIFICATE");
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_STATUS_CERTIFICATE_FK" FOREIGN KEY ("ID_STATUS_CERTIFICATE") REFERENCES "C_STATUS_CERTIFICATES" ("ID_STATUS_CERTIFICATE");
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_USER_MONITORIZA_FK" FOREIGN KEY ("ID_USER_MONITORIZA") REFERENCES "USER_MONITORIZA" ("ID_USER_MONITORIZA");
COMMENT ON TABLE "SYSTEM_CERTIFICATE" IS 'Tabla que almacena toda la información relativa a certificados de uso por el sistema.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ID_SYSTEM_CERTIFICATE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ALIAS" IS 'Valor que representa el alias del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ID_KEYSTORE" IS 'Valor que representa el almacén de claves donde se encuentra almacenado el certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."IS_KEY" IS 'Valor que indica si el alias hace referencia a una clave (Y) o no (N).';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ISSUER" IS 'Valor que representa el emisor del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."SUBJECT" IS 'Valor que representa el asunto del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."SERIAL_NUMBER" IS 'Valor que representa el serial number del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ID_STATUS_CERTIFICATE" IS 'Valor que representa el estado del certificado.';
COMMENT ON COLUMN "SYSTEM_CERTIFICATE"."ID_USER_MONITORIZA" IS 'Valor que representa el ususario.';

-- Table C_PLATFORM_TYPE
CREATE TABLE "C_PLATFORM_TYPE" ( 
	"ID_PLATFORM_TYPE" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(100) NOT NULL
);
ALTER TABLE "C_PLATFORM_TYPE" ADD CONSTRAINT "ID_PLATFORM_TYPE" PRIMARY KEY ("ID_PLATFORM_TYPE");
COMMENT ON TABLE "C_PLATFORM_TYPE" IS 'Tabla que almacena los tipos de plataformas en Monitoriz@: @Firma o TS@.';

-- Table PLATFORM_MONITORIZA
CREATE TABLE "PLATFORM_MONITORIZA" ( 
	"ID_PLATFORM" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(100) NOT NULL,
	"HOST" VARCHAR(100) NOT NULL,
	"PORT" VARCHAR(10) NOT NULL,
	"IS_SECURE" CHAR(1) NOT NULL,
	"HTTPS_PORT" VARCHAR(10),
	"SERVICE_CONTEXT" VARCHAR(100) NOT NULL,
	"ID_PLATFORM_TYPE" NUMERIC(19) NOT NULL,
	"OCSP_CONTEXT" VARCHAR(100),
	"RFC3161_CONTEXT" VARCHAR(100),
	"RFC3161_PORT" NUMERIC(10),
	"RFC3161_USE_AUTH" CHAR(1) NOT NULL,
	"RFC3161_AUTH_CERTIFICATE" NUMERIC(19)
	);
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

-- Table ALARM_MONITORIZA
CREATE TABLE "ALARM_MONITORIZA" (
	"ID_ALARM" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(100) NOT NULL,
	"BLOCKED_TIME" NUMERIC(19) NOT NULL
);
ALTER TABLE "ALARM_MONITORIZA" ADD CONSTRAINT "ID_ALARM" PRIMARY KEY ("ID_ALARM");
ALTER TABLE "ALARM_MONITORIZA" ADD CONSTRAINT "ALARM_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "ALARM_MONITORIZA" IS 'Tabla que almacena la información relativa a las alarmas de monitoriz@.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."ID_ALARM" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."NAME" IS 'Valor que representa el nombre único de la alarma.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."BLOCKED_TIME" IS 'Valor que representa el tiempo en milisegundos que se bloquean las alarmas de este tipo antes de lanzar un resumen.';

-- Table SERVICE_MONITORIZA
CREATE TABLE "SERVICE_MONITORIZA" (
	"ID_SERVICE" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(100) NOT NULL,
	"SERVICE_TIMEOUT" NUMERIC(19) NOT NULL,
	"NAME_ENDPOINT_WSDL" VARCHAR(100) NOT NULL,
	"DEGRADED_THRESHOLD" NUMERIC(19) NOT NULL,
	"LOST_THRESHOLD" NUMERIC(19) NOT NULL,
	"ID_TIMER_SERVICE" NUMERIC(19) NOT NULL,
	"ID_ALARM_SERVICE" NUMERIC(19) NOT NULL,
	"ID_PLATFORM_SERVICE" NUMERIC(19),
	"SERVICE_TYPE" VARCHAR(100) NOT NULL
);
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

-- Table MAIL_MONITORIZA
CREATE TABLE "MAIL_MONITORIZA" (
	"ID_MAIL" NUMERIC(19) NOT NULL,
	"EMAIL_ADDRESS" VARCHAR(150) NOT NULL
);
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "ID_MAIL" PRIMARY KEY ("ID_MAIL");
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "MAIL_ADDRESS_UNIQUE" UNIQUE ("EMAIL_ADDRESS");
COMMENT ON TABLE "MAIL_MONITORIZA" IS 'Tabla que almacena las direcciones de correo electrónico a utilizar por las alarmas de monitoriz@.';
COMMENT ON COLUMN "MAIL_MONITORIZA"."ID_MAIL" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "MAIL_MONITORIZA"."EMAIL_ADDRESS" IS 'Valor que representa una dirección de correo electrónico.';

-- Table R_ALARM_MAIL_DEGRADED
CREATE TABLE "R_ALARM_MAIL_DEGRADED"(
  "ID_MAIL" NUMERIC(19) NOT NULL, 
  "ID_ALARM" NUMERIC(19) NOT NULL 
);
ALTER TABLE "R_ALARM_MAIL_DEGRADED" ADD CONSTRAINT "R_ALARM_MAIL_DEGRADED" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL_DEGRADED" ADD CONSTRAINT "R_MAIL_ALARM_DEGRADED" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");
COMMENT ON COLUMN "R_ALARM_MAIL_DEGRADED"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL_DEGRADED"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';


-- Table R_ALARM_MAIL_DOWN
CREATE TABLE "R_ALARM_MAIL_DOWN"(
  "ID_MAIL" NUMERIC(19) NOT NULL, 
  "ID_ALARM" NUMERIC(19) NOT NULL 
);
ALTER TABLE "R_ALARM_MAIL_DOWN" ADD CONSTRAINT "R_ALARM_MAIL_DOWN" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL_DOWN" ADD CONSTRAINT "R_MAIL_ALARM_DOWN" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");
COMMENT ON COLUMN "R_ALARM_MAIL_DOWN"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL_DOWN"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';

ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_TIMER_SERVICE" FOREIGN KEY ("ID_TIMER_SERVICE") REFERENCES "TIMER_MONITORIZA" ("ID_TIMER");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_ALARM_SERVICE" FOREIGN KEY ("ID_ALARM_SERVICE") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_PLATFORM_SERVICE" FOREIGN KEY ("ID_PLATFORM_SERVICE") REFERENCES "PLATFORM_MONITORIZA" ("ID_PLATFORM");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_PLATFORM_TYPE_FK" FOREIGN KEY ("ID_PLATFORM_TYPE") REFERENCES "C_PLATFORM_TYPE" ("ID_PLATFORM_TYPE");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_SYSTEM_CERTIFICATE_FK" FOREIGN KEY ("RFC3161_AUTH_CERTIFICATE") REFERENCES "SYSTEM_CERTIFICATE" ("ID_SYSTEM_CERTIFICATE") ;
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "R_SC_K" FOREIGN KEY ("ID_KEYSTORE") REFERENCES "KEYSTORE" ("ID_KEYSTORE");

CREATE TABLE "C_STATUS_CERTIFICATES"(
 	"ID_STATUS_CERTIFICATE" NUMERIC(19) NOT NULL, 
	"TOKEN_NAME" VARCHAR(30) NOT NULL);
	
ALTER TABLE "C_STATUS_CERTIFICATES" ADD CONSTRAINT "ID_STATUS_CERTIFICATE" PRIMARY KEY ("ID_STATUS_CERTIFICATE");
COMMENT ON TABLE "C_STATUS_CERTIFICATES"  IS 'Tabla que almacena las constantes para los estados de los certificados.';
COMMENT ON COLUMN "C_STATUS_CERTIFICATES"."ID_STATUS_CERTIFICATE" IS 'Identificador de la tabla.'; 
COMMENT ON COLUMN "C_STATUS_CERTIFICATES"."TOKEN_NAME" IS 'Valor que representa la descripción del estado del certificado.';
 
   

COMMIT;