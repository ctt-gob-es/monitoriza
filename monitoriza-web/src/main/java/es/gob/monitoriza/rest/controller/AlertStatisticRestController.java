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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.AlertStatisticDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApplication;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics;
import es.gob.monitoriza.service.IAlertDIMAppService;
import es.gob.monitoriza.service.IAlertDIMNodeService;
import es.gob.monitoriza.service.IAlertDIMSeverityService;
import es.gob.monitoriza.service.IAlertDIMTemplateService;
import es.gob.monitoriza.service.IAlertDIMTypeService;
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
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMAppService alertDIMAppService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMTemplateService alertDIMTemplateService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMTypeService alertDIMTypeService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMNodeService alertDIMNodeService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMSeverityService alertDIMSeverityService;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the search of all the values of the parameter.
	 */
	private static final Long PARAM_ALL = (long) -1;

	/**
	 * Method that returns the alert statistics from the database
	 * @param input Holder object for datatable attributes.
	 * @return DataTablesOutput<AlertStatistics> that represents the alert statistics data
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alertstatisticsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<AlertStatistics> alertsAudit(@NotEmpty final DataTablesInput input) {
		final DataTablesOutput<AlertStatistics> dtOutput = new DataTablesOutput<AlertStatistics>();
		final List<AlertStatistics> statisticsList = this.alertStatisticService.findByFilters(null, null, null, null, null, null, null);
		dtOutput.setData(statisticsList);
		return dtOutput;
	}

	/**
	 * Method that returns the statistics with the filters indicated in the form.
	 * @param alertStatisticForm Form with the data to filter.
	 * @return List of filtered data.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(value = "/filterstats", method = RequestMethod.POST)
	public @ResponseBody DataTablesOutput<AlertStatistics> filterStats(@RequestBody final AlertStatisticDTO alertStatisticForm) {

		final DataTablesOutput<AlertStatistics> dtOutput = new DataTablesOutput<AlertStatistics>();
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date minDate = null;
		Date maxDate = null;
		try {
			minDate = formatter.parse(alertStatisticForm.getMinDate() + " 00:00:00");
			maxDate = formatter.parse(alertStatisticForm.getMaxDate() + " 23:59:59");
		} catch (final ParseException e) {
			LOGGER.error("Error al parsear la fecha maxima o minima de las estadisticas a filtrar.");
		}

		AlertDIMApplication app = null;
		AlertDIMTemplate template = null;
		AlertDIMType type = null;
		AlertDIMNode node = null;
		AlertDIMSeverity severity = null;

		if (!PARAM_ALL.equals(alertStatisticForm.getAppID())) {
			app = this.alertDIMAppService.getAlertDIMAppById(alertStatisticForm.getAppID());
		}

		if (!PARAM_ALL.equals(alertStatisticForm.getTemplateID())) {
			template = this.alertDIMTemplateService.getAlertDIMTemplateById(alertStatisticForm.getTemplateID());
		}

		if (!PARAM_ALL.equals(alertStatisticForm.getTypeID())) {
			type = this.alertDIMTypeService.getAlertDIMTypeById(alertStatisticForm.getTypeID());
		}

		if (!PARAM_ALL.equals(alertStatisticForm.getNodeID())) {
			node = this.alertDIMNodeService.getAlertDIMNodeById(alertStatisticForm.getNodeID());
		}

		if (!PARAM_ALL.equals(alertStatisticForm.getSeverityID())) {
			severity = this.alertDIMSeverityService.getAlertDIMSeverityById(alertStatisticForm.getSeverityID());
		}

		final List<AlertStatistics> statisticsList = this.alertStatisticService.findByFilters(minDate, maxDate, app, template, type, node, severity);
		dtOutput.setData(statisticsList);

		return dtOutput;
	}

}