/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.constants.AfirmaWsdlServicesName.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Autotester</p>
 * <b>Date:</b><p>11/1/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 11/1/2018.
 */
package es.gob.monitoriza.afirma.constant;


/** 
 * <p>Interface that translate the name of the service to the name of the WSDL associate to the service.</p>
 * <b>Project:</b><p>Autotester.</p>
 * @version 1.0, 11/1/2018.
 */
public interface AfirmaWsdlServicesName {

	/**
	 * Name of the WSDL associate to the 'archiveRetrieval' service.
	 */
	String archiveRetrieval = "DSSAfirmaArchiveRetrieval";
	
	/**
	 * Name of the WSDL associate to the 'verifySignatures' service.
	 */
	String verifySignatures = "DSSBatchVerifySignature";
	
	/**
	 * Name of the WSDL associate to the 'sign' service.
	 */
	String sign = "DSSAfirmaSign";
	
	/**
	 * Name of the WSDL associate to the 'verifyCertificates' service.
	 */
	String verifyCertificates = "DSSBatchVerifyCertificate";

	/**
	 * Name of the WSDL associate to the 'verify' service.
	 */
	String verify = "DSSAfirmaVerify";

	/**
	 * Name of the WSDL associate to the 'getProcessResponse' service.
	 */
	String getProcessResponse = "DSSAsyncRequestStatus";

	/**
	 * Name of the WSDL associate to the 'ObtenerInfoCertificado' service.
	 */
	String ObtenerInfoCertificado = "ObtenerInfoCertificado";

	/**
	 * Name of the WSDL associate to the 'ObtenerIdDocumento' service.
	 */
	String ObtenerIdDocumento = "ObtenerIdDocumento";

	/**
	 * Name of the WSDL associate to the 'GetInfoCertificate' service.
	 */
	String GetInfoCertificate = "GetInfoCertificate";

	/**
	 * Name of the WSDL associate to the 'ServerSignatureCounterSign' service.
	 */
	String ServerSignatureCounterSign = "ServerSignatureCounterSign";

	/**
	 * Name of the WSDL associate to the 'ObtenerFirmaTransaccion' service.
	 */
	String ObtenerFirmaTransaccion = "ObtenerFirmaTransaccion";

	/**
	 * Name of the WSDL associate to the 'GetDocumentContent' service.
	 */
	String GetDocumentContent = "GetDocumentContent";

	/**
	 * Name of the WSDL associate to the 'SignatureValidation' service.
	 */
	String SignatureValidation = "SignatureValidation";

	/**
	 * Name of the WSDL associate to the 'AlmacenarDocumento' service.
	 */
	String AlmacenarDocumento = "AlmacenarDocumento";

	/**
	 * Name of the WSDL associate to the 'ObtenerConfiguracionDelegada' service.
	 */
	String ObtenerConfiguracionDelegada = "ObtenerConfiguracionDelegada";

	/**
	 * Name of the WSDL associate to the 'ObtenerInfoTransaccion' service.
	 */
	String ObtenerInfoTransaccion = "ObtenerInfoTransaccion";

	/**
	 * Name of the WSDL associate to the 'ObtenerTransaccionesAplicacion' service.
	 */
	String ObtenerTransaccionesAplicacion = "ObtenerTransaccionesAplicacion";

	/**
	 * Name of the WSDL associate to the 'EstablecerConfiguracionDelegada' service.
	 */
	String EstablecerConfiguracionDelegada = "EstablecerConfiguracionDelegada";

	/**
	 * Name of the WSDL associate to the 'GetESignature' service.
	 */
	String GetESignature = "GetESignature";

	/**
	 * Name of the WSDL associate to the 'DeleteDocumentContent' service.
	 */
	String DeleteDocumentContent = "DeleteDocumentContent";

	/**
	 * Name of the WSDL associate to the 'ServerSignatureCoSign' service.
	 */
	String ServerSignatureCoSign = "ServerSignatureCoSign";

	/**
	 * Name of the WSDL associate to the 'ServerSignature' service.
	 */
	String ServerSignature = "ServerSignature";

	/**
	 * Name of the WSDL associate to the 'ObtenerContenidoDocumentoId' service.
	 */
	String ObtenerContenidoDocumentoId = "ObtenerContenidoDocumentoId";

	/**
	 * Name of the WSDL associate to the 'FirmaServidorCoSign' service.
	 */
	String FirmaServidorCoSign = "FirmaServidorCoSign";

	/**
	 * Name of the WSDL associate to the 'EliminarContenidoDocumento' service.
	 */
	String EliminarContenidoDocumento = "EliminarContenidoDocumento";

	/**
	 * Name of the WSDL associate to the 'ValidarCertificado' service.
	 */
	String ValidarCertificado = "ValidarCertificado";

	/**
	 * Name of the WSDL associate to the 'GetDocId' service.
	 */
	String GetDocId = "GetDocId";

	/**
	 * Name of the WSDL associate to the 'FirmaServidorCounterSign' service.
	 */
	String FirmaServidorCounterSign = "FirmaServidorCounterSign";

	/**
	 * Name of the WSDL associate to the 'StoreDocument' service.
	 */
	String StoreDocument = "StoreDocument";
	
	/**
	 * Name of the WSDL associate to the 'GetDocumentContentByDocId' service.
	 */
	String GetDocumentContentByDocId = "GetDocumentContentByDocId";

	/**
	 * Name of the WSDL associate to the 'ObtenerContenidoDocumento' service.
	 */
	String ObtenerContenidoDocumento = "ObtenerContenidoDocumento";

	/**
	 * Name of the WSDL associate to the 'FirmaServidor' service.
	 */
	String FirmaServidor = "FirmaServidor";

	/**
	 * Name of the WSDL associate to the 'ValidarFirma' service.
	 */
	String ValidarFirma = "ValidarFirma";

	/**
	 * Name of the WSDL associate to the 'ValidateCertificate' service.
	 */
	String ValidateCertificate = "ValidateCertificate";
	
}
