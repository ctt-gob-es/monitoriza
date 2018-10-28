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
 * <b>File:</b><p>es.gob.monitoriza.service.IUserMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.UserDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserEditDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserPasswordDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 28/10/2018.
 */
public interface IUserMonitorizaService {

	/**
	 * Method that obtains an user by its identifier.
	 * @param userId The user identifier.
	 * @return {@link UserMonitoriza}
	 */
	UserMonitoriza getUserMonitorizaById(Long userId);

	/**
	 * Method that obtains an user by its login.
	 * @param login The user login.
	 * @return {@link UserMonitoriza}
	 */
	UserMonitoriza getUserMonitorizaByLogin(String login);

	/**
	 * Method that deletes a user in the persistence.
	 * @param userId {@link Integer} that represents the user identifier to delete.
	 */
	void deleteUserMonitoriza(Long userId);

	/**
	 * Method that gets all the users from the persistence.
	 * @return a {@link Iterable<UserMonitoriza>} with the information of all users.
	 */
	Iterable<UserMonitoriza> getAllUserMonitoriza();

	/**
	 * Method that gets all the users from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<UserMonitoriza> findAll(DataTablesInput input);
	
	/**
	 * Method that stores a user in the persistence.
	 * @param userDto a {@link UserDTO} with the information of the user.
	 * @return {@link UserMonitoriza} The user.
	 */
	UserMonitoriza saveUserMonitoriza(UserDTO userDto);
	
	/**
	 * Method that updates a user in the persistence.
	 * @param userEditDto a {@link UserEditDTO} with the information of the user.
	 * @return {@link UserMonitoriza} The user.
	 */
	UserMonitoriza updateUserMonitoriza(UserEditDTO userEditDto);
	
	/**
	 * Method that change the password of a user.
	 * @param userPasswordDto a {@link UserPasswordDTO} with the information of the user.
	 * @return {@link String} The result of the password change:
	 * 		0: Change success
	 * 	   -1: Old password and new password doesn't match
	 *     -2: Error updating the user with new password
	 */
	String changeUserMonitorizaPassword(UserPasswordDTO userPasswordDto);
}
