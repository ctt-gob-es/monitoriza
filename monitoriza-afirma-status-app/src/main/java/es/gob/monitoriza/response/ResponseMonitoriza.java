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
 * @version 1.2, 18/01/2019.
 */
package es.gob.monitoriza.response;

import static j2html.TagCreator.body;
import static j2html.TagCreator.caption;
import static j2html.TagCreator.div;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.status.StatusUptodate;
import es.gob.monitoriza.utilidades.GeneralUtils;
import j2html.tags.Tag;

/**
 * <p>Class that builds the global HTML response for the Monitoriz@ servlet call.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 18/01/2019.
 */
public final class ResponseMonitoriza {
	
	/**
	 * Constant attribute that represents.
	 */
	public static final String RESULT_OK = "OK";
	
	/**
	 * Attribute that represents the name for this operation test.
	 */
	public static final String OPERATION_NAME = "Status for @firma platform services";

	/**
	 * Attribute that represents the column header name for the schema.
	 */
	public static final String HEADER_SERVICE_NAME = "Service Name";

	/**
	 * Attribute that represents the column header name for the test result.
	 */
	public static final String HEADER_RESULT_STATUS = "Status";
	
	/**
	 * Attribute that represents the column header name for the test result.
	 */
	public static final String HEADER_RESULT_STATUS_UPTODATE = "Last update";
	
	/**
	 * Attribute that represents the column header name for the advanced information of the service. 
	 */
	public static final String HEADER_RESULT_ADVANCED_INFO = "Advanced info";
	
	/**
	 * Attribute that represents the column header for the name of the request file in advanced info. 
	 */
	public static final String HEADER_RESULT_REQUEST_FILE = "Request file";
	
	/**
	 * Attribute that represents the column header for the time of the request in advanced info.  
	 */
	public static final String HEADER_RESULT_REQUEST_TIME = "Request time";
	
	/**
	 * Attribute that represents the column header name for the test result.
	 */
	public static final String CAPTION_LAST_REFRESH_DATETIME = "Last refresh time: ";
	
	/**
	 * Attribute that represents the column header name for the test result.
	 */
	public static final String HEADER_SYSTEM_NAME = "System";
	
	/**
	 * Constructor method for the class ResponseMonitoriza.java. 
	 */
	private ResponseMonitoriza(){
		
	}
			
	/**
	 * Method that renders the HTML code for the result response.
	 * @param platformFilter String argument for filtering the results by system name
	 * @param adminFilter String argument to enable admin mode, returning the response in JSON format.
	 * @return String that represents the HTML code of the result
	 * @throws Exception Error during HTML render.
	 */
	public static String render(final String platformFilter, final String adminFilter) throws Exception {
				
        return html().with(makeHead(), makeBody(platformFilter, adminFilter)).render(); 
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
     * @param platformFilter String that represents the optional filter for showing results for a platform. 
     * @param adminFilter String that represents the optional filter for showing additional information
     * @return Tag that represents the HTML body
     */
    private static Tag<?> makeBody(final String platformFilter, final String adminFilter) {
    	    	
        return body().with(makeDivOperations(platformFilter, adminFilter)); 
    }
    
   
    /**
     * Method that builds the HTML divs for each of the operation result.
     * @param platformFilter String that represents the optional filter for showing results for a platform. 
     * @param adminFilter String that represents the optional filter for showing additional information
     * @return Tag that represents the HTML divs for each of the operation result.
     */
    private static Tag<?> makeDivOperations(final String platformFilter, final String adminFilter) {
		    	  	
    	return div().with(makeTableResultOperation(platformFilter, adminFilter));
		
    }
    
   
	/**
	 * Method that builds the HTML for the table for the opertaion results.
     * @param platformFilter String that represents the optional filter for showing results for a platform. 
     * @param adminFilter String that represents the optional filter for showing additional information
	 * @return Tag that represents the table
	 */
	public static Tag<?> makeTableResultOperation(final String platformFilter, final String adminFilter) {

		List<Tag<?>> contents = new LinkedList<>();
		
		contents.add(makeTableCaption());
		contents.addAll(makeTableHeader(adminFilter));
		contents.addAll(makeTableRows(platformFilter, adminFilter));
		return table().with(contents);
	}
	
	/**
	 * Method that builds the HTML caption for the result table.
	 * @return HTML table caption.
	 */
	private static Tag<?> makeTableCaption() {

		final StringBuffer caption = new StringBuffer();

		caption.append(CAPTION_LAST_REFRESH_DATETIME);
		caption.append(GeneralUtils.getFormattedDateTime(LocalDateTime.now()));

		return caption(caption.toString());
	}


	/**
	 * Method that builds the HTML header for the result table.
	 * @param adminFilter Parameter for filtering the results by platform.
	 * @return HTML table headers.
	 */
	private static Collection<? extends Tag<?>> makeTableHeader(final String adminFilter) {
		List<Tag<?>> rowsHeader = new LinkedList<>();
		List<Tag<?>> headerGroup1 = new LinkedList<>();
	
		headerGroup1.add(th(HEADER_SYSTEM_NAME));
		headerGroup1.add(th(HEADER_SERVICE_NAME));
		headerGroup1.add(th(HEADER_RESULT_STATUS));
		headerGroup1.add(th(HEADER_RESULT_STATUS_UPTODATE));
		
		if (adminFilter != null) {
			headerGroup1.add(th(HEADER_RESULT_ADVANCED_INFO));
		}

		rowsHeader.add(tr().with(headerGroup1));

		return rowsHeader;
	}

	/**
	 * Method that builds the HTML for the result rows.
	 * @param platformFilter Parameter for filtering the results by platform.
	 * @param adminFilter Parameter to return the JSON format for admin mode.
	 * @return HTML table rows.
	 */
	private static Collection<? extends Tag<?>> makeTableRows(final String platformFilter, final String adminFilter) {
		
		List<Tag<?>> tdGroup = null;
		List<Tag<?>> rows = new LinkedList<>();
		StatusUptodate statusUptodate = null;
		
		for (Map.Entry<String,StatusUptodate> entry : StatusHolder.getInstance().getCurrenttatusHolder().entrySet()) {
			
			if (isRequestedService(platformFilter, entry.getValue().getPlatform())) {
				statusUptodate = entry.getValue();
    			tdGroup = new LinkedList<>();
    			tdGroup.add(td(statusUptodate.getPlatform()));
    			tdGroup.add(td(entry.getKey()));
    			tdGroup.add(td(statusUptodate.getStatusValue()));
    			tdGroup.add(td( GeneralUtils.getFormattedDateTime(statusUptodate.getStatusUptodate())));
    			
    			if (adminFilter != null) {
       				tdGroup.add(td(statusUptodate.generateAdvancedInfoString()));
       			}
    			
       			rows.add(tr().with(tdGroup));
             			
			}
		}

		return rows;
	}
	
	/**
	 * Method that determines whether a service must be added to the response according to the platform filter.
	 * @param platformFilter String that represents the identifier for the platform to be filtered.
	 * @param platformName String that represents the name of the platform to be compared with the parameter.
	 * @return true if the service belongs to the platform being filtered.
	 */
	private static boolean isRequestedService(final String platformFilter, final String platformName) {
		
		boolean resultado = Boolean.FALSE;
		
		if (platformFilter == null) {
			resultado = Boolean.TRUE;
		} else if (platformFilter.equals(GeneralConstants.PARAMETER_TSA) && (platformName.equalsIgnoreCase(GeneralConstants.PLATFORM_TSA))) {
			resultado = Boolean.TRUE;
		} else if (platformFilter.equals(GeneralConstants.PARAMETER_AFIRMA) && (platformName.equalsIgnoreCase(GeneralConstants.PLATFORM_AFIRMA))) {
			resultado = Boolean.TRUE;
		} else if (platformFilter.equals(GeneralConstants.PARAMETER_CLAVE) && (platformName.equalsIgnoreCase(GeneralConstants.PLATFORM_CLAVE))) {
			resultado = Boolean.TRUE;
		}
		
		return resultado;
	}
	
	
		
}
