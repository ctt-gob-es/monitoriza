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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.UserMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import es.gob.monitoriza.persistence.configuration.dto.UserDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserEditDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserPasswordDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.UserMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.UserDatatableRepository;
import es.gob.monitoriza.service.IUserMonitorizaService;
import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/10/2018.
 */
@Service
public class UserMonitorizaService implements IUserMonitorizaService {

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private UserMonitorizaRepository repository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private UserDatatableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getUserMonitorizaById(java.lang.Long)
	 */
	@Override
	public UserMonitoriza getUserMonitorizaById(final Long userId) {
		return repository.findByIdUserMonitoriza(userId);
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#deleteUserMonitoriza(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteUserMonitoriza(final Long userId) {
		repository.deleteById(userId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getAllUserMonitoriza()
	 */
	@Override
	public Iterable<UserMonitoriza> getAllUserMonitoriza() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getUserMonitorizaByLogin(java.lang.String)
	 */
	@Override
	public UserMonitoriza getUserMonitorizaByLogin(final String login) {
		return repository.findByLogin(login);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<UserMonitoriza> findAll(final DataTablesInput input) {

		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#saveUserMonitoriza(es.gob.monitoriza.persistence.configuration.dto.UserDTO)
	 */
	@Override
	@Transactional
	public UserMonitoriza saveUserMonitoriza(UserDTO userDto) {
		
		UserMonitoriza userMonitoriza = null;
		
		if (userDto.getIdUserMonitoriza() != null) {
			userMonitoriza = repository.findByIdUserMonitoriza(userDto.getIdUserMonitoriza());
		} else {
			userMonitoriza = new UserMonitoriza();
		}
		if (!StringUtils.isEmpty(userDto.getPassword())) {
			String pwd = userDto.getPassword();
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			String hashPwd = bcpe.encode(pwd);

			userMonitoriza.setPassword(hashPwd);
		}

		userMonitoriza.setLogin(userDto.getLogin());
		userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
		userMonitoriza.setEmail(userDto.getEmail());
		userMonitoriza.setIsBlocked(Boolean.FALSE);
		userMonitoriza.setLastAccess(null);
		userMonitoriza.setLastIpAccess(null);
		userMonitoriza.setName(userDto.getName());
		userMonitoriza.setSurnames(userDto.getSurnames());
		
		return repository.save(userMonitoriza);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#updateUserMonitoriza(es.gob.monitoriza.persistence.configuration.dto.UserDTO)
	 */
	@Override
	@Transactional
	public UserMonitoriza updateUserMonitoriza(UserEditDTO userDto) {
		
		UserMonitoriza userMonitoriza = null;
		
		if (userDto.getIdUserMonitorizaEdit() != null) {
			userMonitoriza = repository.findByIdUserMonitoriza(userDto.getIdUserMonitorizaEdit());
		} else {
			userMonitoriza = new UserMonitoriza();
		}
		userMonitoriza.setLogin(userDto.getLoginEdit());
		userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
		userMonitoriza.setEmail(userDto.getEmailEdit());
		userMonitoriza.setIsBlocked(Boolean.FALSE);
		userMonitoriza.setLastAccess(null);
		userMonitoriza.setLastIpAccess(null);
		userMonitoriza.setName(userDto.getNameEdit());
		userMonitoriza.setSurnames(userDto.getSurnamesEdit());

		return repository.save(userMonitoriza);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#changeUserMonitorizaPassword(es.gob.monitoriza.persistence.configuration.dto.UserPasswordDTO)
	 */
	@Override
	@Transactional
	public String changeUserMonitorizaPassword(UserPasswordDTO userPasswordDto) {
		
		UserMonitoriza userMonitoriza = repository.findByIdUserMonitoriza(userPasswordDto.getIdUserMonitorizaPass());
		String result = null;
		
		String oldPwd = userPasswordDto.getOldPassword();
		String pwd = userPasswordDto.getPassword();

		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		String hashPwd = bcpe.encode(pwd);

		try {
			if (bcpe.matches(oldPwd, userMonitoriza.getPassword())) {
				userMonitoriza.setPassword(hashPwd);

				repository.save(userMonitoriza);
				result = "0";
			} else {
				result = "-1";
			}
		} catch (Exception e) {
			result = "-2";
			throw e;
		}
		
		return result;
		
	}

}
