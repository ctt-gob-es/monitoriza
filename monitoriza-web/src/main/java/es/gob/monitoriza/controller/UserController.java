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
 * <b>File:</b><p>es.gob.monitoriza.controller.UserController.java.</p>
 * <b>Description:</b><p>Class that manages the requests related to the Users administration.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 8 mar. 2018.
 */
package es.gob.monitoriza.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.form.UserForm;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.service.IUserMonitorizaService;
import es.gob.monitoriza.utilidades.NumberConstants;

/** 
 * <p>Class that manages the requests related to the Users administration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 8 mar. 2018.
 */
@Controller
public class UserController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IUserMonitorizaService userService; 

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "useradmin")
    public String index(Model model){
        return "fragments/useradmin.html";
    }
		
	
	/**
	 * Method that maps the add user web request to the controller and sets the backing form. 
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public String addUser(Model model){
		model.addAttribute("userform", new UserForm());
		return "modal/userForm";
    }	

    /**
     * Method that maps the edit user web request to the controller and loads the user to the
     * backing form.
     * @param userId Identifier of the user to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editUser(@RequestParam("id") Long userId, Model model){
    	UserMonitoriza user = userService.getUserMonitorizaById(userId);
    	UserForm userForm = new UserForm();
    	userForm.setPassword(user.getPassword());
    	userForm.setLogin(user.getLogin());
    	userForm.setEmail(user.getEmail());
    	userForm.setName(user.getName());
    	userForm.setSurnames(user.getSurnames());
    	model.addAttribute("userform", userForm);
        return "modal/userForm";
    }
    
    /**
     * Method that maps the save user web request to the controller and saves it in the persistence.
     * @param userForm Object that represents the backing user form.
     * @param bindingResult Object that represents the form validation result. 
     * @return String that represents the name of the view to redirect.
     */
//    @RequestMapping(value = "saveuser", method = RequestMethod.POST)
//    public String save(@Valid @ModelAttribute("userform") UserForm userForm, BindingResult bindingResult) {
//    	LOGGER.debug(bindingResult.toString());
//    	if (!bindingResult.hasErrors()) { // validation errors
//    		
//	    		String pwd = userForm.getPassword();
//		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//		    	String hashPwd = bc.encode(pwd);
//	
//		    	UserMonitoriza newUser = new UserMonitoriza();
//		    	newUser.setPassword(hashPwd);
//		    	newUser.setLogin(userForm.getLogin());
//		    	newUser.setAttemptsNumber(NumberConstants.NUM0);
//		    	newUser.setEmail(userForm.getEmail());
//		    	newUser.setIsBlocked(Boolean.FALSE);
//		    	newUser.setLastAccess(null);
//		    	newUser.setLastIpAccess(null);
//		    	newUser.setName(userForm.getName());
//		    	newUser.setSurnames(userForm.getSurnames());
//		    	
//		    	if (userService.getUserMonitorizaByLogin(userForm.getLogin()) == null) {
//		    		userService.addUserMonitoriza(newUser);
//		    	}
//		    	else {
//	    			bindingResult.rejectValue("login", "error.userexists", "Username already exists");    	
//	    			return "modal/userForm";	    		
//		    	}
//		    	
//		    	return "blank";
//    		
//    	}
//    	else {
//    		  		
//    		return "modal/userForm";
//    		
//    	}
//        	
//    }    
}
