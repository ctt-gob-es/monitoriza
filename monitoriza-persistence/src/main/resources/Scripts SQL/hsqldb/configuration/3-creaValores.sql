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
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE, VERSION)
 Values
 (1,'TrustStoreSSL', 'KEYSTORE01', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS', 0);
 
Insert into KEYSTORE
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE, VERSION)
 Values
 (2,'AuthClientRFC3161', 'KEYSTORE02', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS', 0);
 
Insert into KEYSTORE
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE, VERSION)
 Values
 (3,'UserStore', 'KEYSTORE03', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS', 0);
 
 Insert into KEYSTORE
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE, VERSION)
 Values
 (4,'ValidServiceStore', 'KEYSTORE04', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS', 0);
 
 -- TABLA C_STATUS_CERTIFICATES
   
Insert into C_STATUS_CERTIFICATES
   (ID_STATUS_CERTIFICATE, TOKEN_NAME)
 Values
   (1, 'Válido');
   
Insert into C_STATUS_CERTIFICATES
   (ID_STATUS_CERTIFICATE, TOKEN_NAME)
 Values
   (2, 'Caducado');
   
Insert into C_STATUS_CERTIFICATES
   (ID_STATUS_CERTIFICATE, TOKEN_NAME)
 Values
   (3, 'Revocado');
   
Insert into C_STATUS_CERTIFICATES
   (ID_STATUS_CERTIFICATE, TOKEN_NAME)
 Values
   (4, 'Desconocido');
   
Insert into C_STATUS_CERTIFICATES
   (ID_STATUS_CERTIFICATE, TOKEN_NAME)
 Values
   (5, 'Aún no válido');
   
Insert into C_STATUS_CERTIFICATES
   (ID_STATUS_CERTIFICATE, TOKEN_NAME)
 Values
   (6, 'No válido');
   
-- TABLA C_STATUS_CERTIFICATES
   
Insert into C_AUTHENTICATION_TYPE
   (ID_AUTHENTICATION_TYPE, TOKEN_NAME)
 Values
   (1, 'Sin autenticación');
   
Insert into C_AUTHENTICATION_TYPE
   (ID_AUTHENTICATION_TYPE, TOKEN_NAME)
 Values
   (2, 'Usuario/Password');
   
Insert into C_AUTHENTICATION_TYPE
   (ID_AUTHENTICATION_TYPE, TOKEN_NAME)
 Values
   (3, 'Certificado');
   
 -- TABLA ALARM 
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_001', 'Nodo de @firma sin conexión con TS@');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_002', 'Nodo de @firma sin conexión con HSM');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_003', 'Nodo de @firma con método de validación en estado no correcto');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_004', 'Nodo de @firma con servicio degradado');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_005', 'Nodo de @firma en modo de emergencia');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_006', 'Nodo de TS@ sin conexión con HSM');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_007', 'Nodo de TS@ sin conexión con @firma');
Insert into ALARM (ID_ALARM, DESCRIPTION) Values ('ALM_008', 'Nodo de TS@ en modo de emergencia');

 -- TABLA SPIE_TYPE 
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (1, 'HSM Connection', '/spie/checkStatusInf?opCodes=4', 1, 1);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (2, 'Emergency mode', '/spie/checkStatusInf?opCodes=2', 1, 1);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (3, 'TS@ Connection', '/spie/checkStatusInf?opCodes=5', 1, 2);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (4, 'Validation methods', '/spie/checkStatusInf?opCodes=6', 1, 1);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (5, 'Response times', '/spie/checkStatusInf?opCodes=7', 1, 1);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (6, 'HSM Connection', '/spie/checkStatusInf?opCodes=4', 2, 1);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (7, 'Emergency mode', '/spie/checkStatusInf?opCodes=2', 2, 1);
Insert into SPIE_TYPE (ID_SPIE_TYPE, TOKENNAME, CONTEXT, ID_PLATFORM_TYPE, SEMAPHORE_LEVEL) Values (8, '@Firma Connection', '/spie/checkStatusInf?opCodes=5', 2, 2);