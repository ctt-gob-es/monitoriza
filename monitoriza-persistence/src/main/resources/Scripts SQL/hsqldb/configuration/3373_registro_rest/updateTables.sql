-- Monitorización del SPIE Validation Methods
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlValMethodsResolver' WHERE ID_SPIE_TYPE = 4;
UPDATE SPIE_TYPE SET SEMAPHORE_LEVEL = 2 WHERE ID_SPIE_TYPE = 4

-- Table SYSTEM_NOTIFICATION
CREATE TABLE "SYSTEM_NOTIFICATION" ( 
	"ID_SYSTEM_NOTIFICATION" NUMERIC(19) NOT NULL,
	"ID_NOTIFICATION_PRIORITY" NUMERIC(19) NOT NULL,
	"DESCRIPTION" VARCHAR(300) NOT NULL,
	"IS_OK" CHAR(1) DEFAULT 'N' NOT NULL,
	"ID_NOTIFICATION_TYPE" NUMERIC(19) NOT NULL,
	"ID_NOTIFICATION_ORIGIN" NUMERIC(19) NOT NULL,
	"CREATION_DATE" TIMESTAMP NOT NULL,
	"EXPIRATION_DATE" TIMESTAMP
	);
ALTER TABLE "SYSTEM_NOTIFICATION" ADD CONSTRAINT "ID_SYSTEM_NOTIFICATION_CONS" PRIMARY KEY ("ID_SYSTEM_NOTIFICATION");
COMMENT ON TABLE "SYSTEM_NOTIFICATION" IS 'Tabla que almacena la información relativa a los avisos del sistema.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."ID_SYSTEM_NOTIFICATION" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."ID_NOTIFICATION_PRIORITY" IS 'Identificador del nivel de prioridad del aviso de sistema.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."ID_NOTIFICATION_TYPE" IS 'Identificador del tipo de notificación asociado.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."ID_NOTIFICATION_ORIGIN" IS 'Identificador del origen de notificación asociado.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."DESCRIPTION" IS 'Descripción de la notificación.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."IS_OK" IS 'Indica si la notificación se da por vista.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."CREATION_DATE" IS 'Fecha de creación del aviso.';
COMMENT ON COLUMN "SYSTEM_NOTIFICATION"."EXPIRATION_DATE" IS 'Fecha de caducidad del aviso.';

-- Table C_PLATFORM_TYPE
CREATE TABLE "C_NOTIFICATION_TYPE" ( 
	"ID_NOTIFICATION_TYPE" NUMERIC(19) NOT NULL,
	"TOKEN_NAME" VARCHAR(100) NOT NULL
);
ALTER TABLE "C_NOTIFICATION_TYPE" ADD CONSTRAINT "ID_NOTIFICATION_TYPE_CONS	" PRIMARY KEY ("ID_NOTIFICATION_TYPE");
COMMENT ON TABLE "C_NOTIFICATION_TYPE" IS 'Tabla que almacena los tipos de notificationes en Monitoriz@.';

-- TABLA C_NOTIFICATION_TYPE 
   Insert into C_NOTIFICATION_TYPE
   (ID_NOTIFICATION_TYPE, TOKEN_NAME)
 Values
   (1, 'NOTIFICATION_TYPE_CONFIG');

   Insert into C_NOTIFICATION_TYPE
   (ID_NOTIFICATION_TYPE, TOKEN_NAME)
 Values
   (2, 'NOTIFICATION_TYPE_NODE');
   
   Insert into C_NOTIFICATION_TYPE
   (ID_NOTIFICATION_TYPE, TOKEN_NAME)
 Values
   (3, 'NOTIFICATION_TYPE_LOG');
   
-- Table C_NOTIFICATION_ORIGIN
CREATE TABLE "C_NOTIFICATION_ORIGIN" ( 
	"ID_NOTIFICATION_ORIGIN" NUMERIC(19) NOT NULL,
	"TOKEN_NAME" VARCHAR(100) NOT NULL
);
ALTER TABLE "C_NOTIFICATION_ORIGIN" ADD CONSTRAINT "ID_NOTIFICATION_ORIGIN_CONS	" PRIMARY KEY ("ID_NOTIFICATION_ORIGIN");
COMMENT ON TABLE "C_NOTIFICATION_ORIGIN" IS 'Tabla que almacena los orígenes de notificationes en Monitoriz@.';

   Insert into C_NOTIFICATION_ORIGIN
   (ID_NOTIFICATION_ORIGIN, TOKEN_NAME)
 Values
   (1, 'CONFIG_VALIDATION_SERVICE_ORIGIN');
   
   Insert into C_NOTIFICATION_ORIGIN
   (ID_NOTIFICATION_ORIGIN, TOKEN_NAME)
 Values
   (2, 'CONFIG_MAIL_SERVER_ORIGIN');
   
   Insert into C_NOTIFICATION_ORIGIN
   (ID_NOTIFICATION_ORIGIN, TOKEN_NAME)
 Values
   (3, 'CONFIG_SPIE_GENERAL_ORIGIN');
   
   Insert into C_NOTIFICATION_ORIGIN
   (ID_NOTIFICATION_ORIGIN, TOKEN_NAME)
 Values
   (4, 'REST_SERVICE_NODE_ORIGIN');
   
   Insert into C_NOTIFICATION_ORIGIN
   (ID_NOTIFICATION_ORIGIN, TOKEN_NAME)
 Values
   (5, 'REST_SERVICE_LOG_ORIGIN');
   
  
-- Table C_NOTIFICATION_PRIORITY
CREATE TABLE "C_NOTIFICATION_PRIORITY" ( 
	"ID_NOTIFICATION_PRIORITY" NUMERIC(19) NOT NULL,
	"TOKEN_NAME" VARCHAR(100) NOT NULL,
	"LEVEL" NUMERIC(1) NOT NULL
);
ALTER TABLE "C_NOTIFICATION_PRIORITY" ADD CONSTRAINT "ID_NOTIFICATION_PRIORITY_CONS" PRIMARY KEY ("ID_NOTIFICATION_PRIORITY");
COMMENT ON TABLE "C_NOTIFICATION_PRIORITY" IS 'Tabla que almacena los niveles de prioridad de los avisos del sistema.';

   Insert into C_NOTIFICATION_PRIORITY
   (ID_NOTIFICATION_PRIORITY, TOKEN_NAME, LEVEL)
 Values
   (1, 'PRIORITY_LEVEL_IMPORTANT', 0);
   
   Insert into C_NOTIFICATION_PRIORITY
   (ID_NOTIFICATION_PRIORITY, TOKEN_NAME, LEVEL)
 Values
   (2, 'PRIORITY_LEVEL_NORMAL', 1);
   
   Insert into C_NOTIFICATION_PRIORITY
   (ID_NOTIFICATION_PRIORITY, TOKEN_NAME, LEVEL)
 Values
   (3, 'PRIORITY_LEVEL_LOW', 2);
   
   -- Table MAINTENANCE_SERVICE
CREATE TABLE "MAINTENANCE_SERVICE" ( 
	"ID_MAINTENANCE_SERVICE" NUMERIC(19) NOT NULL,
	"SERVICE" VARCHAR(100) NOT NULL,
	"IS_INMAINTENANCE" CHAR(1) DEFAULT 'N' NOT NULL,
	"STATUS_ORIGIN" NUMERIC(1) NOT NULL
	
);
ALTER TABLE "MAINTENANCE_SERVICE" ADD CONSTRAINT "ID_MAINTENANCE_SERVICE_CONS" PRIMARY KEY ("ID_MAINTENANCE_SERVICE");
COMMENT ON TABLE "MAINTENANCE_SERVICE" IS 'Tabla que almacena los servicios monitorizados que han sido marcados "en mantenimiento" por el usuario.';
COMMENT ON COLUMN "MAINTENANCE_SERVICE"."ID_MAINTENANCE_SERVICE" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "MAINTENANCE_SERVICE"."SERVICE" IS 'Nombre identificativo del servicio que ha generado la alerta.';
COMMENT ON COLUMN "MAINTENANCE_SERVICE"."IS_INMAINTENANCE" IS 'Indica si el servicio ha sido marcado como "en mantenimiento".';
COMMENT ON COLUMN "MAINTENANCE_SERVICE"."STATUS_ORIGIN" IS 'Valor del estado original (ambar, rojo) que tenía el servicio antes de marcarse.';

ALTER TABLE "SYSTEM_NOTIFICATION" ADD CONSTRAINT "ID_NOTIFICATION_TYPE_FK" FOREIGN KEY ("ID_NOTIFICATION_TYPE") REFERENCES "C_NOTIFICATION_TYPE" ("ID_NOTIFICATION_TYPE");
ALTER TABLE "SYSTEM_NOTIFICATION" ADD CONSTRAINT "ID_NOTIFICATION_ORIGIN_FK" FOREIGN KEY ("ID_NOTIFICATION_ORIGIN") REFERENCES "C_NOTIFICATION_ORIGIN" ("ID_NOTIFICATION_ORIGIN");
ALTER TABLE "SYSTEM_NOTIFICATION" ADD CONSTRAINT "ID_NOTIFICATION_PRIORITY_FK" FOREIGN KEY ("ID_NOTIFICATION_PRIORITY") REFERENCES "C_NOTIFICATION_PRIORITY" ("ID_NOTIFICATION_PRIORITY");

  -- Secuencia necesaria para la clave de la tabla SQ_SYSTEM_NOTIFICATION
CREATE SEQUENCE SQ_SYSTEM_NOTIFICATION AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  
    -- Secuencia necesaria para la clave de la tabla SQ_MAINTENANCE_SERVICE
CREATE SEQUENCE SQ_MAINTENANCE_SERVICE AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;

   -- Table SPL_MONITORIZA
CREATE TABLE "SPL_MONITORIZA" ( 
	"ID_SPL" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(30) NOT NULL,
	"DESCRIPTION" VARCHAR(255) NOT NULL,
	"TYPE" VARCHAR(30) NOT NULL,
	"URL" VARCHAR(255) NOT NULL,
	"KEY" VARCHAR(100) NOT NULL
	);
ALTER TABLE "SPL_MONITORIZA" ADD CONSTRAINT "ID_SPL_CONS" PRIMARY KEY ("ID_SPL");
COMMENT ON TABLE "SPL_MONITORIZA" IS 'Tabla que almacena la información de los servicios de provisión de logs configurados.';
COMMENT ON COLUMN "SPL_MONITORIZA"."ID_SPL" IS 'Identificador de la tabla.';
COMMENT ON COLUMN "SPL_MONITORIZA"."NAME" IS 'Nombre del servicio.';
COMMENT ON COLUMN "SPL_MONITORIZA"."DESCRIPTION" IS 'Descripción de los logs que se proporcionan.';
COMMENT ON COLUMN "SPL_MONITORIZA"."TYPE" IS 'Tipo de logs que se recuperan.';
COMMENT ON COLUMN "SPL_MONITORIZA"."URL" IS 'URL del servicio de provisión de logs.';
COMMENT ON COLUMN "SPL_MONITORIZA"."KEY" IS 'Clave AES codificada en base 64 para autenticarse ante el servicio.';

  -- Secuencia necesaria para la clave de la tabla SPL_MONITORIZA
CREATE SEQUENCE SQ_SPL_MONITORIZA AS NUMERIC(19)
  START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE NO CYCLE;
  