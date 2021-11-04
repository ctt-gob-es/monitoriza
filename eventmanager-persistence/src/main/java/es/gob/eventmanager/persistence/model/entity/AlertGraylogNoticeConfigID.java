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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfigID.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_GRAYLOG_NOTICE_CONFIG</i> database table PK.</p>
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
 * Class that maps the <i>ALERT_GRAYLOG_NOTICE_CONFIG</i> database table PK.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Embeddable
public class AlertGraylogNoticeConfigID implements Serializable {


	private static final long serialVersionUID = 9219193357548930099L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long notSysConfigId;

	/**
	 * Attribute that represents the pkey.
	 */
	private String pkey;


	/**
	 * Gets the value of the attribute {@link #notSysConfigId}.
	 *
	 * @return the value of the attribute {@link #notSysConfigId}.
	 */
	public Long getNotSysConfigId() {
		return this.notSysConfigId;
	}

	/**
	 * Sets the value of the attribute {@link #notSysConfigId}.
	 *
	 * @param notSysConfigId
	 *            The value for the attribute {@link #notSysConfigId}.
	 */
	public void setNotSysConfigId(final Long notSysConfigId) {
		this.notSysConfigId = notSysConfigId;
	}

	/**
	 * Gets the value of the attribute {@link #pkey}.
	 *
	 * @return the value of the attribute {@link #pkey}.
	 */
	public String getPkey() {
		return this.pkey;
	}

	/**
	 * Sets the value of the attribute {@link #pkey}.
	 *
	 * @param pkey
	 *            The value for the attribute {@link #pkey}.
	 */
	public void setPkey(final String pkey) {
		this.pkey = pkey;
	}

	@Override
    public int hashCode() {
        return this.notSysConfigId.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof AlertGraylogNoticeConfigID)) {
            return false;
        }

        final AlertGraylogNoticeConfigID other = (AlertGraylogNoticeConfigID)obj;

        return this.notSysConfigId.equals(other.getNotSysConfigId());
    }

}
