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
 
Insert into KEYSTORE
   (ID_KEYSTORE, NAME, TOKEN_NAME, KEYSTORE, PASSWORD, KEYSTORE_TYPE)
 Values
 (3,'UserStore', 'KEYSTORE03', 'CECECECE00000002000000002F03597B85569C665FE07C832801E57EFF4A872C', 'UH2rpWNDX+RLltMuyFPirQ==', 'JCEKS');
 
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