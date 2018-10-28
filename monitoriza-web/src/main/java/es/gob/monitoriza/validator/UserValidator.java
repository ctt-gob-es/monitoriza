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
 * <b>File:</b><p>es.gob.monitoriza.validator.UserValidator.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>3 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.gob.monitoriza.persistence.configuration.dto.UserDTO;
import es.gob.monitoriza.service.IUserMonitorizaService;


/** 
 * <p>Validator class for user inputs.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/10/2018.
 */
@Component
public class UserValidator implements Validator {
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IUserMonitorizaService userService; 

	/**
	 * {@inheritDoc}
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return UserDTO.class.isAssignableFrom(arg0);
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		UserDTO userForm = (UserDTO) target;

		if (userService.getUserMonitorizaByLogin(userForm.getLogin()) != null) {
            errors.rejectValue("login", "Ya existe un usuario con ese login");
            
        }

	}

}
