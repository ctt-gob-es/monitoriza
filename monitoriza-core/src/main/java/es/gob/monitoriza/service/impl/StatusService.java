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
 * @version 1.0, 8 oct. 2018.
 */
package es.gob.monitoriza.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.gob.monitoriza.persistence.configuration.dto.StatusDTO;
import es.gob.monitoriza.service.IStatusService;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for Status.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 8 oct. 2018.
 */
@Service
public class StatusService implements IStatusService {

	/**
	 * 
	 */
	@Override
	public List<StatusDTO> completeStatus() {

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
		
		Type listType = new TypeToken<ArrayList<StatusDTO>>() {}.getType();
		List<StatusDTO> status = new Gson().fromJson(json2, listType);

		status = checkStatus(status);

		return status;
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	private List<StatusDTO> checkStatus(List<StatusDTO> status) {
		for (StatusDTO s : status) {
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

}
