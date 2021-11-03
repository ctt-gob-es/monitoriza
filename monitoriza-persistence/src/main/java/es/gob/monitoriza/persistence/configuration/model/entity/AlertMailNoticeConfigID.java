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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALARM_MONITORIZA</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that maps the ID of <i>ALERT_MAIL_NOTICE_CONFIG</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Embeddable
public class AlertMailNoticeConfigID implements Serializable {


	private static final long serialVersionUID = 9219193357548930099L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNotSysConfig;

	/**
	 * Attribute that represents the mail.
	 */
	private String mail;


	/**
	 * Gets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @return the value of the attribute {@link #idNotSysConfig}.
	 */
	public Long getIdNotSysConfig() {
		return this.idNotSysConfig;
	}

	/**
	 * Sets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @param idNotSysConfig
	 *            The value for the attribute {@link #idNotSysConfig}.
	 */
	public void setIdNotSysConfig(final Long idNotSysConfig) {
		this.idNotSysConfig = idNotSysConfig;
	}

	/**
	 * Gets the value of the attribute {@link #mail}.
	 *
	 * @return the value of the attribute {@link #mail}.
	 */
	@JsonView(DataTablesOutput.View.class)
	public String getMail() {
		return this.mail;
	}

	/**
	 * Sets the value of the attribute {@link #mail}.
	 *
	 * @param mail
	 *            The value for the attribute {@link #mail}.
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}

	@Override
    public int hashCode() {
        return this.idNotSysConfig.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof AlertMailNoticeConfigID)) {
            return false;
        }

        final AlertMailNoticeConfigID other = (AlertMailNoticeConfigID)obj;

        return this.idNotSysConfig.equals(other.getIdNotSysConfig());
    }

}
