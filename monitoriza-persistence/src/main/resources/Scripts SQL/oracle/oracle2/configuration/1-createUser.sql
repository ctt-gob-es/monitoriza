
-- ********************************************************
-- **************** Creación de usuario *******************
-- ********************************************************

 DROP USER MONITORIZA_ORACLE CASCADE;
  CREATE USER MONITORIZA_ORACLE IDENTIFIED BY MONITORIZA_ORACLE;
  GRANT ALL PRIVILEGES TO MONITORIZA_ORACLE IDENTIFIED BY MONITORIZA_ORACLE;
