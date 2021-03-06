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
 * <b>File:</b><p>es.gob.valet.rest.elements.json.DateString.java.</p>
 * <b>Description:</b><p>Class that represents an element transformation between a Date
 * and a string in a specific format: {@value UtilsDate#FORMAT_DATE_TIME_JSON}.</p>
 * <b>Project:</b><p>Platform for detection and validation of certificates recognized in European TSL.</p>
 * <b>Date:</b><p>13/08/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 13/08/2019.
 */
package es.gob.monitoriza.rest.elements.json;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import es.gob.monitoriza.utilidades.UtilsFecha;

/**
 * <p>Class that represents an element transformation between a Date
 * and a string in a specific format: {@value UtilsDate#FORMAT_DATE_TIME_JSON}.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 13/08/2019.
 */
public class DateString implements Serializable {

	/**
	 * Constant attribute that represents the serial version UID.
	 */
	private static final long serialVersionUID = -9125697047406824122L;

	/**
	 * Attribute that represents the date.
	 */
	private Date date = null;

	/**
	 * Attribute that represents the date in string format.
	 */
	private String dateString = null;

	/**
	 * Constructor method for the class DateString.java.
	 */
	private DateString() {
		super();
	}

	/**
	 * Constructor method for the class DateString.java.
	 * @param dateParam Date to set.
	 */
	public DateString(Date dateParam) {
		this();
		this.date = dateParam;
	}

	/**
	 * Constructor method for the class DateString.java.
	 * @param dateStringParam Date to set in string format.
	 */
	public DateString(String dateStringParam) {
		this();
		this.dateString = dateStringParam;
	}

	/**
	 * Gets the value of the attribute {@link #date}.
	 * @return the value of the attribute {@link #date}.
	 * @throws ParseException In case of some error parsing the input date string.
	 */
	public final Date getDate() throws ParseException {
		if (date == null && dateString != null) {
			date = UtilsFecha.transformDate(dateString, UtilsFecha.FORMAT_DATE_TIME_JSON);
		}
		return date;
	}

	/**
	 * Gets the value of the attribute {@link #dateString}.
	 * @return the value of the attribute {@link #dateString}.
	 */
	public final String getDateString() {
		if (dateString == null && date != null) {
			dateString = UtilsFecha.toString(UtilsFecha.FORMAT_DATE_TIME_JSON, date);
		}
		return dateString;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getDateString();
	}
	
}
