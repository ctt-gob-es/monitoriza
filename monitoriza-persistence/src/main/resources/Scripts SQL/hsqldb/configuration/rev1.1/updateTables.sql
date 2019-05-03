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

UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 2 WHERE ID_SPIE_TYPE = 1;
UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 2 WHERE ID_SPIE_TYPE = 2;
UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 3 WHERE ID_SPIE_TYPE = 3;
UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 2 WHERE ID_SPIE_TYPE = 5;
UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 2 WHERE ID_SPIE_TYPE = 6;
UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 2 WHERE ID_SPIE_TYPE = 7;
UPDATE SPIE_TYPE SET "SEMAPHORE_LEVEL" = 3 WHERE ID_SPIE_TYPE = 8;

 -- TABLA ALARM 
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC001' WHERE ID_ALARM = 'ALM_001';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC002' WHERE ID_ALARM = 'ALM_002';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC003' WHERE ID_ALARM = 'ALM_003';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC004' WHERE ID_ALARM = 'ALM_004';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC005' WHERE ID_ALARM = 'ALM_005';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC006' WHERE ID_ALARM = 'ALM_006';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC007' WHERE ID_ALARM = 'ALM_007';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC008' WHERE ID_ALARM = 'ALM_008';
UPDATE ALARM SET "DESCRIPTION" = 'ALARMDESC009' WHERE ID_ALARM = 'ALM_009';
UPDATE ALARM SET "ACTIVE" = 'Y';

ALTER TABLE CONF_SPIE ADD "UPDATE_AFIRMA" CHARACTER(1) DEFAULT 'N';
ALTER TABLE CONF_SPIE ADD "UPDATE_TSA" CHARACTER(1) DEFAULT 'N';

