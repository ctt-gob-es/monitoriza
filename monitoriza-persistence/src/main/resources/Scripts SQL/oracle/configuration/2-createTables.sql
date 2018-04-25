-- ********************************************************
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
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
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
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "TIMER_MONITORIZA" ADD CONSTRAINT "ID_TIMER" PRIMARY KEY ("ID_TIMER");
ALTER TABLE "TIMER_MONITORIZA" ADD CONSTRAINT "TIMER_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "TIMER_MONITORIZA" IS 'Tabla que almacena la información relativa a los timers que determinan la frecuencia de ejecución de las peticiones a servicios de @Firma y TS@.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."ID_TIMER" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."NAME" IS 'Valor que representa el nombre único del timer.';
COMMENT ON COLUMN "TIMER_MONITORIZA"."FREQUENCY" IS 'Valor que representa el valor en milisegundos de la frecuencia de ejecución de las peticiones asociadas al timer.';

-- Table PLATFORM_AFIRMA
CREATE TABLE "PLATFORM_AFIRMA" ( 
	"ID_PLATFORM_AFIRMA" NUMBER(19) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"HOST" Varchar2(100) NOT NULL,
	"PORT" Varchar2(10) NOT NULL,
	"SERVICE_CONTEXT" Varchar2(100) NOT NULL,
	"OCSP_CONTEXT" Varchar2(100) NOT NULL
	)
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "PLATFORM_AFIRMA" ADD CONSTRAINT "ID_PLATFORM_AFIRMA" PRIMARY KEY ("ID_PLATFORM_AFIRMA");
ALTER TABLE "PLATFORM_AFIRMA" ADD CONSTRAINT "PLATFORM_AFIRMA_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "PLATFORM_AFIRMA" IS 'Tabla que almacena la información relativa a la conexión con plataformas @Firma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."ID_PLATFORM_AFIRMA" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."NAME" IS 'Valor que representa el nombre único de la plataforma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."HOST" IS 'Valor que representa el host para la conexión con la plataforma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."PORT" IS 'Valor que representa el puerto para la conexión con la plataforma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."SERVICE_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso a los servicios web SOAP.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."OCSP_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso al servicio OCSP.';

-- Table PLATFORM_TSA
CREATE TABLE "PLATFORM_TSA" ( 
	"ID_PLATFORM_TSA" NUMBER(19) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"HOST" Varchar2(100) NOT NULL,
	"PORT" Varchar2(10) NOT NULL,
	"SERVICE_CONTEXT" Varchar2(100) NOT NULL,
	"RFC3161_CONTEXT" Varchar2(100) NOT NULL,
	"RFC3161_PORT" NUMBER(10) NOT NULL,
	"SSL_TRUSTSTORE" NUMBER(19) NOT NULL,
	"AUTH_KEYSTORE" NUMBER(19) NOT NULL
	)
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "PLATFORM_TSA" ADD CONSTRAINT "ID_PLATFORM_TSA" PRIMARY KEY ("ID_PLATFORM_TSA");
ALTER TABLE "PLATFORM_TSA" ADD CONSTRAINT "PLATFORM_TSA_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "PLATFORM_TSA" IS 'Tabla que almacena la información relativa a la conexión con plataformas TS@.';
COMMENT ON COLUMN "PLATFORM_TSA"."ID_PLATFORM_TSA" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "PLATFORM_TSA"."NAME" IS 'Valor que representa el nombre único de la plataforma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."HOST" IS 'Valor que representa el host para la conexión con la plataforma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."PORT" IS 'Valor que representa el puerto para la conexión con la plataforma.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."SERVICE_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso a los servicios web SOAP.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."RFC3161_CONTEXT" IS 'Valor que representa la parte de la ruta de acceso al servicio RFC3161.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."SSL_TRUSTSTORE" IS 'Valor que representa el identificador del keystore para establecer la conexión SSL con TS@.';
COMMENT ON COLUMN "PLATFORM_AFIRMA"."AUTH_KEYSTORE" IS 'Valor que representa el identificador del keystore para la conexión autenticada con el servicio RFC3161.';

-- Table SERVICE_MONITORIZA
CREATE TABLE "SERVICE_MONITORIZA" (
	"ID_SERVICE" Number(19,0) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"SERVICE_TIMEOUT" Number(19,0) NOT NULL,
	"NAME_ENDPOINT_WSDL" Varchar2(100) NOT NULL,
	"DEGRADED_THRESHOLD" Number(19,0) NOT NULL,
	"LOST_THRESHOLD" Number(19,0) NOT NULL,
	"ID_TIMER_SERVICE" Number(19,0) NOT NULL,
	"ID_ALARM_SERVICE" Number(19,0) NOT NULL
	"ID_PLATFORM_AFIRMA_SERVICE" Number(19,0),
	"ID_PLATFORM_TSA_SERVICE" Number(19,0)
)
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
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


-- Table ALARM_MONITORIZA
CREATE TABLE "ALARM_MONITORIZA" (
	"ID_ALARM" Number(19,0) NOT NULL,
	"NAME" Varchar2(100) NOT NULL,
	"SCOPE" Varchar2(10) NOT NULL,
	"BLOCK_TIME" Number(19,0) NOT NULL
)
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "ALARM_MONITORIZA" ADD CONSTRAINT "ID_ALARM" PRIMARY KEY ("ID_ALARM");
ALTER TABLE "ALARM_MONITORIZA" ADD CONSTRAINT "ALARM_NAME_UNIQUE" UNIQUE ("NAME");
COMMENT ON TABLE "ALARM_MONITORIZA" IS 'Tabla que almacena la información relativa a las alarmas de monitoriz@.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."ID_ALARM" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."NAME" IS 'Valor que representa el nombre único de la alarma.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."SCOPE" IS 'Valor que representa el ámbito de la alarma: degradado, caído, ambos.';
COMMENT ON COLUMN "ALARM_MONITORIZA"."BLOCK_TIME" IS 'Valor que representa el tiempo en milisegundos que se bloquean las alarmas de este tipo antes de lanzar un resumen.';

-- Table MAIL_MONITORIZA
CREATE TABLE "MAIL_MONITORIZA" (
	"ID_MAIL" Number(19,0) NOT NULL,
	"EMAIL_ADDRESS" Varchar2(10) NOT NULL,
	"ID_ALARM_MAIL" Number(19,0) NOT NULL
)
TABLESPACE CONFIGURACION_TABLESPACE INITRANS 1 MAXTRANS 255 NOCACHE;
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "ID_MAIL" PRIMARY KEY ("ID_MAIL");
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "MAIL_ADDRESS_UNIQUE" UNIQUE ("EMAIL_ADDRESS");
COMMENT ON TABLE "MAIL_MONITORIZA" IS 'Tabla que almacena las direcciones de correo electrónico a utilizar por las alarmas de monitoriz@.';
COMMENT ON COLUMN "MAIL_MONITORIZA"."ID_MAIL" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "MAIL_MONITORIZA"."EMAIL_ADDRESS" IS 'Valor que representa una dirección de correo electrónico.';


ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_TIMER_SERVICE" FOREIGN KEY ("ID_TIMER_SERVICE") REFERENCES "TIMER_MONITORIZA" ("ID_TIMER");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_ALARM_SERVICE" FOREIGN KEY ("ID_ALARM_SERVICE") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_AFIRMA_SERVICE" FOREIGN KEY ("ID_PLATFORM_AFIRMA_SERVICE") REFERENCES "PLATFORM_AFIRMA" ("ID_PLATFORM_AFIRMA");
ALTER TABLE "SERVICE_MONITORIZA" ADD CONSTRAINT "R_TSA_SERVICE" FOREIGN KEY ("ID_PLATFORM_TSA_SERVICE") REFERENCES "PLATFORM_TSA" ("ID_PLATFORM_TSA");
--ALTER TABLE "PLATFORM_TSA" ADD CONSTRAINT "R_TRUSTORE_SERVICE" FOREIGN KEY ("SSL_TRUSTSTORE") REFERENCES "KEYSTORE_MONITORIZA" ("ID_KEYSTORE");
--ALTER TABLE "PLATFORM_TSA" ADD CONSTRAINT "R_AUTH_KEYSTORE_SERVICE" FOREIGN KEY ("AUTH_KEYSTORE") REFERENCES "KEYSTORE_MONITORIZA" ("ID_KEYSTORE");
ALTER TABLE "MAIL_MONITORIZA" ADD CONSTRAINT "R_MAIL_ALARM" FOREIGN KEY ("ID_ALARM_SERVICE") REFERENCES "ALARM_MONITORIZA" ("ID_ALARM");




COMMIT;