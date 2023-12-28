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
 * <b>File:</b><p>es.gob.monitoriza.spring.config.MonitorizaConfig.java.</p>
 * <b>Description:</b><p>Spring configuration class that sets the configuration of Spring components, entities and repositories.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * <b>Date:</b><p>27/12/2023.</p>
 * @author Gobierno de España.
 * @version 1.0, 27/12/2023.
 */
package es.gob.monitoriza.spring.config;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * <p>Spring configuration class that sets the configuration of Spring components, entities and repositories.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 27/12/2023.
 */
@Configuration
@PropertySource(value = "${multi.bbdd.configuration}", ignoreResourceNotFound = true)
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, entityManagerFactoryRef = "monitorizaEntityManager", transactionManagerRef = "monitorizaTransactionManager", basePackages = {
		"es.gob.monitoriza.persistence.configuration.model.repository" })
public class MonitorizaConfig {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LogManager.getLogger(MonitorizaConfig.class);
	
	/**
	 * Attribute that represents the Monitoriza JNDI name.
	 */
	@Value("${spring.datasource.jndi-name}")
	private String monitorizaJndiDataSource;

	/**
	 * Attribute that represents the dialect.
	 */
	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String monitorizaDialect;

	/**
	 * Attribute that represents the DDL auto.
	 */
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	/**
	 * Bean of spring with transaction manager.
	 * 
	 * @return transaction manager object.
	 */
	@Bean
	public PlatformTransactionManager monitorizaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(monitorizaEntityManager().getObject());
		return transactionManager;
	}

	/**
	 * 
	 * Bean of spring with local container entity manager factory.
	 * 
	 * @return local container entity manager factory bean.
	 */
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean monitorizaEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setDataSource(this.monitorizaDataSource());
		em.setPackagesToScan(new String[] { "es.gob.monitoriza.persistence.configuration.model.entity" });
		em.setJpaVendorAdapter(vendorAdapter);

		Map<String, String> monitorizaJpaProperties = new HashMap<String, String>();
		monitorizaJpaProperties.put("hibernate.dialect", monitorizaDialect);
		monitorizaJpaProperties.put("hibernate.hbm2ddl.auto", ddlAuto);

		em.setJpaPropertyMap(monitorizaJpaProperties);

		return em;
	}

	/**
	 * Bean of spring with data source.
	 * 
	 * @return data source.
	 */
	@Bean
	public DataSource monitorizaDataSource() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(monitorizaJndiDataSource);
		bean.setProxyInterface(DataSource.class);
		try {
			bean.afterPropertiesSet();
		} catch (IllegalArgumentException | NamingException e) {
			LOGGER.error(e);
		}
		return (DataSource) bean.getObject();
	}

}
