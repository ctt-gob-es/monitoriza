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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.UserMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertResumeSystemRepository;
import es.gob.monitoriza.service.IAlertResumeSystemService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("alertResumeSystemService")
public class AlertResumeSystemService implements IAlertResumeSystemService {

	@Autowired
	private AlertResumeSystemRepository repository;

	@Override
	public AlertResumeSystem getAlertResumeSystemId(final Long resumeSystemId) {
		return this.repository.findByIdResSystem(resumeSystemId);
	}

	@Override
	@Transactional
	public void deleteAlertResumeSystem(final Long idResSystem) {
		this.repository.deleteById(idResSystem);
	}

	@Override
	@Transactional
	public void deleteAlertResumeSystemByResumeMonitoriza(final ResumeMonitoriza resumeMonitoriza) {
		this.repository.deleteByResumeMonitoriza(resumeMonitoriza);
	}

	@Override
	public Iterable<AlertResumeSystem> getAllAlertResumeSystems() {
		return this.repository.findAll();
	}

	@Override
	public AlertResumeSystem saveAlertResumeSystem(final AlertResumeSystem alertResumeSystem) {

		AlertResumeSystem newResumeSystem = null;

		if (alertResumeSystem.getIdResSystem() == null) {
			newResumeSystem = new AlertResumeSystem();
		}

		newResumeSystem.setResumeMonitoriza(alertResumeSystem.getResumeMonitoriza());
		newResumeSystem.setAlertSystemMonitoriza(alertResumeSystem.getAlertSystemMonitoriza());

		return this.repository.save(newResumeSystem);
	}


}
