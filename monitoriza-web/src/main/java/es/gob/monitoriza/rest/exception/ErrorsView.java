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
 * <b>File:</b><p>es.gob.monitoriza.rest.exception.ErrorsView.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>3 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 3 abr. 2018.
 */
package es.gob.monitoriza.rest.exception;

import java.util.List;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 3 abr. 2018.
 */
public class ErrorsView {
	
    private List<FieldError> fieldErrors;
    private List<GlobalError> globalErrors;

    public ErrorsView(List<FieldError> fieldErrors, List<GlobalError> globalErrors) {
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public List<GlobalError> getGlobalErrors() {
        return globalErrors;
    }

}
