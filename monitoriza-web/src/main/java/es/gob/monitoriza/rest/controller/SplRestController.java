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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.SplRestController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 28/10/2018.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.LogFilesDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowLogFileDTO;
import es.gob.monitoriza.persistence.configuration.dto.SplDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SplMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.ILogConsumerService;
import es.gob.monitoriza.service.ISplService;

/**
 * <p>
 * Class that manages the REST requests related to the SPL administration
 * and JSON communication.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring SPLs.
 * </p>
 *
 * @version 1.0, 14/03/2019.
 */
@RestController
public class SplRestController {

	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object to manage the SPLs.
	 */
	@Autowired
	private ISplService splService;

	/**
	 * Attribute that represents the service object to connect to SPLs and
	 * consult the log files.
	 */
	@Autowired
	private ILogConsumerService logConsumerService;

	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/spldatatable", method = RequestMethod.GET)
	public DataTablesOutput<SplMonitoriza> dtSpl(@Valid final DataTablesInput input) {
		return this.splService.findAll(input);
	}

	/**
	 * Method that maps the delete user request from datatable to the controller
	 * and performs the delete of the SPL identified by its id.
	 *
	 * @param splId Identifier of the SPL to be deleted.
	 * @param index Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletespl", method = RequestMethod.POST)
	public String deleteSpl(@RequestParam("id") final Long splId, @RequestParam("index") final String index) {

		String rowIndex = index;

		try {
			this.splService.deleteSplById(splId);
		} catch (final Exception e) {
			rowIndex = GeneralConstants.ROW_INDEX_ERROR;
		}
		return rowIndex;
	}

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence. It also updates the scheduled timers.
	 *
	 * @param splForm
	 *            Object that represents the backing SPL form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<SplMonitoriza>}
	 */
	@RequestMapping(value = "/savespl", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<SplMonitoriza> saveSpl(
			@Validated(OrderedValidation.class) @RequestBody final SplDTO splForm,
			final BindingResult bindingResult) {

		final DataTablesOutput<SplMonitoriza> dtOutput = new DataTablesOutput<>();
		List<SplMonitoriza> listNewSpl = new ArrayList<>();

		if (bindingResult.hasErrors()) {
			listNewSpl = StreamSupport.stream(this.splService.getAllSpl().spliterator(), false)
					.collect(Collectors.toList());
			final JSONObject json = new JSONObject();
			for (final FieldError o : bindingResult.getFieldErrors()) {
				json.put("invalid-" + o.getField(), o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {

				final SplMonitoriza spl = this.splService.saveSpl(splForm);
				listNewSpl.add(spl);

			}catch(final Exception e) {
				listNewSpl = StreamSupport.stream(this.splService.getAllSpl().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}

		dtOutput.setData(listNewSpl);

		return dtOutput;
	}

	/**
	 * Method that maps the SPL's log files to the controller and
	 * forwards the logs files to the view.
	 *
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path = "/logfilesdatatable", method = RequestMethod.GET)
	public DataTablesOutput<RowLogFileDTO> dtLogFiles() {

		final DataTablesOutput<RowLogFileDTO> dtOutput = new DataTablesOutput<>();
		final List<RowLogFileDTO> filesList = new ArrayList<>();

		try {
			final LogFilesDTO logFiles = this.logConsumerService.getLogFiles();
			for (final RowLogFileDTO logFile : logFiles.getFileList()) {
				filesList.add(logFile);
			}
			dtOutput.setRecordsTotal(logFiles.getFileList().size());
		}
		catch (final Exception e) {
			LOGGER.warn("Error al llamar al servicio de listado de ficheros de log" , e);
			dtOutput.setError("No se pudo obtener el listado de ficheros del SPL");
		}

		dtOutput.setData(filesList);

		return dtOutput;
	}
}
