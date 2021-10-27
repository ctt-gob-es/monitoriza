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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.SystemNotificationService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.constant.INotificationPriority;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationOrigin;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationPriority;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationType;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification;
import es.gob.monitoriza.persistence.configuration.model.repository.CNotificationOriginRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.CNotificationPriorityRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.CNotificationTypeRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemNotificationRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.SystemNotificationDatatableRepository;
import es.gob.monitoriza.service.ISystemNotificationService;


/**
 * <p>Class that implements the communication with the operations of the persistence layer for SystemNotification.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
@Service("systemNotificationService")
public class SystemNotificationService implements ISystemNotificationService {

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private SystemNotificationRepository repository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private CNotificationTypeRepository typeRepository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private CNotificationOriginRepository originRepository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private CNotificationPriorityRepository priorityRepository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private SystemNotificationDatatableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#getSystemNotificationById(java.lang.Long)
	 */
	@Override
	public SystemNotification getSystemNotificationById(final Long sysNotificationId) {

		return this.repository.findByIdSystemNotification(sysNotificationId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#registerSystemNotification(es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification)
	 */
	@Override
	@Transactional
	public void registerSystemNotification(final Long idNotificationType, final Long idNotificationOrigin, final Long idNotificationPriority, final String msg) {

		// Se prepara y registra el aviso de sistema sobre el registro del nodo.
		final SystemNotification aviso = new SystemNotification();
		aviso.setCreationDate(LocalDateTime.now());

		if (INotificationPriority.ID_NOTIFICATION_PRIORITY_IMPORTANT.equals(idNotificationPriority)) {
			// Si la notificación es importante, ésta no caducará.
    		aviso.setExpirationDate(null);
		} else {
    		// Se establece la fecha de expiración a 7 días tras la fecha de creación.
    		aviso.setExpirationDate(LocalDateTime.now().plusDays(NumberConstants.NUM7));
		}

		aviso.setNotificationType(getNotificationTypeById(idNotificationType));
		aviso.setNotificationOrigin(getNotificationOriginById(idNotificationOrigin));
		aviso.setNotificationPriority(getNotificationPriorityById(idNotificationPriority));
		aviso.setDescription(msg);
		aviso.setIsOk(Boolean.FALSE);

		//repository.saveAndFlush(aviso);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#deleteSystemNotification(es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification)
	 */
	@Override
	@Transactional
	public void deleteSystemNotification(final SystemNotification systemNotification) {
		this.repository.delete(systemNotification);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#getNotificationTypeById(java.lang.Long)
	 */
	@Override
	public CNotificationType getNotificationTypeById(final Long idNotificationType) {

		return this.typeRepository.findByIdNotificationType(idNotificationType);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#findAllSystemNotifications()
	 */
	@Override
	public DataTablesOutput<SystemNotification> findAllSystemNotifications(final DataTablesInput input) {

		return this.dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#getNotificationOriginById(java.lang.Long)
	 */
	@Override
	public CNotificationOrigin getNotificationOriginById(final Long idNotificationOrigin) {

		return this.originRepository.findByIdNotificationOrigin(idNotificationOrigin);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#getNotificationPriorityById(java.lang.Long)
	 */
	@Override
	public CNotificationPriority getNotificationPriorityById(final Long idNotificationPriority) {

		return this.priorityRepository.findByIdNotificationPriority(idNotificationPriority);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#getSystemNotificationByOrigin(java.lang.Long)
	 */
	@Override
	public SystemNotification getSystemNotificationByOrigin(final Long originNotificationId) {

		return this.repository.findByNotificationOriginIdNotificationOrigin(originNotificationId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#clearExpiredNotifications()
	 */
	@Override
	@Transactional
	public void clearExpiredNotifications() {

		final Iterable<SystemNotification> all = this.repository.findAll();
		final List<SystemNotification> expired = new ArrayList<>();

		for (final SystemNotification sysNot : all) {
			if (sysNot.getExpirationDate() != null && sysNot.getExpirationDate().isBefore(LocalDateTime.now()) ) {
				expired.add(sysNot);
			}
		}

		this.repository.deleteAll(expired);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#hasPendingNotifications()
	 */
	@Override
	public Boolean hasPendingNotifications() {

		return !this.repository.findByIsOk(Boolean.FALSE).isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemNotificationService#checkNotification(es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification)
	 */
	@Override
	@Transactional
	public void checkNotification(final SystemNotification sysNot) {

		sysNot.setIsOk(Boolean.TRUE);
		this.repository.saveAndFlush(sysNot);
	}

}
