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
 * <b>File:</b><p>es.gob.monitoriza.config.WebSecurityConfig.java.</p>
 * <b>Description:</b><p>Class that enables and configures Spring Web Security for the Monitoriz@ application.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>6 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 6 mar. 2018.
 */
package es.gob.monitoriza.spring.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import es.gob.monitoriza.constant.GeneralConstants;

/**
 * <p>Class that enables and configures Spring Web Security for the Monitoriz@ application.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 6 mar. 2018.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the injected service for user authentication.
	 */
	@Autowired
	private MultiFieldAuthenticationProvider multiFieldAuthPr;

	/**
	 * Attribute that represents the injected service for success user authentication.
	 */
	@Autowired
	private SuccessHandler successHandler;

	/**
	 * Attribute that represents the injected service for failure user authentication.
	 */
	@Autowired
	private FailureHandler failureHandler;

	/**
	 * {@inheritDoc}
	 * @throws
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(final HttpSecurity http) {

		try {
			http.authorizeRequests().antMatchers("/css/**", "/images/**", "/js/**", "/fonts/**", "/fonts/icons/themify/**", "/fonts/fontawesome/**", "/less/**", "/invalidSession").permitAll() // Enable
																																																// css,
																																																// images
																																																// and
																																																// js
																																																// when
																																																// logged
																																																// out
				.and().authorizeRequests().antMatchers("/", "add", "delete/{id}", "edit/{id}", "save", "users").permitAll().anyRequest().authenticated().and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class).logout().permitAll().and().httpBasic().and().csrf().disable() // Disable
																																																																												 // CSRF
				.sessionManagement().maximumSessions(1).expiredUrl("/invalidSession").and().invalidSessionUrl("/invalidSession");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	/**
	 * Filter authentication.
	 * @return MultiFieldAuthenticationFilter filter
	 * @throws Exception
	 */
	@Bean
	public MultiFieldAuthenticationFilter authenticationFilter() {
		MultiFieldAuthenticationFilter authFilter = new MultiFieldAuthenticationFilter();
		authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/", "POST"));
		try {
			authFilter.setAuthenticationManager(authenticationManager());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		authFilter.setAuthenticationSuccessHandler(successHandler);
		authFilter.setAuthenticationFailureHandler(failureHandler);
		authFilter.setUsernameParameter("username");
		authFilter.setPasswordParameter("password");
		authFilter.setSigBase64Param("signatureBase64");
		return authFilter;
	}

	/**
	 * Method that sets the authentication global configuration.
	 * @param auth Object that represents the Spring security builder.
	 * @throws Exception Object that represents the exception thrown in case of error.
	 */
	@Override
	public void configure(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(multiFieldAuthPr);
	}

	/**
	 * Get multiFieldAuthPr.
	 * @return multiFieldAuthPr
	 */
	public MultiFieldAuthenticationProvider getMultiFieldAuthPr() {
		return multiFieldAuthPr;
	}

	/**
	 * Set multiFieldAuthPr.
	 * @param multiFieldAuthPrP set multiFieldAuthPr
	 */
	public void setMultiFieldAuthPr(final MultiFieldAuthenticationProvider multiFieldAuthPrP) {
		this.multiFieldAuthPr = multiFieldAuthPrP;
	}

	/**
	 * Get successHandler.
	 * @return successHandler
	 */
	public SuccessHandler getSuccessHandler() {
		return successHandler;
	}

	/**
	 * Set successHandler.
	 * @param successHandlerP set successHandler
	 */
	public void setSuccessHandler(final SuccessHandler successHandlerP) {
		this.successHandler = successHandlerP;
	}

	/**
	 * Get failureHandler.
	 * @return failureHandler
	 */
	public FailureHandler getFailureHandler() {
		return failureHandler;
	}

	/**
	 * Set failureHandler.
	 * @param failureHandlerP set failureHandler
	 */
	public void setFailureHandler(final FailureHandler failureHandlerP) {
		this.failureHandler = failureHandlerP;
	}
}
