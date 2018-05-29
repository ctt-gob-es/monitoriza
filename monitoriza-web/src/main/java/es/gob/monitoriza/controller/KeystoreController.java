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
 * <b>File:</b><p>es.gob.monitoriza.controller.KeystoreController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 14 may. 2018.
 */
package es.gob.monitoriza.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.form.SslForm;
import es.gob.monitoriza.service.ISystemCertificateService;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 14 may. 2018.
 */
@Controller
public class KeystoreController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ISystemCertificateService sysCertService; 
	
	
	/**
	 * Method that maps the list keystore web requests to the controller and forwards the list of keystores
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "ssladmin")
    public String keystoreadmin(Model model){
						
        return "fragments/ssladmin.html";
    }
	
	/**
	 * Method that maps the add keystore web requests to the controller and forwards to the form
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addssl")
    public String addSsl(Model model){
		
		model.addAttribute("sslform", new SslForm());
						
        return "modal/sslForm.html";
    }
	
	/**
     * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
     * @param file 
     * @param sslForm Object that represents the backing user form. 
     * @return 
     */
	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="loadssl", method=RequestMethod.POST)
    public String loadssl(@RequestParam("file") MultipartFile file, Model model) throws IOException {
    		
		SslForm sslForm = new SslForm();
		
		sslForm.setCertBytes(file.getBytes());
		sslForm.setFile(file);
		
		model.addAttribute("sslform", sslForm);
        
		return "modal/sslForm.html";

    }
	
}
