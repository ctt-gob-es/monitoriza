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
 * <b>File:</b><p>es.gob.monitoriza.rest.exception.MonitorizaResponseEntityExceptionHandler.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>3 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 3 abr. 2018.
 */
package es.gob.monitoriza.rest.exception;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 3 abr. 2018.
 */
@ControllerAdvice
@RestController
public class ValidationExceptionHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		List<FieldError> fieldErrors = bindingResult.getFieldErrors().stream().map(fieldError -> new FieldError(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage())).collect(toList());

		List<GlobalError> globalErrors = bindingResult.getGlobalErrors().stream().map(globalError -> new GlobalError(globalError.getCode())).collect(toList());

		ErrorsView errorsView = new ErrorsView(fieldErrors, globalErrors);

		return new ResponseEntity<>(errorsView, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
