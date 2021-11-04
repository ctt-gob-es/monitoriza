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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertMailNoticeConfigID.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_MAIL_NOTICE_CONFIG</i> database table PK.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * <p>
 * Class that maps the <i>ALERT_MAIL_NOTICE_CONFIG</i> database table PK.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
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
