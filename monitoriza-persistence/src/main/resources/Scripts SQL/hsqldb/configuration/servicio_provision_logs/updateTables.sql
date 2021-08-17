-- Se añade la monitorización del SPIE de métodos de validación
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlValMethodsResolver' WHERE ID_SPIE_TYPE = 4;   
   
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