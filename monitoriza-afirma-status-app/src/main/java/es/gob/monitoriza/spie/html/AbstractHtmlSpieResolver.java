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
 * <b>File:</b><p>es.gob.monitoriza.spie.html.IHTML.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>27/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 27/08/2019.
 */
package es.gob.monitoriza.spie.html;

import java.util.List;
import java.util.Map;

import es.gob.monitoriza.persistence.configuration.dto.AvgTimesServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.ValMethodsConnDTO;

/** 
 * <p>Interface for the resolvers that parses concrete HTML SPIE responses.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 27/08/2019.
 */
public abstract class AbstractHtmlSpieResolver {
		
	/**
	 * Method that parse the HTML from a SPIE and gets the status result.
	 * @param htmlResult HTML response from a SPIE
	 * @param confSpie DTO containing SPIE global configuration
	 * @return SpieHtmlResolved representing the status of the SPIE
	 */
	public abstract Integer solveHtmlResult(String htmlResult, ConfSpieDTO confSpie);
	
	/**
	 * Gets the {@link #detailResults}.
	 * @return {@link Map}.
	 */
	public abstract List<AvgTimesServiceDTO> getAvgDetailResults();
	
	/**
	 * Gets the {@link #detailResults}.
	 * @return {@link Map}.
	 */
	public abstract List<ValMethodsConnDTO> getValMethodDetailResults();

}
