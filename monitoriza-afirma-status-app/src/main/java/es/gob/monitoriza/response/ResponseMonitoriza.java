/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
 * This program is licensed and may be used, modified and redistributed under the  terms
 * of the European Public License (EUPL), either version 1.1 or (at your option)
 * any later version as soon as they are approved by the European Commission.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * more details.
 * You should have received a copy of the EUPL1.1 license
 * along with this program; if not, you may find it at
 * http:joinup.ec.europa.eu/software/page/eupl/licence-eupl
 ******************************************************************************/

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.response.ResponseMonitoriza.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that builds the global HTML response for the Monitoriz@ servlet call.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 13/02/2006.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 05/02/2018.
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

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.utilidades.GeneralUtils;
import j2html.tags.Tag;

/**
 * <p>Class that builds the global HTML response for the Monitoriz@ servlet call.</p>
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
	 * Attribute that represents the column header name for the test result
	 */
	public static final String HEADER_SYSTEM_NAME = "System";
			
	/**
	 * Method that renders the HTML code for the result response.
	 * @param operations List that represents response results for the requested tests.
	 * @return String that represents the HTML code of the result
	 * @throws Exception
	 */
	public static String render(final String platformFilter) throws Exception {
				
        return html().with(makeHead(), makeBody(platformFilter)).render(); 
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
    private static Tag<?> makeBody(final String platformFilter) {
    	    	
        return body().with(makeDivOperations(platformFilter)); 
    }
    
   
    /**
     * Method that builds the HTML divs for each of the operation result.
     * @param operations List that represents response results for the requested tests. 
     * @return Tag that represents the HTML divs for each of the operation result.
     */
    private static Tag<?> makeDivOperations(final String platformFilter) {
		    	  	
    	return div().with(makeTableResultOperation(platformFilter));
		
    }
    
    /* (non-Javadoc)
	 * @see es.gob.afirma.spie.response.ResponseSPIEOperation#makeTableResultOperation()
	 */
	public static Tag<?> makeTableResultOperation(final String platformFilter) {

		List<Tag<?>> contents = new LinkedList<>();
		
		contents.addAll(makeTableHeader());
		contents.addAll(makeTableRows(platformFilter));
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
		headerGroup.add(th(HEADER_SYSTEM_NAME));

		rowsHeader.add(tr().with(headerGroup));

		return rowsHeader;
	}

	/**
	 * Method that builds the HTML for the result rows.
	 * @return
	 */
	private static Collection<? extends Tag<?>> makeTableRows(final String platformFilter) {
		
		List<Tag<?>> tdGroup = null;
		List<Tag<?>> rows = new LinkedList<>();
		
		for (Map.Entry<String,String> entry : StatusHolder.getInstance().getCurrenttatusHolder().entrySet()) {
			
			if (isRequestedService(platformFilter, entry.getKey())) {
    			tdGroup = new LinkedList<>();
    			tdGroup.add(td(entry.getKey()));
    			tdGroup.add(td(entry.getValue()));
    			tdGroup.add(td(GeneralUtils.getSystemName(entry.getKey())));
    			rows.add(tr().with(tdGroup));
			}
		}

		return rows;
	}
	
	/**
	 * Method that determines whether a service must be added to the response according to the platform filter.
	 * @param platformFilter String that represents the identifier for the platform to be filtered.
	 * @param serviceId String that represents the service identifier.
	 * @return true if the service belongs to the platform being filtered.
	 */
	private static boolean isRequestedService(final String platformFilter, final String serviceId) {
		
		boolean resultado = Boolean.FALSE;
		
		if (platformFilter == null) {
			resultado = Boolean.TRUE;
		} else if (platformFilter.equals(GeneralConstants.PARAMETER_TSA) && (serviceId.contains(GeneralConstants.RFC3161_SERVICE) || serviceId.contains(GeneralConstants.TIMESTAMP_SERVICE))) {
			resultado = Boolean.TRUE;
		} else if (platformFilter.equals(GeneralConstants.PARAMETER_AFIRMA) && !serviceId.contains(GeneralConstants.RFC3161_SERVICE) && !serviceId.contains(GeneralConstants.TIMESTAMP_SERVICE)) {
			resultado = Boolean.TRUE;
		} else {
			resultado = Boolean.FALSE;
		}
		
		return resultado;
	}
	
	
		
}
