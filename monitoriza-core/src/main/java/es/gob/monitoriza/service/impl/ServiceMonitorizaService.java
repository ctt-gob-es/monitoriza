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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ServiceMonitorizaService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.RequestServiceFile;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.AlarmMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.PlatformRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.ServiceMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.ServiceMonitorizaDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.TimerSpecification;
import es.gob.monitoriza.service.IServiceMonitorizaService;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for ServiceMonitoriza.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.2, 28/10/2018.
 */
@Service
public class ServiceMonitorizaService implements IServiceMonitorizaService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ServiceMonitorizaRepository repositoryService;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private TimerMonitorizaRepository repositoryTimer;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmMonitorizaRepository repositoryAlarm;
		
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private TimerScheduledRepository repositoryScheduled;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private PlatformRepository repositoryplatform;

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ServiceMonitorizaDatatableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getServiceMonitorizaById(java.lang.Long)
	 */
	@Override
	public ServiceMonitoriza getServiceMonitorizaById(Long serviceId) {

		return repositoryService.findByIdService(serviceId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#deleteServiceMonitoriza(java.lang.Long)
	 */
	@Override
	@Transactional(noRollbackFor = EmptyResultDataAccessException.class)
	public void deleteServiceMonitoriza(Long serviceId) {
		
		if (serviceId != null) {
			try {
				
				ServiceMonitoriza serviceMonitoriza = repositoryService.findByIdService(serviceId);
				repositoryService.deleteById(serviceId);
						
				TimerScheduled scheduled = repositoryScheduled.findByTimerIdTimer(serviceMonitoriza.getTimer().getIdTimer());
						
				scheduled.setUpdated(false);
				
				repositoryScheduled.save(scheduled);
				
				
			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllServiceMonitoriza()
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllServiceMonitoriza() {

		return repositoryService.findAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<ServiceMonitoriza> findAll(DataTablesInput input) {

		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllByTimer(es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza)
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllByTimer(TimerMonitoriza timer) {
				
		TimerSpecification byTimer = new TimerSpecification(timer);
		
		return repositoryService.findAll(byTimer);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllByPlatform(es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza)
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllByPlatform(PlatformMonitoriza platform) {
		
		return repositoryService.findByPlatformIdPlatform(platform.getIdPlatform());
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllByAlarm(es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza)
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllByAlarm(AlarmMonitoriza alarm) {
		
		return repositoryService.findByAlarmIdAlarm(alarm.getIdAlarm());
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#saveServiceMonitoriza(es.gob.monitoriza.persistence.configuration.dto.ServiceDTO)
	 */
	@Override
	@Transactional
	public ServiceMonitoriza saveServiceMonitoriza(ServiceDTO serviceDto, MultipartFile file) throws IOException {
		
		ServiceMonitoriza serviceMonitoriza = null;
		RequestServiceFile requestFile = new RequestServiceFile();
		boolean elServicioHaCambiado = false;
		boolean nuevoTimerSeleccionado = false;
		TimerMonitoriza nuevoTimer = null;
		
		// Si es una edición, se recupera el servicio original de la persistencia
		// para comprobar si existen cambios respecto a los datos del formulario.
		if (serviceDto.getIdService() != null) {
			serviceMonitoriza = repositoryService.findByIdService(serviceDto.getIdService());
			elServicioHaCambiado = isServiceUpdatedForm(serviceDto, serviceMonitoriza, file);
			
			// Si se ha seleccionado un nuevo timer, habrá que reprogamar el original y el nuevo.
			nuevoTimerSeleccionado = !serviceDto.getTimer().equals(serviceMonitoriza.getTimer().getIdTimer());
			
			if (nuevoTimerSeleccionado) {
				nuevoTimer = serviceMonitoriza.getTimer();
			}
			
		} else {
			serviceMonitoriza = new ServiceMonitoriza();
		}
						
		serviceMonitoriza.setDegradedThreshold(serviceDto.getDegradedThreshold());
		serviceMonitoriza.setLostThreshold(serviceDto.getLostThreshold());
		serviceMonitoriza.setName(serviceDto.getName());
		serviceMonitoriza.setNameWsdl(serviceDto.getNameWsdl());
		serviceMonitoriza.setAlarm(repositoryAlarm.findByIdAlarm(serviceDto.getAlarm()));
		serviceMonitoriza.setPlatform(repositoryplatform.findByIdPlatform(serviceDto.getPlatform()));
		serviceMonitoriza.setTimeout(serviceDto.getTimeout());
		serviceMonitoriza.setTimer(repositoryTimer.findByIdTimer(serviceDto.getTimer()));
		
		serviceMonitoriza.setServiceType(serviceDto.getServiceType());
		
		// Se añade/modifica el fichero de peticiones
		if (file != null && !file.isEmpty()) {
			
			requestFile.setIdRequestServiceFile(serviceDto.getIdFile());
			requestFile.setFilename(file.getOriginalFilename());
			requestFile.setContentType(file.getContentType());
			requestFile.setFiledata(file.getBytes());
			serviceMonitoriza.setRequestFile(requestFile);
		}
					
		ServiceMonitoriza service = repositoryService.save(serviceMonitoriza);
		
		// Si el servicio ha cambiado o es nuevo, hay que gestionar la programación de timer asociado (un servicio nunca debería estar en más de un timer)
		if (elServicioHaCambiado || serviceDto.getIdService() == null) {
			
			// Actualizar en bd (tabla TIMER_SCHEDULED) el timer poniendo IS_UPDATED a false
							
			TimerScheduled scheduled = repositoryScheduled.findByTimerIdTimer(serviceDto.getTimer());		
			
			// Si el timer asociado aún no ha sido programado, se añade para que la tarea programada lo haga.
			if (scheduled == null) {
				scheduled = new TimerScheduled();
				scheduled.setTimer(repositoryTimer.findByIdTimer(serviceDto.getTimer()));
			}
			
			// En cualquier caso, se pondrá la bandera a false para que la tarea lo procese
			scheduled.setUpdated(false);
			repositoryScheduled.save(scheduled);		

			// Si además lo que se ha cambiado es el timer asociado al servicio, hay que actualizar el nuevo timer seleccionado y el antiguo.
			if (nuevoTimerSeleccionado) {
				
				TimerScheduled scheduledOld = repositoryScheduled.findByTimerIdTimer(nuevoTimer.getIdTimer());

				// Si el antiguo timer nunca había sido programado, se ignora el cambio
				if (scheduledOld != null) {
					scheduledOld.setUpdated(false);
					repositoryScheduled.save(scheduledOld);
				}
			}
		}		
		
		return service;
	}
	
	/**
	 * Method that checks if there are changes between the service form and the persisted service.
	 * @param serviceForm Form object for the service.
	 * @param service Entity object for the service.
	 * @param file 
	 * @return true if there are changes between the service form and the persisted service.
	 * @throws IOException 
	 */
	private boolean isServiceUpdatedForm(final ServiceDTO serviceForm, final ServiceMonitoriza service, MultipartFile file) throws IOException {
		
		boolean filesAreEquals = file.isEmpty() || Arrays.equals(file.getBytes(), service.getRequestFile().getFiledata());
						
		return !(serviceForm.getAlarm().equals(service.getAlarm().getIdAlarm()) 
				&& serviceForm.getDegradedThreshold().equals(service.getDegradedThreshold())
				&& serviceForm.getLostThreshold().equals(service.getLostThreshold())
				&& serviceForm.getName().equals(service.getName())
				&& serviceForm.getNameWsdl().equals(service.getNameWsdl())
				&& serviceForm.getPlatform().equals(service.getPlatform().getIdPlatform())
				&& serviceForm.getServiceType().equals(service.getServiceType())
				&& serviceForm.getTimeout().equals(service.getTimeout())
				&& serviceForm.getTimer().equals(service.getTimer().getIdTimer())
				&& filesAreEquals);
		
	}

}
