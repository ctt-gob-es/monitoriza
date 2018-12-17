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
  "KEYSTORE_TYPE" VARCHAR(50) NOT NULL,
  "VERSION" NUMERIC(19) NOT NULL
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
COMMENT ON COLUMN "KEYSTORE"."VERSION" IS 'Valor que representa el número de modificaciones aplicadas al almacén de claves inicial.';

CREATE TABLE "C_STATUS_CERTIFICATES"(
 	"ID_STATUS_CERTIFICATE" NUMERIC(19) NOT NULL, 
	"TOKEN_NAME" VARCHAR(30) NOT NULL);
	
ALTER TABLE "C_STATUS_CERTIFICATES" ADD CONSTRAINT "ID_STATUS_CERTIFICATE" PRIMARY KEY ("ID_STATUS_CERTIFICATE");
COMMENT ON TABLE "C_STATUS_CERTIFICATES"  IS 'Tabla que almacena las constantes para los estados de los certificados.';
COMMENT ON COLUMN "C_STATUS_CERTIFICATES"."ID_STATUS_CERTIFICATE" IS 'Identificador de la tabla.'; 
COMMENT ON COLUMN "C_STATUS_CERTIFICATES"."TOKEN_NAME" IS 'Valor que representa la descripción del estado del certificado.';

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
  "ID_USER_MONITORIZA" NUMERIC(19)
);
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_SYSTEM_CERTIFICATE" PRIMARY KEY ("ID_SYSTEM_CERTIFICATE");
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "SYS_CERT_UNIQUE_CERT" UNIQUE ("ID_KEYSTORE", "ISSUER", "SERIAL_NUMBER");
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
	"SERVICE_CONTEXT" VARCHAR(100) NOT NULL,
	"ID_PLATFORM_TYPE" NUMERIC(19) NOT NULL,
	"OCSP_CONTEXT" VARCHAR(100),
	"RFC3161_CONTEXT" VARCHAR(100),
	"RFC3161_PORT" NUMERIC(10),
	"RFC3161_USE_AUTH" CHAR(1),
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

--Table REQUEST_SERVICE_FILE
CREATE TABLE "REQUEST_SERVICE_FILE" (
	"ID_REQUEST_SERVICE_FILE" NUMERIC(19) NOT NULL,
	"FILENAME" VARCHAR(100) NOT NULL,
	"CONTENT_TYPE" VARCHAR(100) NOT NULL,
	"FILEDATA" BLOB NOT NULL
);
ALTER TABLE "REQUEST_SERVICE_FILE" ADD CONSTRAINT "ID_REQUEST_SERVICE_FILE" PRIMARY KEY ("ID_REQUEST_SERVICE_FILE");
COMMENT ON TABLE "REQUEST_SERVICE_FILE" IS 'Tabla que almacena los archivos comprimidos con las carpetas de peticiones';
COMMENT ON COLUMN "REQUEST_SERVICE_FILE"."ID_REQUEST_SERVICE_FILE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "REQUEST_SERVICE_FILE"."FILENAME" IS 'Nombre del archivo.';
COMMENT ON COLUMN "REQUEST_SERVICE_FILE"."CONTENT_TYPE" IS 'Content Type del archivo.';
COMMENT ON COLUMN "REQUEST_SERVICE_FILE"."FILEDATA" IS 'Contenido del archivo.';

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
	"SERVICE_TYPE" VARCHAR(100) NOT NULL,
	"ID_REQUEST_FILE" NUMERIC(19) NOT NULL
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
COMMENT ON COLUMN "R_ALARM_MAIL_DEGRADED"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL_DEGRADED"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';


-- Table R_ALARM_MAIL_DOWN
CREATE TABLE "R_ALARM_MAIL_DOWN"(
  "ID_MAIL" NUMERIC(19) NOT NULL, 
  "ID_ALARM" NUMERIC(19) NOT NULL 
);
COMMENT ON COLUMN "R_ALARM_MAIL_DOWN"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL_DOWN"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';

-- Table TIMER_SCHEDULED
CREATE TABLE "TIMER_SCHEDULED"(
	"ID_TIMER_SCHEDULED" NUMERIC(19) NOT NULL,
	"ID_TIMER" NUMERIC(19) NOT NULL,
	"IS_UPDATED" CHAR(1) NOT NULL
);

ALTER TABLE "TIMER_SCHEDULED" ADD CONSTRAINT "ID_TIMER_SCHEDULED" PRIMARY KEY ("ID_TIMER_SCHEDULED");
COMMENT ON TABLE "TIMER_SCHEDULED" IS 'Tabla que almacena qué timers se están ejecutando y si éstos están actualizados respecto a la información de la base de datos.';
COMMENT ON COLUMN "TIMER_SCHEDULED"."ID_TIMER_SCHEDULED" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "TIMER_SCHEDULED"."ID_TIMER" IS 'Identificador del timer programado.';
COMMENT ON COLUMN "TIMER_SCHEDULED"."IS_UPDATED" IS 'Indica si los datos del timer programado están actualizados respecto de la información de la base de datos.';

-- Table C_AUTHENTICATION_TYPE
CREATE TABLE "C_AUTHENTICATION_TYPE" ( 
	"ID_AUTHENTICATION_TYPE" NUMERIC(19) NOT NULL,
	"TOKEN_NAME" VARCHAR(30) NOT NULL
);
ALTER TABLE "C_AUTHENTICATION_TYPE" ADD CONSTRAINT "ID_AUTHENTICATION_TYPE" PRIMARY KEY ("ID_AUTHENTICATION_TYPE");
COMMENT ON TABLE "C_AUTHENTICATION_TYPE" IS 'Tabla que almacena los tipos de autenticación con el servicio de validación.';
COMMENT ON COLUMN "C_AUTHENTICATION_TYPE"."ID_AUTHENTICATION_TYPE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "C_AUTHENTICATION_TYPE"."TOKEN_NAME" IS 'Valor que representa el nombre del tipo de autenticación.';

-- Table VALID_SERVICE
CREATE TABLE "VALID_SERVICE" ( 
	"ID_VALID_SERVICE" NUMERIC(19) NOT NULL,
	"APPLICATION" VARCHAR(100) NOT NULL,
	"HOST" VARCHAR(100) NOT NULL,
	"PORT" VARCHAR(10) NOT NULL,
	"IS_SECURE" CHAR(1),
	"IS_ENABLE_VALIDATION_JOB" CHAR(1),
	"CRON_EXPRESSION" VARCHAR(100),
	"USER" VARCHAR(100),
	"PASS" VARCHAR(255),
	"ID_AUTHENTICATION_TYPE" NUMERIC(19) NOT NULL,
	"ID_AUTH_CERTIFICATE" NUMERIC(19)
	);
ALTER TABLE "VALID_SERVICE" ADD CONSTRAINT "ID_VALID_SERVICE" PRIMARY KEY ("ID_VALID_SERVICE");
ALTER TABLE "VALID_SERVICE" ADD CONSTRAINT "VALID_SERVICE_APP_UNIQUE" UNIQUE ("APPLICATION", "HOST", "PORT");
COMMENT ON TABLE "VALID_SERVICE" IS 'Tabla que almacena la información relativa a la conexión con plataformas @Firma/TS@.';
COMMENT ON COLUMN "VALID_SERVICE"."ID_VALID_SERVICE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "VALID_SERVICE"."APPLICATION" IS 'Valor que representa el nombre único de la aplicación.';
COMMENT ON COLUMN "VALID_SERVICE"."HOST" IS 'Valor que representa el host para la conexión con el servicio de validación.';
COMMENT ON COLUMN "VALID_SERVICE"."PORT" IS 'Valor que representa el puerto para la conexión con el servicio de validación.';
COMMENT ON COLUMN "VALID_SERVICE"."IS_SECURE" IS 'Valor que representa si se accede de manera seguro a el servicio de validación.';
COMMENT ON COLUMN "VALID_SERVICE"."IS_ENABLE_VALIDATION_JOB" IS 'Valor que representa si se habilita el proceso de validación en segundo plano.';
COMMENT ON COLUMN "VALID_SERVICE"."CRON_EXPRESSION" IS 'Valor que representa la expresión cron del proceso de validación en segundo plano.';
COMMENT ON COLUMN "VALID_SERVICE"."USER" IS 'Valor que representa el usuario para la conexión con el servicio de validación.';
COMMENT ON COLUMN "VALID_SERVICE"."PASS" IS 'Valor que representa las contraseña para la conexión con el servicio de validación.';
COMMENT ON COLUMN "VALID_SERVICE"."ID_AUTHENTICATION_TYPE" IS 'Valor que representa el tipo de autenticación del servicio de validación.';
COMMENT ON COLUMN "VALID_SERVICE"."ID_AUTH_CERTIFICATE" IS 'Valor que representa el identificador del certificado para la conexión autenticada con el servicio de validación.';

-- Table NODE_AFIRMA
CREATE TABLE "NODE_MONITORIZA" ( 
	"ID_NODE" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(100) NOT NULL,
	"HOST" VARCHAR(100) NOT NULL,
	"PORT" VARCHAR(10) NOT NULL,
	"IS_SECURE" CHAR(1) NOT NULL,
	"CHECK_EMERGENCYDB" CHAR(1),
	"CHECK_TSA" CHAR(1),
	"CHECK_AFIRMA" CHAR(1),
	"CHECK_HSM" CHAR(1),
	"CHECK_SERVICES" CHAR(1),
	"CHECK_VALIDATION_METHOD" CHAR(1),
	"ID_NODE_TYPE" NUMERIC(19) NOT NULL
	);
ALTER TABLE "NODE_MONITORIZA" ADD CONSTRAINT "ID_NODE_CONS" PRIMARY KEY ("ID_NODE");
ALTER TABLE "NODE_MONITORIZA" ADD CONSTRAINT "NODE_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "NODE_MONITORIZA" IS 'Tabla que almacena la información relativa a la conexión con nodos de @Firma del entorno que se monitoriza.';
COMMENT ON COLUMN "NODE_MONITORIZA"."ID_NODE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "NODE_MONITORIZA"."NAME" IS 'Valor que representa el nombre único del nodo en Monitoriz@.';
COMMENT ON COLUMN "NODE_MONITORIZA"."HOST" IS 'Valor que representa el host para la conexión con el nodo.';
COMMENT ON COLUMN "NODE_MONITORIZA"."PORT" IS 'Valor que representa el puerto para la conexión con el nodo.';
COMMENT ON COLUMN "NODE_MONITORIZA"."IS_SECURE" IS 'Valor que indica si la conexión al nodo es segura.';
COMMENT ON COLUMN "NODE_MONITORIZA"."CHECK_EMERGENCYDB" IS 'Valor que indica si se va a monitorizar el SPIE de la base de datos de emergencia.';
COMMENT ON COLUMN "NODE_MONITORIZA"."CHECK_TSA" IS 'Valor que indica si se va a monitorizar el SPIE de la conexión a TS@.';
COMMENT ON COLUMN "NODE_MONITORIZA"."CHECK_AFIRMA" IS 'Valor que indica si se va a monitorizar el SPIE de la conexión a @Firma.';
COMMENT ON COLUMN "NODE_MONITORIZA"."CHECK_HSM" IS 'Valor que indica si se va a monitorizar el SPIE de la conexión al HSM.';
COMMENT ON COLUMN "NODE_MONITORIZA"."CHECK_SERVICES" IS 'Valor que indica si se va a monitorizar el SPIE de tiempos de respuesta de servicios @Firma.';
COMMENT ON COLUMN "NODE_MONITORIZA"."CHECK_VALIDATION_METHOD" IS 'Valor que indica si se va a monitorizar el SPIE de conexión con métodos de validación.';
COMMENT ON COLUMN "NODE_MONITORIZA"."ID_NODE_TYPE" IS 'Valor que indica si el nodo pertenece a @Firma o TS@.';


-- Table CONF_SERVER_MAIL
CREATE TABLE "CONF_SERVER_MAIL" (
	"ID_CONF_SERVER_MAIL" NUMERIC(19) NOT NULL,
	"ISSUER_MAIL" VARCHAR(200) NOT NULL,
	"HOST_MAIL" VARCHAR(200) NOT NULL,
	"PORT_MAIL" NUMERIC(10) NOT NULL,
	"TLS" CHAR(1) NOT NULL,
	"AUTHENTICATION" CHAR(1) NOT NULL,
	"USER_MAIL" VARCHAR(200),
	"PASSWORD_MAIL" VARCHAR(200)
	);
ALTER TABLE "CONF_SERVER_MAIL" ADD CONSTRAINT "ID_CONF_SERVER_MAIL" PRIMARY KEY ("ID_CONF_SERVER_MAIL");
COMMENT ON TABLE "CONF_SERVER_MAIL" IS 'Tabla que almacena la configuración del servidor de correo electrónico de Monitoriz@';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."ID_CONF_SERVER_MAIL" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."ISSUER_MAIL" IS 'Emisor del correo electrónico.';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."HOST_MAIL" IS 'Host del servidor del correo electrónico';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."PORT_MAIL" IS 'Puerto del servidor del correo electrónico';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."TLS" IS 'Indica si los correos electrónicos se cifran mediante TLS';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."AUTHENTICATION" IS 'Autenticación del servidor del correo electrónico';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."USER_MAIL" IS 'Usuario del servidor del correo electrónico';
COMMENT ON COLUMN "CONF_SERVER_MAIL"."PASSWORD_MAIL" IS 'Contraseña del servidor del correo electrónico';

-- Table METHOD_VALIDATION
CREATE TABLE "METHOD_VALIDATION" (
	"ID_METHOD_VALIDATION" NUMERIC(19) NOT NULL,
	"METHOD_NAME" VARCHAR(200) NOT NULL,
	"ID_CONF_SPIE" NUMERIC(19) NOT NULL
	);
ALTER TABLE "METHOD_VALIDATION" ADD CONSTRAINT "ID_METHOD_VALIDATION" PRIMARY KEY ("ID_METHOD_VALIDATION");
COMMENT ON TABLE "METHOD_VALIDATION" IS 'Tabla que almacena los métodos de validación de las configuración de SPIE';
COMMENT ON COLUMN "METHOD_VALIDATION"."ID_METHOD_VALIDATION" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "METHOD_VALIDATION"."METHOD_NAME" IS 'Nombre del método de validación.';
COMMENT ON COLUMN "METHOD_VALIDATION"."ID_CONF_SPIE" IS 'Identificador de la tabla conf spie.';

-- Table CONF_SPIE
CREATE TABLE "CONF_SPIE" (
	"ID_CONF_SPIE" NUMERIC(19) NOT NULL,
	"PERCENT_ACCEPT" NUMERIC(19,0) NOT NULL,
	"FREQUENCY_AFIRMA" NUMERIC(19,0) NOT NULL,
	"FREQUENCY_TSA" NUMERIC(19,0) NOT NULL
	);
ALTER TABLE "CONF_SPIE" ADD CONSTRAINT "ID_CONF_SPIE" PRIMARY KEY ("ID_CONF_SPIE");
COMMENT ON TABLE "CONF_SPIE" IS 'Tabla que almacena la configuración del SPIE';
COMMENT ON COLUMN "CONF_SPIE"."ID_CONF_SPIE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "CONF_SPIE"."PERCENT_ACCEPT" IS 'Porcentaje de aceptación de peticiones degradadas en la monitorización de SPIE.';
COMMENT ON COLUMN "CONF_SPIE"."FREQUENCY_AFIRMA" IS 'Timer de @firma.';
COMMENT ON COLUMN "CONF_SPIE"."FREQUENCY_TSA" IS 'Timer de TS@.';

-- Table ALARM_DEFECT
CREATE TABLE "ALARM"(
  "ID_ALARM" VARCHAR(100) NOT NULL,
  "DESCRIPTION" VARCHAR(200),
  "TIME_BLOCK" NUMERIC(19,0),
  "ACTIVE" CHAR(1)
);
ALTER TABLE "ALARM" ADD CONSTRAINT "ID_ALARM_SPIE" PRIMARY KEY ("ID_ALARM");
COMMENT ON TABLE "ALARM" IS 'Tabla que almacena las alarmas de la configuración de SPIE.';
COMMENT ON COLUMN "ALARM"."ID_ALARM" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "ALARM"."DESCRIPTION" IS 'Valor que representa la descripción de la alarma.';
COMMENT ON COLUMN "ALARM"."TIME_BLOCK" IS 'Valor que representa el tiempo de bloqueo de la alarma.';
COMMENT ON COLUMN "ALARM"."ACTIVE" IS 'Valor que representa si la alarma se encuentra activa o no.';

-- R_ALARM_MAIL
CREATE TABLE "R_ALARM_MAIL"(
  "ID_MAIL" NUMERIC(19,0) NOT NULL, 
  "ID_ALARM" VARCHAR(100) NOT NULL 
);
COMMENT ON COLUMN "R_ALARM_MAIL"."ID_ALARM" IS 'Identificador de la alarma.';
COMMENT ON COLUMN "R_ALARM_MAIL"."ID_MAIL" IS 'Identificador de la dirección de correo electrónico.';

-- Table SPIE_SCHEDULED
CREATE TABLE "SPIE_SCHEDULED"(
	"ID_SPIE_SCHEDULED" NUMERIC(19) NOT NULL,
	"ID_PLATFORM_TYPE" NUMERIC(19) NOT NULL,
	"IS_UPDATED" CHAR(1) NOT NULL
);

ALTER TABLE "SPIE_SCHEDULED" ADD CONSTRAINT "ID_SPIE_SCHEDULED" PRIMARY KEY ("ID_SPIE_SCHEDULED");
COMMENT ON TABLE "SPIE_SCHEDULED" IS 'Tabla que almacena las plataformas cuyos SPIE se están monitorizando y están programados.';
COMMENT ON COLUMN "SPIE_SCHEDULED"."ID_SPIE_SCHEDULED" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SPIE_SCHEDULED"."ID_PLATFORM_TYPE" IS 'Referencia al tipo de plataforma del SPIE programado.';
COMMENT ON COLUMN "SPIE_SCHEDULED"."IS_UPDATED" IS 'Indica si los datos del timer programado están actualizados respecto de la información de la base de datos.';

-- Table SPIE_TYPE
CREATE TABLE "SPIE_TYPE" ( 
	"ID_SPIE_TYPE" NUMERIC(19) NOT NULL,
	"TOKENNAME" VARCHAR(100) NOT NULL,
	"CONTEXT" VARCHAR(100) NOT NULL,
	"ID_PLATFORM_TYPE" NUMERIC(19) NOT NULL,
	"SEMAPHORE_LEVEL" NUMERIC(1) NOT NULL
	);
ALTER TABLE "SPIE_TYPE" ADD CONSTRAINT "ID_SPIE_TYPE_CONS" PRIMARY KEY ("ID_SPIE_TYPE");
COMMENT ON TABLE "SPIE_TYPE" IS 'Tabla que almacena la información relativa a los tipos de servicio SPIE que se van a monitorizar.';
COMMENT ON COLUMN "SPIE_TYPE"."ID_SPIE_TYPE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SPIE_TYPE"."TOKENNAME" IS 'Valor que representa el nombre del servicio SPIE.';
COMMENT ON COLUMN "SPIE_TYPE"."CONTEXT" IS 'Valor que representa el contexto de conexión al servicio SPIE.';
COMMENT ON COLUMN "SPIE_TYPE"."ID_PLATFORM_TYPE" IS 'Valor que indica la plataforma a la que accede el servicio SPIE.';
COMMENT ON COLUMN "SPIE_TYPE"."SEMAPHORE_LEVEL" IS 'Valor que indica el nivel de semáforo (1-Ambar,2-Rojo) al obtenerse un estado de error en el SPIE';

-- Table DAILY_VIP_MONITORING
CREATE TABLE "DAILY_VIP_MONITORING" ( 
	"ID_DAILY_VIP" NUMERIC(19) NOT NULL,
	"STATUS" VARCHAR(100) NOT NULL,
	"PLATFORM" VARCHAR(100) NOT NULL,
	"SERVICE" VARCHAR(100) NOT NULL,
	"SAMPLING_TIME" TIMESTAMP NOT NULL
	);
ALTER TABLE "DAILY_VIP_MONITORING" ADD CONSTRAINT "ID_DAILY_VIP_CONS" PRIMARY KEY ("ID_DAILY_VIP");
COMMENT ON TABLE "DAILY_VIP_MONITORING" IS 'Tabla que almacena la información relativa a los estados de los servicios de la VIP monitorizados en un momento determinado.';
COMMENT ON COLUMN "DAILY_VIP_MONITORING"."ID_DAILY_VIP" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "DAILY_VIP_MONITORING"."STATUS" IS 'Valor que representa el estado obtenido para un servicio en un momento determinado.';
COMMENT ON COLUMN "DAILY_VIP_MONITORING"."PLATFORM" IS 'Valor que representa la plataforma a la que pertenece el servicio monitorizado.';
COMMENT ON COLUMN "DAILY_VIP_MONITORING"."SERVICE" IS 'Valor que representa el nombre del servicio monitorizado.';
COMMENT ON COLUMN "DAILY_VIP_MONITORING"."SAMPLING_TIME" IS 'Valor que indica el momento de tiempo en el que se obtuvo el estado para el servicio monitorizado';

-- Table VIP_STATISTICS
CREATE TABLE "VIP_STATISTICS" ( 
	"ID_VIP_STATISTICS" NUMERIC(19) NOT NULL,
	"STATUS" VARCHAR(100) NOT NULL,
	"PLATFORM" VARCHAR(100) NOT NULL,
	"SERVICE" VARCHAR(100) NOT NULL,
	"DATE_GROUP" DATE NOT NULL,
	"STATUS_PERCENTAGE" FLOAT(6)
	);
ALTER TABLE "VIP_STATISTICS" ADD CONSTRAINT "ID_VIP_STATISTICS_CONS" PRIMARY KEY ("ID_VIP_STATISTICS");
COMMENT ON TABLE "VIP_STATISTICS" IS 'Tabla que almacena la información relativa a los estados de los servicios de la VIP monitorizados en un momento determinado.';
COMMENT ON COLUMN "VIP_STATISTICS"."ID_VIP_STATISTICS" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "VIP_STATISTICS"."STATUS" IS 'Valor que representa el estado obtenido para un servicio en un momento determinado.';
COMMENT ON COLUMN "VIP_STATISTICS"."PLATFORM" IS 'Valor que representa la plataforma a la que pertenece el servicio monitorizado.';
COMMENT ON COLUMN "VIP_STATISTICS"."SERVICE" IS 'Valor que representa el nombre del servicio monitorizado.';
COMMENT ON COLUMN "VIP_STATISTICS"."DATE_GROUP" IS 'Valor que indica la fecha dd/mm/aaaa en la que se agrupa el resultado para el servicio/estado';
COMMENT ON COLUMN "VIP_STATISTICS"."STATUS_PERCENTAGE" IS 'Valor que indica el porcentaje (0-1) de servicios que se encuentran en un estado para la fecha del registro';

ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_TIMER_SERVICE_FK" FOREIGN KEY ("ID_TIMER_SERVICE") REFERENCES "TIMER_MONITORIZA" ("ID_TIMER");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_ALARM_SERVICE_FK" FOREIGN KEY ("ID_ALARM_SERVICE") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_PLATFORM_SERVICE_FK" FOREIGN KEY ("ID_PLATFORM_SERVICE") REFERENCES "PLATFORM_MONITORIZA" ("ID_PLATFORM");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_REQUEST_FILE_FK" FOREIGN KEY ("ID_REQUEST_FILE") REFERENCES "REQUEST_SERVICE_FILE" ("ID_REQUEST_SERVICE_FILE");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_PLATFORM_TYPE_FK" FOREIGN KEY ("ID_PLATFORM_TYPE") REFERENCES "C_PLATFORM_TYPE" ("ID_PLATFORM_TYPE");
ALTER TABLE "PLATFORM_MONITORIZA" ADD CONSTRAINT "ID_SYSTEM_CERTIFICATE_FK" FOREIGN KEY ("RFC3161_AUTH_CERTIFICATE") REFERENCES "SYSTEM_CERTIFICATE" ("ID_SYSTEM_CERTIFICATE") ;
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "R_SC_K" FOREIGN KEY ("ID_KEYSTORE") REFERENCES "KEYSTORE" ("ID_KEYSTORE");
ALTER TABLE "TIMER_SCHEDULED" ADD CONSTRAINT "ID_TIMER_FK" FOREIGN KEY ("ID_TIMER") REFERENCES "TIMER_MONITORIZA" ("ID_TIMER");
ALTER TABLE "NODE_MONITORIZA" ADD CONSTRAINT "ID_NODE_TYPE_FK" FOREIGN KEY ("ID_NODE_TYPE") REFERENCES "C_PLATFORM_TYPE" ("ID_PLATFORM_TYPE");
ALTER TABLE "SPIE_TYPE" ADD CONSTRAINT "ID_SPIE_PLATFORM_TYPE_FK" FOREIGN KEY ("ID_PLATFORM_TYPE") REFERENCES "C_PLATFORM_TYPE" ("ID_PLATFORM_TYPE");
<<<<<<< HEAD
=======
ALTER TABLE "VALID_SERVICE" ADD CONSTRAINT "ID_AUTHENTICATION_TYPE_FK" FOREIGN KEY ("ID_AUTHENTICATION_TYPE") REFERENCES "C_AUTHENTICATION_TYPE" ("ID_AUTHENTICATION_TYPE");
ALTER TABLE "VALID_SERVICE" ADD CONSTRAINT "ID_SYSCERT_VALID_FK" FOREIGN KEY ("ID_AUTH_CERTIFICATE") REFERENCES "SYSTEM_CERTIFICATE" ("ID_SYSTEM_CERTIFICATE") ;
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_STATUS_CERTIFICATE_FK" FOREIGN KEY ("ID_STATUS_CERTIFICATE") REFERENCES "C_STATUS_CERTIFICATES" ("ID_STATUS_CERTIFICATE");
ALTER TABLE "SYSTEM_CERTIFICATE" ADD CONSTRAINT "ID_USER_MONITORIZA_FK" FOREIGN KEY ("ID_USER_MONITORIZA") REFERENCES "USER_MONITORIZA" ("ID_USER_MONITORIZA");
ALTER TABLE "R_ALARM_MAIL_DEGRADED" ADD CONSTRAINT "R_ALARM_MAIL_DEGRADED_FK" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL_DEGRADED" ADD CONSTRAINT "R_MAIL_ALARM_DEGRADED_FK" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");
ALTER TABLE "R_ALARM_MAIL_DOWN" ADD CONSTRAINT "R_ALARM_MAIL_DOWN_FK" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL_DOWN" ADD CONSTRAINT "R_MAIL_ALARM_DOWN_FK" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");
ALTER TABLE "R_ALARM_MAIL" ADD CONSTRAINT "R_ALARM_MAIL_FK" FOREIGN KEY ("ID_ALARM") REFERENCES "ALARM" ("ID_ALARM");
ALTER TABLE "R_ALARM_MAIL" ADD CONSTRAINT "R_MAIL_ALARM_FK" FOREIGN KEY ("ID_MAIL") REFERENCES "MAIL_MONITORIZA" ("ID_MAIL");
>>>>>>> d325c891afd03f44a7a4dae67c89541ab0268bd8

COMMIT;