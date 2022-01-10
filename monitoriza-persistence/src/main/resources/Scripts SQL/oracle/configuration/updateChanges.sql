ALTER TABLE ALERT_RESUMES ADD "LAST_SENT_TIME" DATE;

-- NUEVA TABLA "ALERT_SYSTEMS_TYPES" QUE RECOGE LOS TIPOS DE SISTEMAS DE NOTIFICACION.
-- SE AÑADE PARA PODER CONTEMPLAR SI EL SISTEMA PUEDE O NO RECIBIR RESÚMENES DE ALERTAS.
CREATE TABLE "ALERT_SYSTEMS_TYPES" (
	"SYSTEM_TYPE_ID" NUMERIC(19) NOT NULL,
	"NAME" VARCHAR(30) NOT NULL,
	"RESUME_ENABLED" CHAR(1) NOT NULL
);
ALTER TABLE "ALERT_SYSTEMS_TYPES" ADD CONSTRAINT "SYSTEM_TYPE_ID_CONS" PRIMARY KEY ("SYSTEM_TYPE_ID");
COMMENT ON TABLE "ALERT_SYSTEM_TYPE" IS 'Tabla que recoge los distintos sistemas de notificación.';
COMMENT ON COLUMN "ALERT_SYSTEM_TYPE"."SYSTEM_TYPE_ID" IS 'Identificador del tipo de sistema de notificación.';
COMMENT ON COLUMN "ALERT_SYSTEM_TYPE"."NAME" IS 'Nombre asignado al tipo de sistema de notificación.';
COMMENT ON COLUMN "ALERT_SYSTEM_TYPE"."RESUME_ENABLED" IS 'Valor S/N que indica si el tipo de sistema de notificación permite el envío de resúmenes.';

 -- TABLA ALERT_SYSTEM_TYPE
Insert into ALERT_SYSTEMS_TYPES (SYSTEM_TYPE_ID, NAME, RESUME_ENABLED) Values (1, 'EMAIL', 'S');
Insert into ALERT_SYSTEMS_TYPES (SYSTEM_TYPE_ID, NAME, RESUME_ENABLED) Values (2, 'GRAYLOG', 'N');

ALTER TABLE
   ALERT_SYSTEMS
rename COLUMN
   TYPE 
TO
   SYSTEM_TYPE_ID;
   
-- ANTES DE CAMBIAR EL TIPO, HAY QUE ELIMINAR DATOS
-- SERÁ NECESARIO GUARDAR LOS VALORES ANTERIORES
-- SEGURO QUE SE PUEDE HACER MEJOR CON ALGúN PROCEDIMIENTO ORACLE
UPDATE ALERT_SYSTEMS set SYSTEM_TYPE_ID = null;

-- SE MODIFICA EL TIPO PARA PODER CONVERTIRLO EN FK
ALTER TABLE
   ALERT_SYSTEMS
MODIFY
(
   SYSTEM_TYPE_ID    number(19)
);

-- REFERENCIAR A MANO SEGÚN LOS DATOS GUARDADOS ANTERIORMENTE
UPDATE ALERT_SYSTEMS set SYSTEM_TYPE_ID = ? WHERE SYSTEM_ID = ?;

-- CREAR LA REFERENCIA A TRAVÉS DE LA FK
ALTER TABLE "ALERT_SYSTEMS" ADD CONSTRAINT "SYSTEM_TYPE_ID_FK" FOREIGN KEY ("SYSTEM_TYPE_ID") REFERENCES "ALERT_SYSTEMS_TYPES" ("SYSTEM_TYPE_ID");

