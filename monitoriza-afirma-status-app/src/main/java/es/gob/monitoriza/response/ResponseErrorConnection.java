/* 
* Este fichero forma parte de la plataforma TS@. 
* La plataforma TS@ es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2017 Gobierno de España
* Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
* condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este 
* fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
*/

/** 
 * <b>File:</b><p>es.gob.tsa.response.impl.ResponseErrorConnection.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Time Stamping Authority.</p>
 * <b>Date:</b><p>20/10/2017.</p>
 * @author Gobierno de España.
 * @version 1.0, 20/10/2017.
 */
package es.gob.monitoriza.response;

import static j2html.TagCreator.body;
import static j2html.TagCreator.caption;
import static j2html.TagCreator.div;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.table;
import static j2html.TagCreator.title;

import java.util.LinkedList;
import java.util.List;

import j2html.tags.Tag;

/**<p>Class that represents the response for a error request.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 20/10/2017.
 */
public class ResponseErrorConnection {

	public static String ERROR_MESSAGE = "AN ERROR OCCURRED IN THE SERVICE INVOCATION. CHECK LOG.";

	/**
	 * Constructor method for the class ResponseErrorConnection.java. 
	 */
	public ResponseErrorConnection() {
	}

	/**
	 * Method that renders the HTML code for the result response.
	 * @param operations List that represents response results for the requested tests.
	 * @return String that represents the HTML code of the result
	 * @throws Exception
	 */
	public static String render() throws Exception {
				
        return html().with(makeHead(), makeBody()).render(); 
    } 
		
	/**
	 * Method that build the HTML head for the response.
	 * @return Tag that represents the HTML head
	 */
	private static Tag<?> makeHead() {
        return head().with(title("Monitoriz@")).withCharset("UTF-8").withType("text/css").with(style("table, th, td { border:1px solid black } table { border-spacing:0px; padding:0px; min-width: 400px } th, td { padding:2px } div { margin:20px }")); 
    } 
 
    /**
     * Method that builds the HTML body for the response.
     * @param operations List that represents response results for the requested tests. 
     * @return Tag that represents the HTML body
     */
    private static Tag<?> makeBody() {
    	    	
        return body().with(makeDivOperations()); 
    }
    
   
    /**
     * Method that builds the HTML divs for each of the operation result.
     * @param operations List that represents response results for the requested tests. 
     * @return Tag that represents the HTML divs for each of the operation result.
     */
    private static Tag<?> makeDivOperations() {
		    	  	
    	return div().with(makeTableResultOperation());
		
    }
        
	public static Tag<?> makeTableResultOperation() {

		List<Tag<?>> contents = new LinkedList<>();
		
		contents.add(makeTableCaption());
		
		return table().with(contents);
	}
	

	/**
	 * Method that builds the HTML caption for the result message.
	 * @return
	 */
	private static Tag<?> makeTableCaption() {
		final StringBuffer caption = new StringBuffer();
		caption.append(ERROR_MESSAGE);
		return caption(caption.toString());
	}

}
