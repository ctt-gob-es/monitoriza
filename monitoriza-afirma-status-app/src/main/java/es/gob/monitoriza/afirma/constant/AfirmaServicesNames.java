/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.main.AfirmaServicesNames.java.</p>
 * <b>Description:</b><p> Interface that contains the list of services offered by the @Firma platform.</p>
 * <b>Project:</b><p>Autotester.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.1, 11/1/2018.
 */
package es.gob.monitoriza.afirma.constant;


/** 
 * <p>Interface that contains the list of services offered by the @Firma platform.</p>
 * * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 12/01/2018.
 */
public interface AfirmaServicesNames {

	/**
	 * Constant that represents the @Firma service 'archiveRetrieval'.
	 */
	String DSS_ARCHIVE_RETRIEVAL = "archiveRetrieval";
	
	/**
	 * Constant that represents the @Firma service 'verifySignatures'.
	 */
	String DSS_BATCH_VERIFY_SIGNATURE  = "verifySignatures";
	
	/**
	 * Constant that represents the @Firma service 'sign'.
	 */
	String DSS_AFIRMA_SIGN = "sign";
	
	/**
	 * Constant that represents the @Firma service 'co-sign'.
	 */
	String DSS_AFIRMA_COSING = "sign";
	
	/**
	 * Constant that represents the @Firma service 'counter-sign'.
	 */
	String DSS_AFIRMA_COUNTERSIGN = "sign";
	
	/**
	 * Constant that represents the @Firma service 'verifyCertificates'.
	 */
	String DSS_BATCH_VERIFY_CERTIFICATE = "verifyCertificates";
	
	/**
	 * Constant that represents the @Firma service 'verify'.
	 */
	String DSS_AFIRMA_VALIDATION = "verify";
	
	/**
	 * Constant that represents the @Firma service 'getProcessResponse'.
	 */
	String DSS_ASYNC_REQUEST_STATUS  = "getProcessResponse";
	
	/**
	 * Constant that represents the @Firma service 'verify'.
	 */
	String DSS_AFIRMA_VERIFY_CERTIFICATE = "verify";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerInfoCertificado'.
	 */
	String OBTENER_INFO_CERTIFICADO = "ObtenerInfoCertificado";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerIdDocumento'.
	 */
	String OBTENER_ID_DOCUMENTO = "ObtenerIdDocumento";
	
	/**
	 * Constant that represents the @Firma service 'GetInfoCertificate'.
	 */
	String GET_INFO_CERTIFICATE = "GetInfoCertificate";
	
	/**
	 * Constant that represents the @Firma service 'ServerSignatureCounterSign'.
	 */
	String SERVER_SIGNATURE_COUNTER_SIGN = "ServerSignatureCounterSign";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerFirmaTransaccion'.
	 */
	String OBTENER_FIRMA_TRANSACCION  = "ObtenerFirmaTransaccion";

	/**
	 * Constant that represents the @Firma service 'GetDocumentContent'.
	 */
	String GET_DOCUMENT_CONTENT = "GetDocumentContent";
	
	/**
	 * Constant that represents the @Firma service 'SignatureValidation'.
	 */
	String SIGNATURE_VALIDATION = "SignatureValidation";
	
	/**
	 * Constant that represents the @Firma service 'AlmacenarDocumento'.
	 */
	String ALMACENAR_DOCUMENTO = "AlmacenarDocumento";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerConfiguracionDelegada'.
	 */
	String ADMIN_DELEGADA_OBTENER_CONF_DELEGADA = "ObtenerConfiguracionDelegada";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerInfoTransaccion'.
	 */
	String ADMIN_DELEGADA_OBTENER_INFO_TRANSACCION = "ObtenerInfoTransaccion";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerTransaccionesAplicacion'.
	 */
	String ADMIN_DELEGADA_OBTENER_TRANSACCIONES_APLICACION = "ObtenerTransaccionesAplicacion";
	
	/**
	 * Constant that represents the @Firma service 'EstablecerConfiguracionDelegada'.
	 */
	String ADMIN_DELEGADA_ESTABLECER_CONF_DELEGADA = "EstablecerConfiguracionDelegada";
	
	/**
	 * Constant that represents the @Firma service 'GetESignature'.
	 */
	String GET_E_SIGNATURE = "GetESignature";
	
	/**
	 * Constant that represents the @Firma service 'DeleteDocumentContent'.
	 */
	String DELETE_DOCUMENT_CONTENT = "DeleteDocumentContent";
	
	/**
	 * Constant that represents the @Firma service 'ServerSignatureCoSign'.
	 */
	String SERVER_SIGNATURE_CO_SIGN = "ServerSignatureCoSign";
	
	/**
	 * Constant that represents the @Firma service 'ServerSignature'.
	 */
	String SERVER_SIGNATURE = "ServerSignature";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerContenidoDocumentoId'.
	 */
	String OBTENER_CONTENIDO_DOCUMENTO_ID = "ObtenerContenidoDocumentoId";

	/**
	 * Constant that represents the @Firma service 'FirmaServidorCoSign'.
	 */
	String FIRMA_SERVIDOR_CO_SIGN = "FirmaServidorCoSign";
	
	/**
	 * Constant that represents the @Firma service 'EliminarContenidoDocumento'.
	 */
	String ELIMINAR_CONTENIDO_DOCUMENTO = "EliminarContenidoDocumento";
	
	/**
	 * Constant that represents the @Firma service 'ValidarCertificado'.
	 */
	String VALIDAR_CERTIFICADO = "ValidarCertificado";
	
	/**
	 * Constant that represents the @Firma service 'GetDocId'.
	 */
	String GET_DOC_ID = "GetDocId";
	
	/**
	 * Constant that represents the @Firma service 'FirmaServidorCounterSign'.
	 */
	String FIRMA_SERVIDOR_COUNTER_SIGN = "FirmaServidorCounterSign";
	
	/**
	 * Constant that represents the @Firma service 'StoreDocument'.
	 */
	String STORE_DOCUMENT = "StoreDocument";
	
	/**
	 * Constant that represents the @Firma service 'GetDocumentContentByDocId'.
	 */
	String GET_DOCUMENT_CONTENT_BY_DOC_ID = "GetDocumentContentByDocId";
	
	/**
	 * Constant that represents the @Firma service 'ObtenerContenidoDocumento'.
	 */
	String OBTENER_CONTENIDO_DOCUMENTO = "ObtenerContenidoDocumento";
	
	/**
	 * Constant that represents the @Firma service 'FirmaServidor'.
	 */
	String FIRMA_SERVIDOR = "FirmaServidor";
	
	/**
	 * Constant that represents the @Firma service 'ValidarFirma'.
	 */
	String VALIDAR_FIRMA = "ValidarFirma";
	
	/**
	 * Constant that represents the @Firma service 'ValidateCertificate'.
	 */
	String VALIDATE_CERTIFICATE = "ValidateCertificate";
}
