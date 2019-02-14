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
 * <b>Date:</b><p>06/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.6, 30/01/2019.
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
import es.gob.monitoriza.spie.html.IHtmlSpieResolver;
import es.gob.monitoriza.utilidades.UtilsStringChar;


/** 
 * <p>Class that parses average times SPIE HTML response.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.6, 30/01/2019.
 */
public class HtmlAvgResponseTimeResolver implements IHtmlSpieResolver {
	
	/**
	 * Attribute that represents the semaphore level to set when the SPIE returns error.
	 */
	private Integer semaphErrorLevel;
	
	/**
	 * Attribute that represents the detailed information of service's average times. 
	 */
	private List<AvgTimesServiceDTO> detailResults = new ArrayList<AvgTimesServiceDTO>();
	
	/**
	 * Constructor method for the class HtmlAvgResponseTimeResolver.java.
	 * @param semaphErrorLevelParam {@link #semaphErrorLevel}}
	 */
	public HtmlAvgResponseTimeResolver(final Integer semaphErrorLevelParam) {
		super();
		this.semaphErrorLevel = semaphErrorLevelParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.spie.html.IHtmlSpieResolver#solveHtmlResult(java.lang.String)
	 */
	@Override
	public Integer solveHtmlResult(final String htmlResult, final ConfSpieDTO confSpie) {
		
		Integer sempaphoreValue = SemaphoreEnum.GREEN.getId();
		AvgTimesServiceDTO detail = null;
		
		if (htmlResult != null) {
    		Document doc = Jsoup.parse(htmlResult);
    		Element table = doc.select("table").get(0);
    		Elements rows = table.select("tr");
    		
    		Iterator<Element> avgRows = rows.iterator();
    				
    		Element row = null;
    		Elements cols = null;
    		
    		// Se recorren todas las filas para generar el detalle
    		while (avgRows.hasNext()) {
    			
    			row = avgRows.next();
    			cols = row.select("td");
    			
    			// El primer grupo de columnas son las cabeceras,
    			// luego hay que comprobar que exista <td>.
    			if (!cols.isEmpty()) {
    				detail = new AvgTimesServiceDTO();
    				// La columna con índice 0 se corresponde con el nombre del servicio
    				detail.setServiceName(cols.get(NumberConstants.NUM0).text());
    				// La columna con índice 1 se corresponde con el tiempo medio obtenido
    				detail.setAvgTime(Float.valueOf(cols.get(NumberConstants.NUM1).text()));
    				// La columna con índice 2 se corresponde con el nº de transacciones que superan el máximo tiempo establecido
    				// y el valor de ese tiempo máximo entre paréntesis --> Se obtiene el nº de transacciones
    				detail.setNumTransAboveMax(getNumOfTransAboveMax(cols.get(NumberConstants.NUM2).text()));
    				// La columna con índice 2 se corresponde con el nº de transacciones que superan el máximo tiempo establecido
    				// y el valor de ese tiempo máximo entre paréntesis --> Se obtiene el tiempo máximo
    				detail.setMaxTime(getMaxAllowedTime(cols.get(NumberConstants.NUM2).text()));
    				// La columna con índice 3 se corresponde con el nº total de transacciones procesadas para el servicio
    				detail.setTotalNumTrans(Long.valueOf(cols.get(NumberConstants.NUM3).text()));
    				
    				// Se añade al detalle
    				detailResults.add(detail);
    				
    				// Si el nº de transacciones que superan el máximo supera el porcentaje de aceptación respecto del total de transacciones,
    				// se considera que el SPIE presenta un estado erróneo 
    				if (detail.getNumTransAboveMax() > (detail.getTotalNumTrans() * confSpie.getPercentAccept()/NumberConstants.NUM100)) {
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
	public List<AvgTimesServiceDTO> getDetailResults() {
		return detailResults;
	}

	/**
	 * Sets the {@link #detailResults}.
	 * @param detailResultsParam value for {@link #detailResults} to set
	 */
	public void setDetailResults(List<AvgTimesServiceDTO> detailResultsParam) {
		this.detailResults = detailResultsParam;
	}

	/**
	 * Method that parses the value of the cell containing the number of transactions above maximum and its corresponding maximum.
	 * @param colText Text of the cell containing the number of transactions above average time and (maximum time).
	 * @return Long that represents the number of transactions that exceed the average time  
	 */
	private Long getNumOfTransAboveMax(final String colText) {
				
		String numOfTrans = colText.replaceAll("\\s",UtilsStringChar.EMPTY_STRING).substring(NumberConstants.NUM0, colText.indexOf(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING)-1);
		
		return Long.valueOf(numOfTrans);
		
	}
	
	/**
	 * Method that parses the value of the cell containing the number of transactions above maximum and its corresponding maximum.
	 * @param colText Text of the cell containing the number of transactions above average time and (maximum time).
	 * @return Long that represents the maximum allowed time defined in the cell  
	 */
	private Long getMaxAllowedTime(final String colText) {
						
		String maxTime = colText.replaceAll("\\s",UtilsStringChar.EMPTY_STRING).substring(colText.indexOf(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING), colText.indexOf(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING)-1).replaceAll("ms", UtilsStringChar.EMPTY_STRING);
		
		return Long.valueOf(maxTime);
		
	}

}
