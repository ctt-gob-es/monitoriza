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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.PlatformAfirmaService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for PlatformAfirma.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 04/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.AfirmaDTO;
import es.gob.monitoriza.persistence.configuration.dto.ClaveDTO;
import es.gob.monitoriza.persistence.configuration.dto.TsaDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.CPlatformTypeRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.PlatformRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.ServiceMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.PlatformDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.CPlatformTypeSpecification;
import es.gob.monitoriza.service.IPlatformService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for PlatformAfirma.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 04/01/2019.
 */
@Service
public class PlatformService implements IPlatformService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private PlatformRepository repository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private CPlatformTypeRepository typeRepository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private SystemCertificateRepository certRepository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerScheduledRepository scheduledRepository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private ServiceMonitorizaRepository serviceRepository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private PlatformDatatableRepository dtRepository; 

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getPlatformById(java.lang.Long)
	 */
	@Override
	public PlatformMonitoriza getPlatformById(Long afirmaId) {
		return repository.findByIdPlatform(afirmaId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#deleteUserMonitoriza(java.lang.Long)
	 */
	@Override
	public void deletePlatform(PlatformMonitoriza platform) {
		repository.delete(platform);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getAllUserMonitoriza()
	 */
	@Override
	public Iterable<PlatformMonitoriza> getAllPlatform() {
		
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<PlatformMonitoriza> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getPlatformTypeById(java.lang.Long)
	 */
	@Override
	public CPlatformType getPlatformTypeById(Long typeId) {
		
		return typeRepository.findByIdPlatformType(typeId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#findAllAfirma(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<PlatformMonitoriza> findAllAfirma(DataTablesInput input) {
				
		CPlatformType platformType = new CPlatformType();
		platformType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
		CPlatformTypeSpecification byPlatformType = new CPlatformTypeSpecification(platformType);
		
		return dtRepository.findAll(input, byPlatformType);
				
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#findAllTsa(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<PlatformMonitoriza> findAllTsa(DataTablesInput input) {
		CPlatformType platformType = new CPlatformType();
		platformType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
		CPlatformTypeSpecification byPlatformType = new CPlatformTypeSpecification(platformType);
		
		return dtRepository.findAll(input, byPlatformType);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#deletePlatformById(java.lang.Long)
	 */
	@Override
	public void deletePlatformById(Long afirmaId) {
		repository.deleteById(afirmaId);
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#savePlatformAfirma(es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza)
	 */
	@Override
	@Transactional
	public PlatformMonitoriza savePlatformAfirma(AfirmaDTO afirmaDto) {
		
		PlatformMonitoriza platformAfirma = null;
		boolean afirmaHaCambiado = false;
		
		if (afirmaDto.getIdPlatform() != null) {
			platformAfirma = repository.findByIdPlatform(afirmaDto.getIdPlatform());
			afirmaHaCambiado = isAfirmaUpdatedForm(afirmaDto, platformAfirma);
		} else {
			platformAfirma = new PlatformMonitoriza();
		}

		platformAfirma.setHost(afirmaDto.getHost());
		platformAfirma.setName(afirmaDto.getName());
		platformAfirma.setOcspContext(afirmaDto.getOcspContext());
		platformAfirma.setPort(afirmaDto.getPort());
		platformAfirma.setIsSecure(afirmaDto.getIsSecure());
		platformAfirma.setServiceContext(afirmaDto.getServiceContext());
		CPlatformType afirmaType = new CPlatformType();
		afirmaType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
		platformAfirma.setPlatformType(afirmaType);

		PlatformMonitoriza afirma = repository.save(platformAfirma);
				
		// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
		if (afirmaHaCambiado && platformAfirma.getIdPlatform() != null) {
			
			updateScheduledTimerFromPlatform(platformAfirma);
		}
		
		return afirma;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#savePlatformTsa(es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza)
	 */
	@Override
	@Transactional
	public PlatformMonitoriza savePlatformTsa(TsaDTO tsaDto) {
		
		PlatformMonitoriza platformTsa = null;
		boolean tsaHaCambiado = false;
		
		if (tsaDto.getIdPlatform() != null) {
			platformTsa = repository.findByIdPlatform(tsaDto.getIdPlatform());
			tsaHaCambiado = isTsaUpdatedForm(tsaDto, platformTsa);
		} else {
			platformTsa = new PlatformMonitoriza();
		}

		platformTsa.setHost(tsaDto.getHost());
		platformTsa.setName(tsaDto.getName());
		platformTsa.setPort(tsaDto.getPort());
		platformTsa.setIsSecure(tsaDto.getIsSecure());
		platformTsa.setServiceContext(tsaDto.getServiceContext());
		CPlatformType afirmaType = new CPlatformType();
		afirmaType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
		platformTsa.setPlatformType(afirmaType);
		platformTsa.setRfc3161Context(tsaDto.getRfc3161Context());
		platformTsa.setRfc3161Port(tsaDto.getRfc3161Port());
		platformTsa.setUseRfc3161Auth(tsaDto.getUseRfc3161Auth());
		platformTsa.setRfc3161Certificate(certRepository.findByIdSystemCertificate(tsaDto.getRfc3161Certificate()));
		
		if (tsaDto.getRfc3161Certificate() == null || tsaDto.getRfc3161Certificate().longValue() == -1) {
			platformTsa.setUseRfc3161Auth(false);
		}
				
		PlatformMonitoriza tsa = repository.saveAndFlush(platformTsa);
		
		// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
		if (tsaHaCambiado && platformTsa.getIdPlatform() != null) {
			
			updateScheduledTimerFromPlatform(platformTsa);
		}
		
		// Se construye un nuevo objeto para devolver a la vista
		PlatformMonitoriza tsaView = new PlatformMonitoriza();
		
		tsaView.setHost(tsa.getHost());
		tsaView.setName(tsa.getName());
		tsaView.setPort(tsa.getPort());
		tsaView.setIsSecure(tsa.getIsSecure());
		tsaView.setServiceContext(tsa.getServiceContext());
		tsaView.setPlatformType(tsa.getPlatformType());
		tsaView.setRfc3161Context(tsa.getRfc3161Context());
		tsaView.setRfc3161Port(tsa.getRfc3161Port());
		tsaView.setUseRfc3161Auth(tsa.getUseRfc3161Auth());
		tsaView.setRfc3161Certificate(tsa.getRfc3161Certificate());
		
		// Se construye objeto vacío para evitar warning de datatables
		if (!tsaDto.getUseRfc3161Auth()) {
			tsaView.setRfc3161Certificate(new SystemCertificate());
		}
		
		return tsaView;
	}
	
	/**
	 * Method that checks if there are changes between the afirma form and the persisted platform.
	 * @param afirmaForm Object that represents the backing form for the @firma platform
	 * @param platform Object that represents the persisted @firma platform 
	 * @return true if the data of the form has been updated
	 */
	private boolean isAfirmaUpdatedForm(AfirmaDTO afirmaForm, PlatformMonitoriza platform) {
		
		return !(afirmaForm.getIsSecure().equals(platform.getIsSecure()) &&
				 afirmaForm.getHost().equals(platform.getHost()) &&
				 afirmaForm.getName().equals(platform.getName()) &&
				 afirmaForm.getOcspContext().equals(platform.getOcspContext()) &&
				 afirmaForm.getPort().equals(platform.getPort()) &&
				 afirmaForm.getIsSecure().equals(platform.getIsSecure()) &&
				 afirmaForm.getServiceContext().equals(platform.getServiceContext()));
		
	}
	
	/**
	 * Method that checks if there are changes between the TS@ form and the persisted platform.
	 * @param tsaForm Object that represents the backing form for the @firma platform
	 * @param platform Object that represents the platform
	 * @return true if the data of the form has been updated
	 */
	private boolean isTsaUpdatedForm(TsaDTO tsaForm, PlatformMonitoriza platform) {
		
		boolean changeAuthRFC3161 = false;
		
		if (platform.getRfc3161Certificate() == null ) {
			
			changeAuthRFC3161 = tsaForm.getRfc3161Certificate() != null;
			
		} else {
			
			changeAuthRFC3161 = !platform.getRfc3161Certificate().getIdSystemCertificate().equals(tsaForm.getRfc3161Certificate());
		}
		
		return !(tsaForm.getIsSecure().equals(platform.getIsSecure()) &&
				tsaForm.getHost().equals(platform.getHost()) &&
				tsaForm.getName().equals(platform.getName()) &&
				!changeAuthRFC3161 &&
				tsaForm.getRfc3161Context().equals(platform.getRfc3161Context()) &&
				tsaForm.getRfc3161Port().equals(platform.getRfc3161Port()) &&
				tsaForm.getPort().equals(platform.getPort()) &&
				tsaForm.getIsSecure().equals(platform.getIsSecure()) &&
				tsaForm.getServiceContext().equals(platform.getServiceContext()));
		
	}
	
	/**
	 * Method that sets the scheduled timers of the services which uses this platform as updated.
	 * @param platform Object that represents the platform
	 */
	private void updateScheduledTimerFromPlatform(PlatformMonitoriza platform) {
				
		List<ServiceMonitoriza> servicesUsingThisPlatform = StreamSupport.stream(serviceRepository.findByPlatformIdPlatform(platform.getIdPlatform()).spliterator(), false).collect(Collectors.toList());
		
		TimerScheduled scheduled = null;
		for (ServiceMonitoriza service : servicesUsingThisPlatform) {
			
			scheduled =  scheduledRepository.findByTimerIdTimer(service.getTimer().getIdTimer());
			scheduled.setUpdated(false);
			scheduledRepository.save(scheduled);
			
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getAllPlatformType()
	 */
	@Override
	public Iterable<CPlatformType> getAllPlatformType() {
		
		return typeRepository.findAll();
	}
	
	/**
	 * Method that returns a list of platforms to be showed in DataTable.
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#findAllTsa(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<PlatformMonitoriza> findAllByPlatFormType(DataTablesInput input, Long platformTypeId) {
		CPlatformType platformType = new CPlatformType();
		platformType.setIdPlatformType(platformTypeId);
		CPlatformTypeSpecification byPlatformType = new CPlatformTypeSpecification(platformType);
		
		return dtRepository.findAll(input, byPlatformType);
	}
	
	/**
	 * Method that stores Cl@ve configuration in the persistence and updates corresponding scheduled timers.
	 * @param claveDto a {@link ClaveDTO} with the information of the platform configuration.
	 * @param platformTypeId with the information of the platform type.
	 * @return {@link PlatformMonitoriza} The cl@ve configuration.
	 */
	@Override
	public PlatformMonitoriza savePlatformClave(ClaveDTO claveDto, Long platformTypeId) {
		PlatformMonitoriza platformClave = null;
		
		if (claveDto.getIdPlatform() != null) {
			platformClave = repository.findByIdPlatform(claveDto.getIdPlatform());
		} else {
			platformClave = new PlatformMonitoriza();
		}

		platformClave.setHost(claveDto.getHost());
		platformClave.setName(claveDto.getName());
		platformClave.setPort(claveDto.getPort());
		platformClave.setIsSecure(claveDto.getIsSecure());
		platformClave.setServiceContext(claveDto.getServiceContext());
		CPlatformType claveType = new CPlatformType();
		claveType.setIdPlatformType(platformTypeId);
		platformClave.setPlatformType(claveType);

		PlatformMonitoriza clave = repository.save(platformClave);
		
		return clave;
	}

}
