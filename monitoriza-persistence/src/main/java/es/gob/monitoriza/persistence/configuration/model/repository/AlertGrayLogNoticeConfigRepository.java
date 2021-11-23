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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.repository.TimerMonitorizaRepository.java.</p>
 * <b>Description:</b><p>Interface that provides CRUD functionality for the TimerMonitoriza entity.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 09/11/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfigID;

/**
 * <p>
 * Interface that provides CRUD functionality for the AlertGraylogNoticeConfig entity.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * @version 1.0, 15/11/2021.
 */

@Repository
public interface AlertGrayLogNoticeConfigRepository extends JpaRepository<AlertGraylogNoticeConfig, AlertGraylogNoticeConfigID> {

	/**
	 * Method that obtains a AlertGraylogNoticeConfig by its id.
	 * @param id Id to find.
	 * @return The AlertGraylogNoticeConfig that corresponds to the id.
	 */
    List<AlertGraylogNoticeConfig> findByNotSysConfigId(Long id);

    /**
     * Method that delete a AlertGraylogNoticeConfig by its id.
     * @param id Id of the AlertGraylogNoticeConfig to delete.
     */
    void deleteByNotSysConfigId(Long id);

    /**
     * Method that find the AlertGraylogNoticeConfig that belongs to a AlertCOnfigSystem.
     * @param alertConfigSystem AlertConfigSystem to find.
     * @return The list of AlertGraylogNoticeConfig of the AlertConfigSystem.
     */
    List<AlertGraylogNoticeConfig> findAllAlertGraylogNoticeConfigByAlertConfigSystem(AlertConfigSystem alertConfigSystem);
}
