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
 * <b>File:</b><p>es.gob.monitoriza.spring.config.ApplicationConfiguration.java.</p>
 * <b>Description:</b><p>Spring configuration class that sets the configuration of Spring components, entities and repositories.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>7 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 7 mar. 2018.
 */
package es.gob.monitoriza.spring.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.gob.monitoriza.cron.SchedulerObjectInterface;
import es.gob.monitoriza.cron.ValidCertificatesJob;
import es.gob.monitoriza.service.IValidServiceService;

/** 
 * <p>Spring configuration class that sets the configuration of Spring components, entities and repositories.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 7 mar. 2018.
 */
@Configuration
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("es.gob.monitoriza")
@EntityScan("es.gob.monitoriza.persistence.configuration.model.entity")
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, basePackages = "es.gob.monitoriza.persistence.configuration.model.repository")
public class ApplicationConfig {
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ValidCertificatesJob validCertificatesJob;
	
	/**
	 * Attribute that represents the service object for accessing the
	 * valid service repository.
	 */
	@Autowired
	private IValidServiceService validServiceService;
	
	private static Map<String, SchedulerObjectInterface> schduledJobsMap = new HashMap<>();
    
    @PostConstruct
    public void initScheduler() {
        schduledJobsMap.put(ValidCertificatesJob.class.getName(), validCertificatesJob);
        startAll();
    }

    private void startAll() {
        for (SchedulerObjectInterface schedulerObjectInterface : schduledJobsMap.values()) {
            schedulerObjectInterface.start();
        }
    }
	
	/**
	 * Get validCertificatesJob.
	 * @return validCertificatesJob
	 */
	public ValidCertificatesJob getValidCertificatesJob() {
		return validCertificatesJob;
	}

	
	/**
	 * Set validCertificatesJob.
	 * @param validCertificatesJob set validCertificatesJob
	 */
	public void setValidCertificatesJob(ValidCertificatesJob validCertificatesJob) {
		this.validCertificatesJob = validCertificatesJob;
	}
	
	/**
	 * Get validServiceService.
	 * @return validServiceService
	 */
	public IValidServiceService getValidServiceService() {
		return validServiceService;
	}

	/**
	 * Set validServiceService.
	 * @param validServiceService set validServiceService
	 */
	public void setValidServiceService(IValidServiceService validServiceService) {
		this.validServiceService = validServiceService;
	}
	
}
