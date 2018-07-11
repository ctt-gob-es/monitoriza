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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/** 
 * <p>Class that enables and configures Spring Web Security for the Monitoriz@ application.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 6 mar. 2018.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Attribute that represents the injected service for user authentication. 
	 */
	@Autowired
    private MultiFieldAuthenticationProvider multiFieldAuthenticationProvider;
	
	@Autowired
	private SuccessHandler successHandler;
	
	@Autowired
	private FailureHandler failureHandler;
	
	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        
      http
        .authorizeRequests()
        	.antMatchers("/css/**", "/images/**", "/js/**", "/fonts/**", "/fonts/icons/themify/**", "/fonts/fontawesome/**", "/less/**", "/invalidSession")
        	.permitAll() // Enable css, images and js when logged out
        	.and()
        .authorizeRequests()
          	.antMatchers("/", "add", "delete/{id}", "edit/{id}", "save", "users")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(
            		authenticationFilter(),
                UsernamePasswordAuthenticationFilter.class)
        .logout()
            .permitAll()
            .and()
        .httpBasic()
        	.and()
        .csrf()
        	.disable()			//Disable CSRF
        .sessionManagement()
        	.maximumSessions(1)
        	.expiredUrl("/invalidSession")
        	.and()
        	.invalidSessionUrl("/invalidSession");

    }
	
	@Bean
	public MultiFieldAuthenticationFilter authenticationFilter() throws Exception {
		MultiFieldAuthenticationFilter authFilter = new MultiFieldAuthenticationFilter();
	    authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/", "POST"));
	    authFilter.setAuthenticationManager(authenticationManager());
	    authFilter.setAuthenticationSuccessHandler(successHandler);
	    authFilter.setAuthenticationFailureHandler(failureHandler);
	    authFilter.setUsernameParameter("username");
	    authFilter.setPasswordParameter("password");
	    authFilter.setSignatureBase64Parameter("signatureBase64");
	    return authFilter;
	}
	
	/**
     * Method that sets the authentication global configuration.
     * @param auth Object that represents the Spring security builder.
     * @throws Exception Object that represents the exception thrown in case of error.
     */
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(multiFieldAuthenticationProvider);
    }
}
