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
 * <b>File:</b><p>es.gob.monitoriza.rest.exception.FieldError.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>3 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 3 abr. 2018.
 */
package es.gob.monitoriza.rest.exception;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 3 abr. 2018.
 */
public class FieldError {

	private String field;
    private String code;
    private Object rejectedValue;

    public FieldError(String field, String code, Object rejectedValue) {
        this.field = field;
        this.code = code;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldError that = (FieldError) o;

        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return rejectedValue != null ? rejectedValue.equals(that.rejectedValue) : that.rejectedValue == null;

    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (rejectedValue != null ? rejectedValue.hashCode() : 0);
        return result;
    }
}
