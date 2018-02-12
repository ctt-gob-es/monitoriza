/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.constant.ServiceNames.java.</p>
 * <b>Description:</b><p> Interface that defines the  name of services defined in @Firma.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/01/2018.
 */
package es.gob.monitoriza.constant;


/** 
 * <p>Interface that defines the  name of services defined in @Firma.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24/01/2018.
 */
public interface AfirmaServiceNames {
	
	/**
	 * Constant that represent the name of the service 'ObtenerInfoCertificado'.
	 */
	String OBTENER_INFO_CERTIFICADO = "ObtenerInfoCertificado";

	/**
	 * Constant that represent the name of the service 'DSSAfirmaArchiveRetrieval'.
	 */
	String DSS_AFIRMA_ARCHIVE_RETRIEVAL = "DSSAfirmaArchiveRetrieval";

	/**
	 * Constant that represent the name of the service 'ObtenerIdDocumento'.
	 */
	String OBTENER_ID_DOCUMENTO = "ObtenerIdDocumento";

	/**
	 * Constant that represent the name of the service 'GetInfoCertificate'.
	 */
	String GET_INFO_CERTIFICATE = "GetInfoCertificate";

	/**
	 * Constant that represent the name of the service 'ServerSignatureCounterSign'.
	 */
	String SERVER_SIGNATURE_COUNTER_SIGN = "ServerSignatureCounterSign";

	/**
	 * Constant that represent the name of the service 'DSSBatchVerifySignature'.
	 */
	String DSS_BATCH_VERIFY_SIGNATURE = "DSSBatchVerifySignature";

	/**
	 * Constant that represent the name of the service 'ObtenerFirmaTransaccion'.
	 */
	String OBTENER_FIRMA_TRANSACCION = "ObtenerFirmaTransaccion";

	/**
	 * Constant that represent the name of the service 'GetDocumentContent'.
	 */
	String GET_DOCUMENT_CONTENT = "GetDocumentContent";

	/**
	 * Constant that represent the name of the service 'DSSAfirmaSign'.
	 */
	String DSS_AFIRMA_SIGN = "DSSAfirmaSign";

	/**
	 * Constant that represent the name of the service 'DSSBatchVerifyCertificate'.
	 */
	String DSS_BATCH_VERIFY_CERTIFICATE = "DSSBatchVerifyCertificate";

	/**
	 * Constant that represent the name of the service 'SignatureValidation'.
	 */
	String SIGNATURE_VALIDATION = "SignatureValidation";

	/**
	 * Constant that represent the name of the service 'DSSAfirmaVerify'.
	 */
	String DSS_AFIRMA_VERIFY = "DSSAfirmaVerify";

	/**
	 * Constant that represent the name of the service 'AlmacenarDocumento'.
	 */
	String ALMACENAR_DOCUMENTO = "AlmacenarDocumento";

	/**
	 * Constant that represent the name of the service 'AdminDelegada'.
	 */
	String ADMIN_DELEGADA = "AdminDelegada";

	/**
	 * Constant that represent the name of the service 'GetESignature'.
	 */
	String GET_E_SIGNATURE = "GetESignature";

	/**
	 * Constant that represent the name of the service 'DSSAsyncRequestStatus'.
	 */
	String DSS_ASYNC_REQUEST_STATUS = "DSSAsyncRequestStatus";

	/**
	 * Constant that represent the name of the service 'DeleteDocumentContent'.
	 */
	String DELETE_DOCUMENT_CONTENT = "DeleteDocumentContent";

	/**
	 * Constant that represent the name of the service 'ServerSignatureCoSign'.
	 */
	String SERVER_SIGNATURE_CO_SIGN = "ServerSignatureCoSign";

	/**
	 * Constant that represent the name of the service 'ServerSignature'.
	 */
	String SERVER_SIGNATURE = "ServerSignature";

	/**
	 * Constant that represent the name of the service 'ObtenerContenidoDocumentoId'.
	 */
	String OBTENER_CONTENIDO_DOCUMENTO_ID = "ObtenerContenidoDocumentoId";

	/**
	 * Constant that represent the name of the service 'FirmaServidorCoSign'.
	 */
	String FIRMA_SERVIDOR_CO_SIGN = "FirmaServidorCoSign";

	/**
	 * Constant that represent the name of the service 'EliminarContenidoDocumento'.
	 */
	String ELIMINAR_CONTENIDO_DOCUMENTO = "EliminarContenidoDocumento";

	/**
	 * Constant that represent the name of the service 'ValidarCertificado'.
	 */
	String VALIDAR_CERTIFICADO = "ValidarCertificado";

	/**
	 * Constant that represent the name of the service 'GetDocId'.
	 */
	String GET_DOC_ID = "GetDocId";

	/**
	 * Constant that represent the name of the service 'FirmaServidorCounterSign'.
	 */
	String FIRMA_SERVIDOR_COUNTER_SIGN = "FirmaServidorCounterSign";

	/**
	 * Constant that represent the name of the service 'StoreDocument'.
	 */
	String STORE_DOCUMENT = "StoreDocument";

	/**
	 * Constant that represent the name of the service 'GetDocumentContentByDocId'.
	 */
	String GET_DOCUMENT_CONTENT_BY_DOC_ID = "GetDocumentContentByDocId";

	/**
	 * Constant that represent the name of the service 'ObtenerContenidoDocumento'.
	 */
	String OBTENER_CONTENIDO_DOCUMENTO = "ObtenerContenidoDocumento";

	/**
	 * Constant that represent the name of the service 'DSSAfirmaVerifyCertificate'.
	 */
	String DSS_AFIRMA_VERIFY_CERTIFICATE = "DSSAfirmaVerifyCertificate";

	/**
	 * Constant that represent the name of the service 'FirmaServidor'.
	 */
	String FIRMA_SERVIDOR = "FirmaServidor";

	/**
	 * Constant that represent the name of the service 'ValidarFirma'.
	 */
	String VALIDAR_FIRMA = "ValidarFirma";

	/**
	 * Constant that represent the name of the service 'ValidateCertificate'.
	 */
	String VALIDATE_CERTIFICATE = "ValidateCertificate";
}
