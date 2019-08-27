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
 * <b>File:</b><p>es.gob.monitoriza.spie.html.impl.HtmlAvgResponseTimeResolver.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/08/2019.</p>
 * @author Gobierno de España.
 * @version 1.1, 27/08/2019.
 */
package es.gob.monitoriza.spie.html.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.enums.SemaphoreEnum;
import es.gob.monitoriza.persistence.configuration.dto.AvgTimesServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.ValMethodsConnDTO;
import es.gob.monitoriza.spie.html.AbstractHtmlSpieResolver;


/** 
 * <p>Class that parses OCSP/CRL connection validation methods SPIE HTML response.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 27/08/2019.
 */
public class HtmlValMethodsResolver extends AbstractHtmlSpieResolver {
	
	/**
	 * Attribute that represents the literal String of the SPIE with no error connection. 
	 */
	private static final String VALMETHOD_CONNECTION_OK = "OK";
	
	/**
	 * Attribute that represents the semaphore level to set when the SPIE returns error.
	 */
	private Integer semaphErrorLevel;
	
	/**
	 * Attribute that represents the detailed information of service's average times. 
	 */
	private List<ValMethodsConnDTO> detailResults = new ArrayList<ValMethodsConnDTO>();
	
	/**
	 * Constructor method for the class HtmlValMethodsResolver.java.
	 * @param semaphErrorLevelParam {@link #semaphErrorLevel}}
	 */
	public HtmlValMethodsResolver(final Integer semaphErrorLevelParam) {
		super();
		this.semaphErrorLevel = semaphErrorLevelParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.spie.html.AbstractHtmlSpieResolver#solveHtmlResult(java.lang.String)
	 */
	@Override
	public Integer solveHtmlResult(final String htmlResult, final ConfSpieDTO confSpie) {
		
		Integer sempaphoreValue = SemaphoreEnum.GREEN.getId();
		ValMethodsConnDTO detail = null;
		
		if (htmlResult != null) {
    		Document doc = Jsoup.parse(htmlResult);
    		Element table = doc.select("table").get(0);
    		Elements rows = table.select("tr");
    		
    		Iterator<Element> connRows = rows.iterator();
    				
    		Element row = null;
    		Elements cols = null;
    		
    		// Se recorren todas las filas para generar el detalle
    		while (connRows.hasNext()) {
    			
    			row = connRows.next();
    			cols = row.select("td");
    			
    			// El primer grupo de columnas son las cabeceras,
    			// luego hay que comprobar que exista <td>.
    			if (!cols.isEmpty()) {
    				detail = new ValMethodsConnDTO();
    				// La columna con índice 0 se corresponde con el identificador del método de validación
    				detail.setValMethodId(cols.get(NumberConstants.NUM0).text());
    				// La columna con índice 1 se corresponde con el tipo del método de validación
    				detail.setValMethodType(cols.get(NumberConstants.NUM1).text());
    				// La columna con índice 2 se corresponde con la URL del método de validación
    				detail.setValMethodUrl(cols.get(NumberConstants.NUM2).text());
    				// La columna con índice 3 se corresponde con el resultado de la conexión con el método de validación
    				detail.setResult(cols.get(NumberConstants.NUM3).text());
    				
    				// Se añade al detalle
    				detailResults.add(detail);
    				    				 
    				if (!detail.getResult().equals(VALMETHOD_CONNECTION_OK)) {
    					sempaphoreValue = semaphErrorLevel;
    
    				}
    			}
    		}
		} else {
			sempaphoreValue = null;
		}
		
		return sempaphoreValue;
	}
			
	
	/**
	 * Gets the {@link #detailResults}.
	 * @return {@link Map}.
	 */
	public List<AvgTimesServiceDTO> getAvgDetailResults() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.spie.html.AbstractHtmlSpieResolver#getValMethodDetailResults()
	 */
	@Override
	public List<ValMethodsConnDTO> getValMethodDetailResults() {
		
		return detailResults;
	}
	
}
