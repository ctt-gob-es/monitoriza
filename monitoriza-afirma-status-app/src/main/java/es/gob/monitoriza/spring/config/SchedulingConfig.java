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
 * <b>File:</b><p>es.gob.monitoriza.spring.config.SchedulingConfig.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>17/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 25/01/2019.
 */
package es.gob.monitoriza.spring.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.task.SpieStatisticsTask;
import es.gob.monitoriza.task.TimerScheduledCheckerTask;
import es.gob.monitoriza.task.VipStatisticsTask;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;

/** 
 * <p>Class that configures the spring scheduled task for checking the status of the timers
 * allowing to to get the fixed rate parameter at running time.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 25/01/2019.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {
	
	/**
	 * Method that creates a new instance of {@link ScheduledExecutorService}.
	 * @return {@link Executor}
	 */
	@Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(NumberConstants.NUM100);
    }
	
	/**
	 * Method that creates a new TimerScheduledCheckerTask instance.
	 * @return {@link TimerScheduledCheckerTask} 
	 */
	@Bean
    public TimerScheduledCheckerTask scheduledChecker() {
        return new TimerScheduledCheckerTask();
    }
	
	/**
	 * Method that creates a new TimerScheduledCheckerTask instance.
	 * @return {@link VipStatisticsTask} 
	 */
	@Bean
    public VipStatisticsTask statVipDumper() {
        return new VipStatisticsTask();
    }
	
	/**
	 * Method that creates a new TimerScheduledCheckerTask instance.
	 * @return {@link SpieStatisticsTask} 
	 */
	@Bean
    public SpieStatisticsTask statSpieDumper() {
        return new SpieStatisticsTask();
    }

    /**
     * {@inheritDoc}
     * @see org.springframework.scheduling.annotation.SchedulingConfigurer#configureTasks(org.springframework.scheduling.config.ScheduledTaskRegistrar)
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        
        // Se añade la tarea para comprobar el estado de los timers programados
        taskRegistrar.addTriggerTask(
                new Runnable() {
                    @Override public void run() {
                    	scheduledChecker().checkIfScheduledTimersHaveChanged();
                    }
                },
                new Trigger() {
                    @Override public Date nextExecutionTime(TriggerContext triggerContext) {
                        Calendar nextExecutionTime =  new GregorianCalendar();
                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.MILLISECOND, Integer.parseInt(StaticMonitorizaConfig.getProperty(StaticConstants.FIXED_RATE_MILLISSECONDS)));
                        return nextExecutionTime.getTime();
                    }
                }
        );
                
        
        // Se añade la tarea para volcar los datos de la VIP diarios de monitorización
		taskRegistrar.addTriggerTask(new Runnable() {

			@Override
			public void run() {
				statVipDumper().dumpAndDeleteMonitoringData();
			}
		}, new CronTrigger(StaticMonitorizaConfig.getProperty(StaticConstants.CRON_DUMP_VIP_MONITORING)) );
		
		// Se añade la tarea para volcar los datos diarios SPIE de monitorización
		taskRegistrar.addTriggerTask(new Runnable() {

			@Override
			public void run() {
				statSpieDumper().dumpAndDeleteMonitoringData();
			}
		}, new CronTrigger(StaticMonitorizaConfig.getProperty(StaticConstants.CRON_DUMP_SPIE_MONITORING)) );
    }

}
