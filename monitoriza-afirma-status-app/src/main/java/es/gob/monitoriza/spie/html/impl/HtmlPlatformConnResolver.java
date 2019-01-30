/* 
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
 * <b>File:</b><p>es.gob.monitoriza.spie.html.impl.HtmlTsaConnResolver.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/01/2019.
 */
package es.gob.monitoriza.spie.html.impl;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.enums.SemaphoreEnum;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.spie.html.IHtmlSpieResolver;


/** 
 * <p>Class that parses @Firma/TS@ Connection SPIE HTML response.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/01/2019.
 */
public class HtmlPlatformConnResolver implements IHtmlSpieResolver {
	
	/**
	 * Attribute that represents the literal String of the SPIE with error status. 
	 */
	private static final String PLATFORM_CONNN_ERROR = "ERROR";
	
	/**
	 * Attribute that represents the semaphore level (1-Yellow,2-Red) to set when the SPIE returns error.
	 */
	private Integer semaphErrorLevel;
		
	/**
	 * Constructor method for the class HtmlTsaConnResolver.java.
	 * @param semaphErrorLevelParam {@link #semaphErrorLevel}}
	 */
	public HtmlPlatformConnResolver(Integer semaphErrorLevelParam) {
		super();
		this.semaphErrorLevel = semaphErrorLevelParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.spie.html.IHtmlSpieResolver#solveHtmlResult(java.lang.String)
	 */
	@Override
	public Integer solveHtmlResult(String htmlResult, ConfSpieDTO confSpie) {
		
		Integer sempaphoreValue = SemaphoreEnum.GREEN.getId();
		String tsaStatus = null;
		
		if (htmlResult != null) {
    		Document doc = Jsoup.parse(htmlResult);
    		Element table = doc.select("table").get(0);
    		Elements rows = table.select("tr");
    		
    		Iterator<Element> tsaRows = rows.iterator();
    		
    		// Se comprueba el estado de todas las TS@ mientras haya filas de resultados y no haya
    		// ningún error en TimeStamp ASN1 de alguna TS@.
    		while (tsaRows.hasNext() && SemaphoreEnum.GREEN.getId().equals(sempaphoreValue)) {
    			
    			Element row = tsaRows.next();
    			Elements cols = row.select("td");
    			
    			// El primer grupo de columnas son las cabeceras,
    			// luego hay que comprobar que exista <td>.
    			if (!cols.isEmpty()) {
    				
    				// La columna con índice 1 se corresponde con TimeStamp ASN1
    				tsaStatus = cols.get(NumberConstants.NUM1).text();
    
    				// Si cualquiera de las TS@ del SPIE presenta un estado de error,
    				// se considera que el SPIE es erróneo.
    				if (PLATFORM_CONNN_ERROR.equals(tsaStatus)) {
    					sempaphoreValue = semaphErrorLevel;
    
    				}
    			}
    		}
		} else {
			sempaphoreValue = null;
		}
		
		return sempaphoreValue;
	}

}
