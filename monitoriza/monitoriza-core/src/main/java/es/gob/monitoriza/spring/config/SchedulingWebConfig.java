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
 * <b>File:</b><p>es.gob.monitoriza.spring.config.SchedulingWebConfig.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>17/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 20/09/2018.
 */
package es.gob.monitoriza.spring.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.gob.monitoriza.cron.SchedulerObjectInterface;
import es.gob.monitoriza.cron.ValidCertificatesJob;
import es.gob.monitoriza.service.IValidServiceService;

/** 
 * <p>Class that configures the spring scheduled task for checking the status of the timers
 * allowing to to get the fixed rate parameter at running time.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 20/09/2018.
 */
@Configuration
@EnableScheduling
public class SchedulingWebConfig {
	
	/**
	 * Attribute that represents the service object for accessing the valid certificate job. 
	 */
	@Autowired
	private ValidCertificatesJob validCertificatesJob;
	
	/**
	 * Attribute that represents the service object for accessing the valid service repository.
	 */
	@Autowired
	private IValidServiceService validServiceService;
	
	/**
	 * Method that creates a new instance of {@link ScheduledExecutorService}
	 * @return {@link Executor}
	 */
	@Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }private static Map<String, SchedulerObjectInterface> schduledJobsMap = new HashMap<>();
    
    /**
     * Method that puts all jobs to execute into map.
     */
    @PostConstruct
    public void initScheduler() {
        schduledJobsMap.put(ValidCertificatesJob.class.getName(), validCertificatesJob);
        startAll();
    }

    /**
     * Method that starts all available jobs.
     */
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
