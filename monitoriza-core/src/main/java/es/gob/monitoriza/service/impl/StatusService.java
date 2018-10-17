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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.StatusService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 17/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.gob.monitoriza.persistence.configuration.dto.RowStatusDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusDTO;
import es.gob.monitoriza.service.IStatusService;

/**
 * <p>Class that implements the communication with the status servlet.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * 
 * @version 1.1, 17/10/2018.
 */
@Service
public class StatusService implements IStatusService {

	/**
	 * 
	 */
	@Override
	public StatusDTO completeStatus() {

		// Del JSON obtenido solo usamos el 'data'
		String json = "[{\r\n" 
					+ "    \"status\": \"Correcto\", \r\n" 
					+ "    \"service\": \"validarcertificado1\", \r\n"
					+ "    \"averageTime\": 11, \r\n" 
					+ "    \"samplingTime\": \"05-10-2018 10:36:31\", \r\n"
					+ "    \"partialRequestResult\": {\r\n" 
					+ "        \"C:\\Proyecto\\Monitoriza\\peticionesAfirmaFormZip\\5_validarcertificado\\grupoPrincipal\\ValidarCertificado_1.xml\": 111, \r\n"
					+ "        \"C:\\Proyecto\\Monitoriza\\peticionesAfirmaFormZip\\5_validarcertificado\\grupoPrincipal\\ValidarCertificado_2.xml\": 222 \r\n"
					+ "    }\r\n" 
					+ "},\r\n" 
					+ "{\r\n"
					+ "    \"status\": \"Degradado\", \r\n" 
					+ "    \"service\": \"validarcertificado2\", \r\n"
					+ "    \"averageTime\": 22, \r\n" 
					+ "    \"samplingTime\": \"05-10-2018 10:36:31\", \r\n"
					+ "    \"partialRequestResult\": {\r\n" 
					+ "        \"C:\\Proyecto\\Monitoriza\\peticionesAfirmaFormZip\\5_validarcertificado\\grupoPrincipal\\ValidarCertificado_3.xml\": 333 \r\n"
					+ "    }\r\n" 
					+ "},\r\n" 
					+ "{\r\n"
					+ "    \"status\": \"Caido\", \r\n" 
					+ "    \"service\": \"validarcertificado3\", \r\n"
					+ "    \"averageTime\": 33, \r\n" 
					+ "    \"samplingTime\": \"05-10-2018 10:36:31\", \r\n"
					+ "    \"partialRequestResult\": {\r\n" 
					+ "    }\r\n" 
					+ "},\r\n" 
					+ "{\r\n"
					+ "    \"status\": \"Caido\", \r\n" 
					+ "    \"service\": \"validarcertificado4\", \r\n"
					+ "    \"averageTime\": 44, \r\n" 
					+ "    \"samplingTime\": \"05-10-2018 10:36:31\" \r\n"
					+ "}\r\n" 
					+ "]";

		String json2 = "[{\r\n" + 
				"		\"status\": \"Caido\",\r\n" + 
				"		\"service\": \"validarcertificado1\",\r\n" + 
				"		\"averageTime\": 11,\r\n" + 
				"		\"samplingTime\": \"05-10-2018 10:36:31\",\r\n" + 
				"		\"partialRequestResult\": {\r\n" + 
				"			\"C:\\\\Proyecto\\\\Monitoriza\\\\peticionesAfirmaFormZip\\\\5_validarcertificado\\\\grupoPrincipal\\\\ValidarCertificado_1.xml\": 46,\r\n" + 
				"			\"C:\\\\Proyecto\\\\Monitoriza\\\\peticionesAfirmaFormZip\\\\5_validarcertificado\\\\grupoPrincipal\\\\ValidarCertificado_2.xml\": 48\r\n" + 
				"		}\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"status\": \"Caido\",\r\n" + 
				"		\"service\": \"validarcertificado2\",\r\n" + 
				"		\"averageTime\": 22,\r\n" + 
				"		\"samplingTime\": \"05-10-2018 10:36:31\",\r\n" + 
				"		\"partialRequestResult\": {\r\n" + 
				"			\"C:\\\\Proyecto\\\\Monitoriza\\\\peticionesAfirmaFormZip\\\\5_validarcertificado\\\\grupoPrincipal\\\\ValidarCertificado_3.xml\": 46,\r\n" + 
				"			\"C:\\\\Proyecto\\\\Monitoriza\\\\peticionesAfirmaFormZip\\\\5_validarcertificado\\\\grupoPrincipal\\\\ValidarCertificado_4.xml\": 48\r\n" + 
				"		}\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"status\": \"Correcto\",\r\n" + 
				"		\"service\": \"validarcertificado3\",\r\n" + 
				"		\"averageTime\": 33,\r\n" + 
				"		\"samplingTime\": \"05-10-2018 10:36:31\",\r\n" + 
				"		\"partialRequestResult\": {}\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"status\": \"Degradado\",\r\n" + 
				"		\"service\": \"validarcertificado4\",\r\n" + 
				"		\"averageTime\": 44,\r\n" + 
				"		\"samplingTime\": \"05-10-2018 10:36:31\"\r\n" + 
				"	}\r\n" + 
				"]";
				
		Type listType = new TypeToken<StatusDTO>() {}.getType();
		
		String jsonFromServlet = getRequestFromStatusServlet();
		
		StatusDTO status = new Gson().fromJson(jsonFromServlet, listType);

		status.setData(checkStatus(status.getData()));

		return status;
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	private List<RowStatusDTO> checkStatus(List<RowStatusDTO> status) {
		for (RowStatusDTO s : status) {
			switch (s.getStatus()) {
			case "Correcto":
				s.setStatusAux(0L);
				break;
			case "Caido":
				s.setStatusAux(2L);
				break;
			case "Degradado":
				s.setStatusAux(1L);
				break;
			default:
				s.setStatusAux(2L);
			}
		}
		return status;
	}
	
	private String getRequestFromStatusServlet() {
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost:8080/monitoriza-afirma-status-app/afirmaServicesStatus?admin=true");
		String result = null;
				
		//Execute and get the response.
		HttpResponse response = null;
		HttpEntity entity = null;
		
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			
			if (entity != null) {
			    try (InputStream instream = entity.getContent();) {
			    	result = new BufferedReader(new InputStreamReader(instream))
			    			  .lines().collect(Collectors.joining("\n"));
			    } 
			}
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

}
