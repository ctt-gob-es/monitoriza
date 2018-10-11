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
 * @version 1.0, 04/10/2018.
 */
package es.gob.monitoriza.response;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents the output of the status servlet y JSON format.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 04/10/2018.
 */
public class DatatableStatus {

	/**
	  * The draw counter that this object is a response to - from the draw parameter sent as part of
	  * the data request. Note that it is strongly recommended for security reasons that you cast this
	  * parameter to an integer, rather than simply echoing back to the client what it sent in the draw
	  * parameter, in order to prevent Cross Site Scripting (XSS) attacks.
	  */
	@JsonView(View.class)
	private int draw;

	/**
	 * Total records, before filtering (i.e. the total number of records in the database)
	 */
	@JsonView(View.class)
	private long recordsTotal = 0L;

	/**
	 * Total records, after filtering (i.e. the total number of records after filtering has been
	 * applied - not just the number of records being returned for this page of data).
	 */
	@JsonView(View.class)
	private long recordsFiltered = 0L;

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
	
	
	public int getDraw() {
		return draw;
	}


	public void setDraw(int draw) {
		this.draw = draw;
	}



	
	public long getRecordsTotal() {
		return recordsTotal;
	}



	
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}



	
	public long getRecordsFiltered() {
		return recordsFiltered;
	}



	
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}



	
	public List<ResponsePOJO> getData() {
		return data;
	}



	
	public void setData(List<ResponsePOJO> data) {
		this.data = data;
	}



	
	public String getError() {
		return error;
	}



	
	public void setError(String error) {
		this.error = error;
	}



	/** 
	 * <p>Interface used by Jackson (JSON) for annotate view fields.</p>
	 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
	 * @version 1.0, 05/10/2018.
	 */
	public interface View {
	}

}
