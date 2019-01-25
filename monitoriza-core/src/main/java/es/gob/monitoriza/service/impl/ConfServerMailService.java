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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ConfServerMailService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ConfServerMailMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 25/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.exception.CipherException;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfServerMailDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.persistence.configuration.model.repository.ConfServerMailRepository;
import es.gob.monitoriza.service.IConfServerMailService;
import es.gob.monitoriza.utilidades.AESCipher;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for ConfServerMail.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.4, 25/01/2019.
 */
@Service("serverMailService")
public class ConfServerMailService implements IConfServerMailService {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ConfServerMailRepository repository;

	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IConfServerMailService#getAllConfServerMail()
	 */
	@Override
	public ConfServerMail getAllConfServerMail() {
		
		List<ConfServerMail> allConf = new ArrayList<>();
				
		try {
			allConf = repository.findAll();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		
		if (!allConf.isEmpty()) {
			return allConf.get(0);
		} else {
			return null;
		}
	}

	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IConfServerMailService#getConfServerMailById(java.lang.Long)
	 */
	@Override
	public ConfServerMail getConfServerMailById(Long idConfServerMail) {
		return repository.findByIdConfServerMail(idConfServerMail);
	}

	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IConfServerMailService#saveConfServerMail(es.gob.monitoriza.persistence.configuration.dto.ConfServerMailDTO)
	 */
	@Override
	@Transactional
	public ConfServerMail saveConfServerMail(ConfServerMailDTO confServerMailDto) {
		
		ConfServerMail confMail = new ConfServerMail();
		
		if (confServerMailDto.getIdConfServerMail() != null) {
			confMail = repository.findByIdConfServerMail(confServerMailDto.getIdConfServerMail());
		} else {
			confMail = new ConfServerMail();
		}
	
		String pwd = confServerMailDto.getPasswordMail();
		String hashPwd = null;
		
		if (pwd != null && !"".equals(pwd)) {
						
			try {
				hashPwd = new String(AESCipher.getInstance().encryptMessage(pwd));
			} catch (CipherException e) {
				
				LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE015), e);
			} 
		}
	
		confMail.setIssuerMail(confServerMailDto.getIssuerMail());
		confMail.setHostMail(confServerMailDto.getHostMail());
		confMail.setPortMail(confServerMailDto.getPortMail());
		confMail.setTslMail(confServerMailDto.getTslMail() == null?Boolean.FALSE:Boolean.TRUE);
		confMail.setAuthenticationMail(confServerMailDto.getAuthenticationMail() == null?Boolean.FALSE:Boolean.TRUE);
		confMail.setUserMail(confServerMailDto.getUserMail());
		confMail.setPasswordMail(hashPwd);

		final ConfServerMail result = repository.save(confMail);
				
		return result;
	}

	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IConfServerMailService#deleteConfServerMail(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteConfServerMail(Long idConfServerMail) {
		repository.deleteById(idConfServerMail);
	}

}
