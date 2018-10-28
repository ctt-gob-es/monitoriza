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
 * <b>File:</b><p>es.gob.monitoriza.vo.PickListVO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 jun. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/10/2018.
 */
package es.gob.monitoriza.vo;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents a picklist view component.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 28/10/2018.
 */
public class PickListVO {
	
	/**
	 * Attribute that represents the list of elements. 
	 */
	private List<PickListElementVO> lista = Collections.emptyList();
	
	/**
	 * Attribute that represents an error text in the picklist component. 
	 */
	private String error;

	/**
	 * Gets the value of the attribute {@link #lista}.
	 * @return the value of the attribute {@link #lista}.
	 */
	@JsonView(View.class)
	public List<PickListElementVO> getLista() {
		return lista;
	}

	/**
	 * Sets the value of the attribute {@link #lista}.
	 * @param listaParam The value for the attribute {@link #lista}.
	 */
	public void setLista(List<PickListElementVO> listaParam) {
		this.lista = listaParam;
	}

	/**
	 * Gets the value of the attribute {@link #error}.
	 * @return the value of the attribute {@link #error}.
	 */
	@JsonView(View.class)
	public String getError() {
		return error;
	}

	/**
	 * Sets the value of the attribute {@link #error}.
	 * @param errorParam The value for the attribute {@link #error}.
	 */
	public void setError(String errorParam) {
		this.error = errorParam;
	}
		
	/** 
	 * <p>Interface used in the JsonView annotation.</p>
	 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
	 * @version 1.0, 25/10/2018.
	 */
	public interface View {
	}
}
