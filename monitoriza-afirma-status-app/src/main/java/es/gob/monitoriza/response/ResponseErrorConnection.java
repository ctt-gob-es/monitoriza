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
 * <b>File:</b><p>es.gob.tsa.response.impl.ResponseErrorConnection.java.</p>
 * <b>Description:</b><p>Class that represents the response for a error request.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>22 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 22 ene. 2018.
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
 * @version 1.0, 12/02/2017.
 */
public class ResponseErrorConnection {

	public static final String ERROR_MESSAGE = "AN ERROR OCCURRED IN THE SERVICE INVOCATION. CHECK LOG.";

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
