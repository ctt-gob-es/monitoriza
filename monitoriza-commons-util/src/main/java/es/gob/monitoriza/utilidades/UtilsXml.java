/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2017 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.utilidades.UtilsXml.java.</p>
 * <b>Description:</b><p>Class that provides methods for managing xml.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>15/02/2019.</p>
 * @author Gobierno de España.
 * @version 1.6, 15/02/2019.
 */
package es.gob.monitoriza.utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * <p>Class that provides methods for managing xml.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.6, 15/02/2019.
 */
public final class UtilsXml {

	private static Logger LOGGER = Logger.getLogger(UtilsXml.class);

	public static Object[] getXmlValidation(String pathUri, String app, String certificate) {
		Object [] result = null;
		FileInputStream in = null;
		String xmlRequest = getStringXmlValidation(pathUri, app, certificate);
		try {
			result = Arrays.asList(xmlRequest).toArray();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return result;
	}
	
	public static String getStringXmlValidation(String pathUri, String app, String certificate) {
		Document document = null;
		String xmlString;
		try {
	        //Obtenemos el document del fichero XML existente
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        document = db.parse(pathUri);
	        document.getDocumentElement().normalize();
	        document.getElementsByTagName("dss:Name").item(0).setTextContent(app);
	        document.getElementsByTagName("ds:X509Certificate").item(0).setTextContent(certificate);
	        TransformerFactory transfac = TransformerFactory.newInstance();
	        transfac.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "xml");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));

			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(document.getDocumentElement());

			trans.transform(source, result);
			xmlString = sw.toString();
	        
	    } catch (Exception e) {
	    	xmlString = null;
	    }
		
		return xmlString;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.apache.commons.text.StringEscapeUtils#escapeXml10(java.lang.String)
	 */
	public static String escapeXml10(String textToEscape) {

		return StringEscapeUtils.escapeXml10(textToEscape);

	}

}
