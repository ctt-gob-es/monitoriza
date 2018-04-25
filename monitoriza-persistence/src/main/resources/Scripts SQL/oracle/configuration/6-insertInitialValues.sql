-- ************************************************************
-- *** Creación de los valores en las tablas de constantes  ***
-- ************************************************************
   
-- TABLA USER_MONITORIZA 
   
Insert into USER_MONITORIZA
   (ID_USER_MONITORIZA, LOGIN, PASSWORD, NAME, SURNAMES, 
    EMAIL, IS_BLOCKED, ATTEMPTS_NUMBER)
 Values
   (1, 'admin', 'jLIjfQZ5yojbZGTqxg2pY0VROWQ=', 'admin', 'admin', 'admin@admin.com', 'N', 0);
 
-- TABLA C_PLATFORM_TYPE 
   Insert into C_PLATFORM_TYPE
   (ID_PLATFORM_TYPE, NAME)
 Values
   (1, '@Firma');
   (2, 'TS@'):

COMMIT;
