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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDeleteDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailResumeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitorizaID;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.service.IAlertConfigMonitorizaService;
import es.gob.monitoriza.service.IAlertConfigSystemService;
import es.gob.monitoriza.service.IAlertGrayLogNoticeConfigService;
import es.gob.monitoriza.service.IAlertMailNoticeConfigService;
import es.gob.monitoriza.service.IAlertResumeSystemService;
import es.gob.monitoriza.service.IAlertResumeTypeService;
import es.gob.monitoriza.service.IAlertTypeMonitorizaService;
import es.gob.monitoriza.service.IAlertTypeTemplateMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
import es.gob.monitoriza.service.ITemplateMonitorizaService;

/**
 * <p>Class that manages the REST requests related to the Users administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.7, 14/03/2019.
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
	 * Method that maps the list templates web requests to the controller and
	 * forwards the list of templates to the view.
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/templatesdatatable", method = RequestMethod.GET)
	public DataTablesOutput<TemplateMonitoriza> templates(@NotEmpty final DataTablesInput input) {
		return this.templateService.findAll(input);
	}

	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(value = "/importtemplate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	public DataTablesOutput<TemplateMonitoriza> getTemplateData(@RequestParam("file") final MultipartFile file) {
		Document doc =  null;
		DataTablesOutput<TemplateMonitoriza> dtOutput = null;

		try{    
			dtOutput = new DataTablesOutput<TemplateMonitoriza>();
	        InputStream is = file.getInputStream();
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();                 
	        doc = dBuilder.parse(is);

	        NodeList l =doc.getChildNodes().item(0).getChildNodes();

	        String templateName =  null;
	        String templateDescription =  null;
	        
	        TemplateMonitoriza template = new TemplateMonitoriza();
	        
 	        for(int i = 0; i<l.getLength(); i++){
	        	Node n =l.item(i);
	        	if( n.getNodeType()==Node.ELEMENT_NODE && n.getNodeName().equals("name")){
	        		templateName = n.getFirstChild().getNodeValue();
	        		template.setName(templateName);
	        	}
	        	if( n.getNodeType()==Node.ELEMENT_NODE && n.getNodeName().equals("description")){
	        		templateDescription = n.getFirstChild().getNodeValue();
	        		template.setDescription(templateDescription);
	        	}
	        	
	        }
 	        
 	        NodeList nLists = doc.getElementsByTagName("alarm");
	        List<AlertTypeMonitoriza> listAlertTypeMonitoriza = new ArrayList<>();
	        for(int temp = 0; temp < nLists.getLength(); temp++) {
	        	  Node nNode = nLists.item(temp);

	        	  if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	        		AlertTypeMonitoriza alarm = new AlertTypeMonitoriza();
	        		
	        	    Element eElement = (Element) nNode;
	        	    alarm.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
	        	    alarm.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
	        	    AlertTypeMonitoriza alertTypeMonitoriza = alertTypeMonitorizaService.saveAlertTypeMonitoriza(alarm);
	        	    listAlertTypeMonitoriza.add(alertTypeMonitoriza);
	        	  }
	        }

 	       TemplateMonitoriza result = templateService.saveTemplateMonitoriza(template);
 	 
 	       for(AlertTypeMonitoriza alert : listAlertTypeMonitoriza){
 	    	  AlertTypeTemplateMonitoriza alertTypeTemplateMonitoriza = new AlertTypeTemplateMonitoriza();
 	    	  AlertTypeTemplateMonitorizaID alertTypeTemplateMonitorizaID = new AlertTypeTemplateMonitorizaID();
 	    	  alertTypeTemplateMonitorizaID.setIdTemplateMonitoriza(result.getIdTemplateMonitoriza());
 	    	  alertTypeTemplateMonitorizaID.setIdTypeMonitoriza(alert.getIdTypeMonitoriza());
 	    	  
 	    	  alertTypeTemplateMonitoriza.setIdAlertTypeTemplateMonitorizaID(alertTypeTemplateMonitorizaID);
 	    	  alertTypeTemplateMonitoriza.setAlertTypeMonitoriza(alert);
 	    	  alertTypeTemplateMonitoriza.setTemplateMonitoriza(result);
 	    	  alertTypeTemplateMonitorizaService.saveAlertTypeTemplateMonitoriza(alertTypeTemplateMonitoriza);
 	       }
 	      
 	        
	      } catch (SAXException | IOException | ParserConfigurationException ex) {
	    	  
	    }
		
		List<TemplateMonitoriza> listTemplates = (List<TemplateMonitoriza>) templateService.getAllTemplateMonitoriza();
		dtOutput.setData(listTemplates);
		return dtOutput;
	}
	
	@RequestMapping(value = "/exporttemplate", method = RequestMethod.GET)
	@ResponseBody
	public void exportTemplate(HttpServletResponse response, @RequestParam("idTemplateMonitoriza") final Long idTemplateMonitoriza) {
		
		StreamResult result = null;
		TemplateMonitoriza template = null;
		
		try {
			  template = this.templateService.getTemplateMonitorizaById(idTemplateMonitoriza);
			  String nameTemplate = template.getName();
		      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		      //Template
		      Document doc = docBuilder.newDocument();
		      Element rootElement = doc.createElement("Template");
		      rootElement.setAttribute("xmlns", "urn:es:gob:sgad:afirma:monitoriza");
		      doc.appendChild(rootElement);
		      //Name
		      Element name = doc.createElement("name");
		      name.setTextContent(nameTemplate);
		      rootElement.appendChild(name);
		      //Description
		      Element description = doc.createElement("description");
		      description.setTextContent(template.getDescription());
		      rootElement.appendChild(description);
		      
		      //Alarms
		      Element alarms = doc.createElement("alarms");
		      
		      
		      List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza = this.alertTypeTemplateMonitorizaService.getAllAlertTypeTemplateMonitorizaByTemplateMonitoriza(template);
		      
		      for(AlertTypeTemplateMonitoriza item : listAlertTypeTemplateMonitoriza){
		    	  Element alarm = doc.createElement("alarm");
		    	  //Name Alarm
		    	  Element nameAlarm = doc.createElement("name");
		    	  nameAlarm.setTextContent(item.getAlertTypeMonitoriza().getName());
		    	  alarm.appendChild(nameAlarm);
		    	  //Description Alarm
		    	  Element descriptionAlarm = doc.createElement("description");
		    	  descriptionAlarm.setTextContent(item.getAlertTypeMonitoriza().getDescription());
		    	  alarm.appendChild(descriptionAlarm);
		    	  
		    	  alarms.appendChild(alarm);
		    	  
		      }
		      
		      rootElement.appendChild(alarms);
		      
		      
		      
		      //Se escribe el contenido del XML en un archivo
		      result = transformerFactory(doc, nameTemplate);
		      
		      //Se descarga el XML

		      String xml = replace(result);

		      byte[] bFile = Files.readAllBytes(new File(xml).toPath());
		      InputStream in = new ByteArrayInputStream(bFile);           
		      response.setContentLength(bFile.length);
		      response.setHeader("Content-Disposition", "attachment; filename=" + nameTemplate+".xml");
		      FileCopyUtils.copy(in, response.getOutputStream());

		      
		    } catch (TransformerException tfe) {
		      tfe.printStackTrace();
		    } catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		      
	      
	}
	

	private StreamResult transformerFactory(Document doc, String nameTemplate)
		 throws TransformerConfigurationException, TransformerException {

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StreamResult result = new StreamResult(new File(nameTemplate+".xml"));
		DOMSource docDom = new DOMSource(doc);
		transformer.transform(docDom, result);
		return result;
	}
	
	private String replace(StreamResult result){
		String[] path = result.getSystemId().split("file:/");
	    String xml = path[1].replace("%20", " ");
	    xml = xml.replace("\\", "/");
		return xml;
	}
	
	@RequestMapping(value = "/edittemplate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public DataTablesOutput<TemplateMonitoriza> editTemplateData(@RequestBody TemplateDTO templateform, @RequestParam("idTemplateMonitoriza") final Long idTemplateMonitoriza, final BindingResult bindingResult) { // 
		
		DataTablesOutput<TemplateMonitoriza> dtOutput = new DataTablesOutput<TemplateMonitoriza>();
		
		templateform.setIdTemplateMonitoriza(idTemplateMonitoriza);
		this.templateService.saveTemplateMonitorizaWithDTO(templateform);

		List<TemplateMonitoriza> listTemplates = (List<TemplateMonitoriza>) templateService.getAllTemplateMonitoriza();
		dtOutput.setData(listTemplates);
		return dtOutput;
	}
	
	@RequestMapping(path = "/deletetemplate", method = RequestMethod.POST)
	@Transactional
	@JsonView(DataTablesOutput.View.class)
	public DataTablesOutput<TemplateMonitoriza> deleteTemplate(@RequestParam("idTemplateMonitoriza") final Long idTemplateMonitoriza) {

		TemplateDeleteDTO templateDeleteDTO = getTemplateDeleteDto(idTemplateMonitoriza);
		
		templateService.deleteTemplateMonitorizaWithDTO(templateDeleteDTO);		

		DataTablesOutput<TemplateMonitoriza> dtOutput = new DataTablesOutput<TemplateMonitoriza>();		
		List<TemplateMonitoriza> listTemplates = (List<TemplateMonitoriza>) templateService.getAllTemplateMonitoriza();
		dtOutput.setData(listTemplates);
		return dtOutput;
	}
	
	
	public TemplateDeleteDTO getTemplateDeleteDto(Long idTemplateMonitoriza){
		TemplateMonitoriza template = this.templateService.getTemplateMonitorizaById(idTemplateMonitoriza);

		List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza = alertTypeTemplateMonitorizaService.getAllAlertTypeTemplateMonitorizaByTemplateMonitoriza(template);		
		List<ApplicationMonitoriza> listApplicationMonitoriza = applicationMonitorizaService.getAllApplicationMonitorizaByTemplateMonitoriza(template);
		List<AlertTypeMonitoriza> listAllAlertTypeMonitoriza = getAllAlertTypeMonitoriza(listAlertTypeTemplateMonitoriza);
		List<AlertResumeType> listAllAlertResumeType = new ArrayList<>();
		List<AlertConfigMonitoriza> listAllAlertConfigMonitoriza = new ArrayList<>();		
		List<AlertMailNoticeConfig> listAllAlertMailNoticeConfig = new ArrayList<>();
		List<AlertGraylogNoticeConfig> listAllAlertGraylogNoticeConfig = new ArrayList<>();


		for(ApplicationMonitoriza app : listApplicationMonitoriza){
			listAllAlertResumeType.addAll(alertResumeTypeService.getAllAlertResumeTypeByApplicationMonitoriza(app));
			listAllAlertConfigMonitoriza.addAll(alertConfigMonitorizaService.getAllAlertConfigMonitorizaByApplicationMonitoriza(app));
		}
		
		List<AlertConfigSystem> listAllAlertConfigSystem = getAllAlertConfigSystem(listAllAlertConfigMonitoriza);
		
		for(AlertConfigSystem alertConfigSystem : listAllAlertConfigSystem){
			listAllAlertMailNoticeConfig.addAll(alertMailNoticeConfigService.getAllAlertMailNoticeConfigByAlertConfigSystem(alertConfigSystem));
			listAllAlertGraylogNoticeConfig.addAll(alertGrayLogNoticeConfigService.getAllAlertGraylogNoticeConfigByAlertConfigSystem(alertConfigSystem));

		}
		
		TemplateDeleteDTO templateDeleteDTO = new TemplateDeleteDTO();
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

	private List<AlertTypeMonitoriza> getAllAlertTypeMonitoriza(List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza){
		List<AlertTypeMonitoriza> listAllAlertTypeMonitoriza = new ArrayList<>();
		for(AlertTypeTemplateMonitoriza alertTypeTemplate : listAlertTypeTemplateMonitoriza){
			listAllAlertTypeMonitoriza.add(alertTypeMonitorizaService.getAlertTypeMonitorizaById(alertTypeTemplate.getAlertTypeMonitoriza().getIdTypeMonitoriza()));
		}
		return listAllAlertTypeMonitoriza;
	}
	
	private List<AlertConfigSystem> getAllAlertConfigSystem(List<AlertConfigMonitoriza> listAllAlertConfigMonitoriza){
		List<AlertConfigSystem> listAllAlertConfigSystem = new ArrayList<>();
		for(AlertConfigMonitoriza alerConfig : listAllAlertConfigMonitoriza){
			listAllAlertConfigSystem.addAll(alertConfigSystemService.getAllAlertConfigSystemByAlertConfigMonitoriza(alerConfig));
		}
		return listAllAlertConfigSystem;
	}
}