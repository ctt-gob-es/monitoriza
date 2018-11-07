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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.MailDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>
 * Class Class that represents the transfer object and backing form for e-mail configuration.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 28/10/2018.
 */
public class MailDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input
	 * in the form.
	 */
	private Long idMail;

	/**
	 * Attribute that represents a e-mail address. 
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.mail.emailAddress.notempty}")
	@Size(min = NumberConstants.NUM3, max = NumberConstants.NUM254, groups = ThenCheckIt.class)
	private String emailAddress;

	/**
	 * @return the idMail
	 */
	public Long getIdMail() {
		return idMail;
	}

	/**
	 * @param idMail the idMail to set
	 */
	public void setIdMail(Long idMail) {
		this.idMail = idMail;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
