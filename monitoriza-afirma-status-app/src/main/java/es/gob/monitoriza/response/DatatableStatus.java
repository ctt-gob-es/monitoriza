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
 * <b>File:</b><p>es.gob.monitoriza.response.ResponseJsonPOJO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/10/2018.
 */
package es.gob.monitoriza.response;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents the output of the status servlet y JSON format.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/10/2018.
 */
public class DatatableStatus {
	
	/**
	 * The data to be displayed in the table. This is an array of data source objects, one for each
	 * row, which will be used by DataTables. Note that this parameter's name can be changed using the
	 * ajaxDT option's dataSrc property.
	 */
	@JsonView(View.class)
	private List<ResponsePOJO> data = Collections.emptyList();

	/**
	 * Optional: If an error occurs during the running of the server-side processing script, you can
	 * inform the user of this error by passing back the error message to be displayed using this
	 * parameter. Do not include if there is no error.
	 */
	@JsonView(View.class)
	private String error;
	
	/**
	 * Gets the value of the attribute {@link #data}.
	 * @return the value of the attribute {@link #data}.
	 */
	public List<ResponsePOJO> getData() {
		return data;
	}
	
	/**
	 * Sets the value of the attribute {@link #data}.
	 * @param dataParam the value for the attribute {@link #data} to set.
	 */
	public void setData(final List<ResponsePOJO> dataParam) {
		this.data = dataParam;
	}

	/**
	 * Gets the value of the attribute {@link #error}.
	 * @return the value of the attribute {@link #error}.
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the value of the attribute {@link #error}.
	 * @param errorParam the value for the attribute {@link #error} to set.
	 */
	public void setError(final String errorParam) {
		this.error = errorParam;
	}



	/** 
	 * <p>Interface used by Jackson (JSON) for annotate view fields.</p>
	 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
	 * @version 1.0, 05/10/2018.
	 */
	public interface View {
	}

}
