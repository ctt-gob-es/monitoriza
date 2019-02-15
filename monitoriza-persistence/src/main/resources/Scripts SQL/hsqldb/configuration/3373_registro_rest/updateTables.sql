ALTER TABLE NODE_MONITORIZA ADD "ACTIVE" CHARACTER(1) DEFAULT '1' NOT NULL;
COMMENT ON COLUMN "NODE_MONITORIZA"."ACTIVE" IS 'Indica si el nodo está registrado y por lo tanto debe ser monitorizado.';
ALTER TABLE SPIE_TYPE ADD "RESOLVER_CLASS" VARCHAR(100);
COMMENT ON COLUMN "SPIE_TYPE"."RESOLVER_CLASS" IS 'Nombre de la clase que parseará el HTML del SPIE.';
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlHsmConnResolver' WHERE ID_SPIE_TYPE = 1;
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlEmergencyModeResolver' WHERE ID_SPIE_TYPE = 2;
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlPlatformConnResolver' WHERE ID_SPIE_TYPE = 3;
--UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl' WHERE ID_SPIE_TYPE = 4;
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlAvgResponseTimeResolver' WHERE ID_SPIE_TYPE = 5;
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlHsmConnResolver' WHERE ID_SPIE_TYPE = 6;
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlEmergencyModeResolver' WHERE ID_SPIE_TYPE = 7;
UPDATE SPIE_TYPE SET "RESOLVER_CLASS" = 'es.gob.monitoriza.spie.html.impl.HtmlPlatformConnResolver' WHERE ID_SPIE_TYPE = 8;

