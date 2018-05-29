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
 * <b>File:</b><p>es.gob.monitoriza.form.SslForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>17 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 17 may. 2018.
 */
package es.gob.monitoriza.form;

import org.springframework.web.multipart.MultipartFile;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 17 may. 2018.
 */
public class SslForm {

	private Long idSystemCertificate;
	
	private String alias;
	
	private MultipartFile file;
	
	private byte[] certBytes;

	
	public Long getIdSystemCertificate() {
		return idSystemCertificate;
	}
	
	public void setIdSystemCertificate(Long idSystemCertificate) {
		this.idSystemCertificate = idSystemCertificate;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}


	public MultipartFile getFile() {
		return file;
	}

	
	public void setFile(MultipartFile sslCertificate) {
		this.file = sslCertificate;
	}

	
	public byte[ ] getCertBytes() {
		return certBytes;
	}

	
	public void setCertBytes(byte[ ] certBytes) {
		this.certBytes = certBytes;
	}
		
}
