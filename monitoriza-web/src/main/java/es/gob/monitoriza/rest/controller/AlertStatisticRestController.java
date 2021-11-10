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
 * <b>File:</b><p>es.gob.monitoriza.controller.UserRestController.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>21/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 14/03/2019.
 */
package es.gob.monitoriza.rest.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistic;
import es.gob.monitoriza.service.IAlertStatisticService;

/**
 * <p>Class that manages the REST requests related to the statistics of alerts and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 09/11/2021.
 */
@RestController
public class AlertStatisticRestController {

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertStatisticService alertStatisticService;

	/**
	 * Method that returns the alert statistics from the database
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return DataTablesOutput<AlertStatistic> that represents the alert statistics data
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alertstatisticsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<AlertStatistic> alertsAudit(@NotEmpty final DataTablesInput input) {
		return this.alertStatisticService.findAll(input);
	}


}