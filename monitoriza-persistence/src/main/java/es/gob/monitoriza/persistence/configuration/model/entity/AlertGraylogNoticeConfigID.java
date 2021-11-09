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
	@JsonView(DataTablesOutput.View.class)
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
