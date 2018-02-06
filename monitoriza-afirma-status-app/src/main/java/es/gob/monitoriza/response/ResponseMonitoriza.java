/*
 * Este fichero forma parte de la plataforma de @firma. La plataforma de @firma
 * es de libre distribución cuyo código fuente puede ser consultado y descargado
 * desde http://forja-ctt.administracionelectronica.gob.es Copyright 2017s
 * Gobierno de España Este fichero se distribuye bajo las licencias EUPL versión
 * 1.1 y GPL versión 3, o superiores, según las condiciones que figuran en el
 * fichero 'LICENSE.txt' que se acompaña. Si se distribuyera este fichero
 * individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b>
 * <p>
 * es.gob.afirma.spie.response.impl.ResponseSPIE.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that builds the global HTML response for the result of SPIE test.
 * </p>
 * <b>Project:</b>
 * <p>
 * Horizontal platform of validation services of multiPKI certificates and
 * electronic signature.
 * </p>
 * <b>Date:</b>
 * <p>
 * 13/02/2006.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 20/09/2017.
 */
package es.gob.monitoriza.response;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.gob.monitoriza.status.StatusHolder;
import j2html.tags.Tag;

/**
 * <p>Class that builds the global HTML response for the result of SPIE test.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/02/2018.
 */
public class ResponseMonitoriza {
	
	/**
	 * Constant attribute that represents
	 */
	public static final String RESULT_OK = "OK";
	
	/**
	 * Attribute that represents the name for this operation test
	 */
	public static final String OPERATION_NAME = "Status for @firma platform services";

	/**
	 * Attribute that represents the column header name for the schema
	 */
	public static final String HEADER_SERVICE_NAME = "Service Name";

	/**
	 * Attribute that represents the column header name for the test result
	 */
	public static final String HEADER_RESULT_STATUS = "Status";
			
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
    
    /* (non-Javadoc)
	 * @see es.gob.afirma.spie.response.ResponseSPIEOperation#makeTableResultOperation()
	 */
	public static Tag<?> makeTableResultOperation() {

		List<Tag<?>> contents = new LinkedList<>();
		
		contents.addAll(makeTableHeader());
		contents.addAll(makeTableRows());
		return table().with(contents);
	}


	/**
	 * Method that builds the HTML header for the result table.
	 * @return
	 */
	private static Collection<? extends Tag<?>> makeTableHeader() {
		List<Tag<?>> rowsHeader = new LinkedList<>();
		List<Tag<?>> headerGroup = new LinkedList<>();

		headerGroup.add(th(HEADER_SERVICE_NAME));
		headerGroup.add(th(HEADER_RESULT_STATUS));

		rowsHeader.add(tr().with(headerGroup));

		return rowsHeader;
	}

	/**
	 * Method that builds the HTML for the result rows.
	 * @return
	 */
	private static Collection<? extends Tag<?>> makeTableRows() {
		
		List<Tag<?>> tdGroup = null;
		List<Tag<?>> rows = new LinkedList<>();
		
		for (Map.Entry<String,String> entry : StatusHolder.getInstance().getCurrenttatusHolder().entrySet()) {
			tdGroup = new LinkedList<>();
			tdGroup.add(td(entry.getKey()));
			tdGroup.add(td(entry.getValue()));
			rows.add(tr().with(tdGroup));
		}

		return rows;
	}
		
}
