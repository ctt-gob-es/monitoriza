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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.persistence.configuration.dto.TemplateDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDeleteDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitorizaID;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.service.IAlertConfigMonitorizaService;
import es.gob.monitoriza.service.IAlertConfigSystemService;
import es.gob.monitoriza.service.IAlertGrayLogNoticeConfigService;
import es.gob.monitoriza.service.IAlertMailNoticeConfigService;
import es.gob.monitoriza.service.IAlertResumeTypeService;
import es.gob.monitoriza.service.IAlertTypeMonitorizaService;
import es.gob.monitoriza.service.IAlertTypeTemplateMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
import es.gob.monitoriza.service.ITemplateMonitorizaService;

/**
 * <p>Class that manages the REST requests related to the Users administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/11/2021.
 */

@RestController
public class TemplateRestController {

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private ITemplateMonitorizaService templateService;

	@Autowired
	private IAlertTypeMonitorizaService alertTypeMonitorizaService;

	@Autowired
	private IAlertTypeTemplateMonitorizaService alertTypeTemplateMonitorizaService;

	@Autowired
	private IApplicationMonitorizaService applicationMonitorizaService;

	@Autowired
	private IAlertResumeTypeService alertResumeTypeService;

	@Autowired
	private IAlertConfigMonitorizaService alertConfigMonitorizaService;

	@Autowired
	private IAlertConfigSystemService alertConfigSystemService;

	@Autowired
	private IAlertMailNoticeConfigService alertMailNoticeConfigService;

	@Autowired
	private IAlertGrayLogNoticeConfigService alertGrayLogNoticeConfigService;

	/**
	 * Method that maps the user web requests to the controller and
	 * forwards the list of templates to the view.
	 *
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/templatesdatatable", method = RequestMethod.GET)
	public DataTablesOutput<TemplateMonitoriza> templates(@NotEmpty final DataTablesInput input) {
		return this.templateService.findAll(input);
	}

	/**
	 * Method that import the template and returns the new list of templates.
	 * @param file File to import.
	 * @return DataTablesOutput<TemplateMonitoriza> with the new template.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(value = "/importtemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public DataTablesOutput<TemplateMonitoriza> getTemplateData(@RequestParam("file") final MultipartFile file) {
		Document doc =  null;
		DataTablesOutput<TemplateMonitoriza> dtOutput = null;

		try{
			dtOutput = new DataTablesOutput<TemplateMonitoriza>();
	        final InputStream is = file.getInputStream();
	        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        doc = dBuilder.parse(is);

	        final NodeList l =doc.getChildNodes().item(0).getChildNodes();

	        String templateName =  null;
	        String templateDescription =  null;

	        final TemplateMonitoriza template = new TemplateMonitoriza();

 	        for(int i = 0; i<l.getLength(); i++){
	        	final Node n =l.item(i);
	        	if( n.getNodeType()==Node.ELEMENT_NODE && n.getNodeName().equals("name")){
	        		templateName = n.getFirstChild().getNodeValue();
	        		template.setName(templateName);
	        	}
	        	if( n.getNodeType()==Node.ELEMENT_NODE && n.getNodeName().equals("description")){
	        		templateDescription = n.getFirstChild().getNodeValue();
	        		template.setDescription(templateDescription);
	        	}

	        }

 	        final NodeList nLists = doc.getElementsByTagName("alarm");
	        final List<AlertTypeMonitoriza> listAlertTypeMonitoriza = new ArrayList<>();
	        for(int temp = 0; temp < nLists.getLength(); temp++) {
	        	  final Node nNode = nLists.item(temp);

	        	  if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	        		final AlertTypeMonitoriza alarm = new AlertTypeMonitoriza();

	        	    final Element eElement = (Element) nNode;
	        	    alarm.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
	        	    alarm.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
	        	    final AlertTypeMonitoriza alertTypeMonitoriza = this.alertTypeMonitorizaService.saveAlertTypeMonitoriza(alarm);
	        	    listAlertTypeMonitoriza.add(alertTypeMonitoriza);
	        	  }
	        }

 	       final TemplateMonitoriza result = this.templateService.saveTemplateMonitoriza(template);

 	       for(final AlertTypeMonitoriza alert : listAlertTypeMonitoriza){
 	    	  final AlertTypeTemplateMonitoriza alertTypeTemplateMonitoriza = new AlertTypeTemplateMonitoriza();
 	    	  final AlertTypeTemplateMonitorizaID alertTypeTemplateMonitorizaID = new AlertTypeTemplateMonitorizaID();
 	    	  alertTypeTemplateMonitorizaID.setIdTemplateMonitoriza(result.getIdTemplateMonitoriza());
 	    	  alertTypeTemplateMonitorizaID.setIdTypeMonitoriza(alert.getIdTypeMonitoriza());

 	    	  alertTypeTemplateMonitoriza.setIdAlertTypeTemplateMonitorizaID(alertTypeTemplateMonitorizaID);
 	    	  alertTypeTemplateMonitoriza.setAlertTypeMonitoriza(alert);
 	    	  alertTypeTemplateMonitoriza.setTemplateMonitoriza(result);
 	    	  this.alertTypeTemplateMonitorizaService.saveAlertTypeTemplateMonitoriza(alertTypeTemplateMonitoriza);
 	       }


	      } catch (SAXException | IOException | ParserConfigurationException ex) {

	    }

		final List<TemplateMonitoriza> listTemplates = (List<TemplateMonitoriza>) this.templateService.getAllTemplateMonitoriza();
		dtOutput.setData(listTemplates);
		return dtOutput;
	}

	/**
	 * Method that export a template to a file.
	 * @param response Response with data to send.
	 * @param idTemplateMonitoriza Id of the template to export.
	 */
	@RequestMapping(value = "/exporttemplate", method = RequestMethod.GET)
	@ResponseBody
	public void exportTemplate(final HttpServletResponse response, @RequestParam("idTemplateMonitoriza") final Long idTemplateMonitoriza) {

		StreamResult result = null;
		TemplateMonitoriza template = null;

		try {
			  template = this.templateService.getTemplateMonitorizaById(idTemplateMonitoriza);
			  final String nameTemplate = template.getName();
		      final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		      final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		      //Template
		      final Document doc = docBuilder.newDocument();
		      final Element rootElement = doc.createElement("Template");
		      rootElement.setAttribute("xmlns", "urn:es:gob:sgad:afirma:monitoriza");
		      doc.appendChild(rootElement);
		      //Name
		      final Element name = doc.createElement("name");
		      name.setTextContent(nameTemplate);
		      rootElement.appendChild(name);
		      //Description
		      final Element description = doc.createElement("description");
		      description.setTextContent(template.getDescription());
		      rootElement.appendChild(description);

		      //Alarms
		      final Element alarms = doc.createElement("alarms");

		      final List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza = this.alertTypeTemplateMonitorizaService.getAllAlertTypeTemplateMonitorizaByTemplateMonitoriza(template);

		      for(final AlertTypeTemplateMonitoriza item : listAlertTypeTemplateMonitoriza){
		    	  final Element alarm = doc.createElement("alarm");
		    	  //Name Alarm
		    	  final Element nameAlarm = doc.createElement("name");
		    	  nameAlarm.setTextContent(item.getAlertTypeMonitoriza().getName());
		    	  alarm.appendChild(nameAlarm);
		    	  //Description Alarm
		    	  final Element descriptionAlarm = doc.createElement("description");
		    	  descriptionAlarm.setTextContent(item.getAlertTypeMonitoriza().getDescription());
		    	  alarm.appendChild(descriptionAlarm);

		    	  alarms.appendChild(alarm);

		      }

		      rootElement.appendChild(alarms);

		      //Se escribe el contenido del XML en un archivo
		      result= transformerFactory(doc, nameTemplate);

		      //Se descarga el XML
		      final byte[] bFile = result.getOutputStream().toString().getBytes();
		      final InputStream in = new ByteArrayInputStream(bFile);
		      response.setContentLength(bFile.length);
		      response.setHeader("Content-Disposition", "attachment; filename=" + nameTemplate+".xml");
		      FileCopyUtils.copy(in, response.getOutputStream());

		} catch (final TransformerException tfe) {
		    	  tfe.printStackTrace();
		} catch (final ParserConfigurationException e) {
		    	  e.printStackTrace();
		} catch (final IOException e) {
		    	  e.printStackTrace();
		}
	}


	/**
	 * Method that transform the doc.
	 * @param doc Doc to transform.
	 * @param nameTemplate Name of the template.
	 * @return StreamResult with the data of the doc.
	 * @throws TransformerConfigurationException Error in the configuration transformer.
	 * @throws TransformerException Error in the transformer.
	 */
	private StreamResult transformerFactory(final Document doc, final String nameTemplate)
			throws TransformerConfigurationException, TransformerException {

			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final StreamResult result = new StreamResult(bos);
			final DOMSource docDom = new DOMSource(doc);
			transformer.transform(docDom, result);
			return result;
	}


	/**
	 * Method that edit the template data.
	 * @param templateform Form with the new template data.
	 * @param idTemplateMonitoriza Id of the template to edit.
	 * @param bindingResult Validation.
	 * @return DataTablesOutput<TemplateMonitoriza> with the new data of the template.
	 */
	@RequestMapping(value = "/edittemplate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public DataTablesOutput<TemplateMonitoriza> editTemplateData(@RequestBody final TemplateDTO templateform, @RequestParam("idTemplateMonitoriza") final Long idTemplateMonitoriza, final BindingResult bindingResult) { //

		final DataTablesOutput<TemplateMonitoriza> dtOutput = new DataTablesOutput<TemplateMonitoriza>();

		templateform.setIdTemplateMonitoriza(idTemplateMonitoriza);
		this.templateService.saveTemplateMonitorizaWithDTO(templateform);

		final List<TemplateMonitoriza> listTemplates = (List<TemplateMonitoriza>) this.templateService.getAllTemplateMonitoriza();
		dtOutput.setData(listTemplates);
		return dtOutput;
	}

	/**
	 * Method that delete a template.
	 * @param idTemplateMonitoriza ID of the template to delete.
	 * @return DataTablesOutput<TemplateMonitoriza> with the deleted template.
	 */
	@RequestMapping(path = "/deletetemplate", method = RequestMethod.POST)
	@Transactional
	@JsonView(DataTablesOutput.View.class)
	public DataTablesOutput<TemplateMonitoriza> deleteTemplate(@RequestParam("idTemplateMonitoriza") final Long idTemplateMonitoriza) {

		final TemplateDeleteDTO templateDeleteDTO = getTemplateDeleteDto(idTemplateMonitoriza);

		this.templateService.deleteTemplateMonitorizaWithDTO(templateDeleteDTO);

		final DataTablesOutput<TemplateMonitoriza> dtOutput = new DataTablesOutput<TemplateMonitoriza>();
		final List<TemplateMonitoriza> listTemplates = (List<TemplateMonitoriza>) this.templateService.getAllTemplateMonitoriza();
		dtOutput.setData(listTemplates);
		return dtOutput;
	}


	/**
	 * Method that returns the data of the template to delete.
	 * @param idTemplateMonitoriza Id of the template to delete.
	 * @return TemplateDeleteDTO with the data of the template to dleete.
	 */
	public TemplateDeleteDTO getTemplateDeleteDto(final Long idTemplateMonitoriza){
		final TemplateMonitoriza template = this.templateService.getTemplateMonitorizaById(idTemplateMonitoriza);

		final List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza = this.alertTypeTemplateMonitorizaService.getAllAlertTypeTemplateMonitorizaByTemplateMonitoriza(template);
		final List<ApplicationMonitoriza> listApplicationMonitoriza = this.applicationMonitorizaService.getAllApplicationMonitorizaByTemplateMonitoriza(template);
		final List<AlertTypeMonitoriza> listAllAlertTypeMonitoriza = getAllAlertTypeMonitoriza(listAlertTypeTemplateMonitoriza);
		final List<AlertResumeType> listAllAlertResumeType = new ArrayList<>();
		final List<AlertConfigMonitoriza> listAllAlertConfigMonitoriza = new ArrayList<>();
		final List<AlertMailNoticeConfig> listAllAlertMailNoticeConfig = new ArrayList<>();
		final List<AlertGraylogNoticeConfig> listAllAlertGraylogNoticeConfig = new ArrayList<>();


		for(final ApplicationMonitoriza app : listApplicationMonitoriza){
			listAllAlertResumeType.addAll(this.alertResumeTypeService.getAllAlertResumeTypeByApplicationMonitoriza(app));
			listAllAlertConfigMonitoriza.addAll(this.alertConfigMonitorizaService.getAllAlertConfigMonitorizaByApplicationMonitoriza(app));
		}

		final List<AlertConfigSystem> listAllAlertConfigSystem = getAllAlertConfigSystem(listAllAlertConfigMonitoriza);

		for(final AlertConfigSystem alertConfigSystem : listAllAlertConfigSystem){
			listAllAlertMailNoticeConfig.addAll(this.alertMailNoticeConfigService.getAllAlertMailNoticeConfigByAlertConfigSystem(alertConfigSystem));
			listAllAlertGraylogNoticeConfig.addAll(this.alertGrayLogNoticeConfigService.getAllAlertGraylogNoticeConfigByAlertConfigSystem(alertConfigSystem));

		}

		final TemplateDeleteDTO templateDeleteDTO = new TemplateDeleteDTO();
		templateDeleteDTO.setTemplate(template);
		templateDeleteDTO.setListAlertTypeMonitoriza(listAllAlertTypeMonitoriza);
		templateDeleteDTO.setListAlertTypeTemplateMonitoriza(listAlertTypeTemplateMonitoriza);
		templateDeleteDTO.setListAllAlertConfigMonitoriza(listAllAlertConfigMonitoriza);
		templateDeleteDTO.setListAllAlertConfigSystem(listAllAlertConfigSystem);
		templateDeleteDTO.setListAllAlertGraylogNoticeConfig(listAllAlertGraylogNoticeConfig);
		templateDeleteDTO.setListAllAlertMailNoticeConfig(listAllAlertMailNoticeConfig);
		templateDeleteDTO.setListAllAlertResumeType(listAllAlertResumeType);
		templateDeleteDTO.setListApplicationMonitoriza(listApplicationMonitoriza);
		return templateDeleteDTO;
	}

	/**
	 * Method that returns the AlertTypeMonitoriza of a list of AlertTypeTemplateMonitoriza.
	 * @param listAlertTypeTemplateMonitoriza List if AlertTypeTemplateMonitoriza.
	 * @return List<AlertTypeMonitoriza> list of AlertTypeMonitoriza.
	 */
	private List<AlertTypeMonitoriza> getAllAlertTypeMonitoriza(final List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza){
		final List<AlertTypeMonitoriza> listAllAlertTypeMonitoriza = new ArrayList<>();
		for(final AlertTypeTemplateMonitoriza alertTypeTemplate : listAlertTypeTemplateMonitoriza){
			listAllAlertTypeMonitoriza.add(this.alertTypeMonitorizaService.getAlertTypeMonitorizaById(alertTypeTemplate.getAlertTypeMonitoriza().getIdTypeMonitoriza()));
		}
		return listAllAlertTypeMonitoriza;
	}

	/**
	 * Method that get all AlertConfigSystem by the list of AlertConfigMonitoriza.
	 * @param listAllAlertConfigMonitoriza List alert configurations from obtain.
	 * @return List<AlertConfigSystem> AlertConfigSystem list.
	 */
	private List<AlertConfigSystem> getAllAlertConfigSystem(final List<AlertConfigMonitoriza> listAllAlertConfigMonitoriza){
		final List<AlertConfigSystem> listAllAlertConfigSystem = new ArrayList<>();
		for(final AlertConfigMonitoriza alerConfig : listAllAlertConfigMonitoriza){
			listAllAlertConfigSystem.addAll(this.alertConfigSystemService.getAllAlertConfigSystemByAlertConfigMonitoriza(alerConfig));
		}
		return listAllAlertConfigSystem;
	}
}