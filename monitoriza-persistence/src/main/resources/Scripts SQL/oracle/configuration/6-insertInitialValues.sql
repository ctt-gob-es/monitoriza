-- ************************************************************
-- *** Creación de los valores en las tablas de constantes  ***
-- ************************************************************
   
-- TABLA USER_MONITORIZA 
   
Insert into USER_MONITORIZA
   (ID_USER_MONITORIZA, LOGIN, PASSWORD, NAME, SURNAMES, 
    EMAIL, IS_BLOCKED, ATTEMPTS_NUMBER)
 Values
   (1, 'admin', '$2a$04$THxVTEMMIVxHUxwjm9gKjetbxvPX/H9r54HtukxBTMcAXSrgDzCGK', 'admin', 'admin', 'admin@admin.com', 'N', 0);
 
-- TABLA C_PLATFORM_TYPE 
   Insert into C_PLATFORM_TYPE
   (ID_PLATFORM_TYPE, NAME)
 Values
   (1, '@Firma');

   Insert into C_PLATFORM_TYPE
   (ID_PLATFORM_TYPE, NAME)
 Values
   (2, 'TS@');



-- TABLA KEYSTORE
    
Insert into KEYSTORE
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE)
 Values
 (1,'TrustStoreSSL', 'KEYSTORE01', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS');
 
Insert into KEYSTORE
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE)
 Values
 (2,'AuthClientRFC3161', 'KEYSTORE02', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS');
 
 -- TABLA ALERT_SEVERITY
 
Insert into ALERT_SEVERITY (ID_SEVERITY_TYPE, NAME) Values (0, 'FATAL');
Insert into ALERT_SEVERITY (ID_SEVERITY_TYPE, NAME) Values (1, 'ERROR');
Insert into ALERT_SEVERITY (ID_SEVERITY_TYPE, NAME) Values (2, 'WARNING');
Insert into ALERT_SEVERITY (ID_SEVERITY_TYPE, NAME) Values (3, 'INFO');

 -- TABLAS DEL MODELO DE DATOS ESTADISTICOS PARA LA GESTION DE EVENTOS
 
 -- TABLA ALERT_DIM_LEVELS
 
Insert into ALERT_DIM_LEVELS 
  (LEVEL_ID, LEVEL_NAME) 
Values 
  (0, 'FATAL');

Insert into ALERT_DIM_LEVELS 
  (LEVEL_ID, LEVEL_NAME) 
Values 
  (1, 'ERROR');

Insert into ALERT_DIM_LEVELS 
  (LEVEL_ID, LEVEL_NAME) 
Values 
  (2, 'WARNING');

Insert into ALERT_DIM_LEVELS 
  (LEVEL_ID, LEVEL_NAME) 
Values 
  (3, 'INFO');

 -- TABLA ALERT_DIM_NODES

Insert into ALERT_DIM_NODES 
  (NODE_ID, NODE_NAME) 
Values 
  (1, 'SERVER.COM');

Insert into ALERT_DIM_NODES 
  (NODE_ID, NODE_NAME) 
Values 
  (2, 'HOST.COM');

 -- TABLA ALERT_DIM_TEMPLATES

Insert into ALERT_DIM_TEMPLATES 
  (TEMPLATE_ID, TEMPLATE_NAME) 
Values 
  (1, 'PLANTILLA DE EJEMPLO 1');

Insert into ALERT_DIM_TEMPLATES 
  (TEMPLATE_ID, TEMPLATE_NAME) 
Values 
  (2, 'PLANTILLA DE EJEMPLO 2');

 -- TABLA ALERT_DIM_TYPES

Insert into ALERT_DIM_TYPES 
  (TYPE_ID, TYPE_NAME) 
Values 
  (1, 'ALERTA DUMMY 1');

Insert into ALERT_DIM_TYPES 
  (TYPE_ID, TYPE_NAME) 
Values 
  (2, 'ALERTA DUMMY 2');

 -- TABLA ALERT_DIM_APPS

Insert into ALERT_DIM_APPS 
  (APP_ID, APP_NAME) 
Values 
  (1, 'APLICACION DE PRUEBA 1');

Insert into ALERT_DIM_APPS 
  (APP_ID, APP_NAME) 
Values 
  (2, 'ALERTA DE PRUEBA 2');

 -- TABLA ALERT_STATISTICS

Insert into ALERT_STATISTICS 
  (STATISTIC_ID, APP_ID, TYPE_ID, TEMPLATE_ID, NODE, SEVERITY, TIMESTAMP, OCURRENCY) 
Values 
  (1, 1, 1, 1, 1, 2, TO_DATE('2021/11/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 5);

Insert into ALERT_STATISTICS 
  (STATISTIC_ID, APP_ID, TYPE_ID, TEMPLATE_ID, NODE, SEVERITY, TIMESTAMP, OCURRENCY) 
Values 
  (2, 2, 2, 2, 2, 3, TO_DATE('2021/08/15 15:02:01', 'yyyy/mm/dd hh24:mi:ss'), 10);
