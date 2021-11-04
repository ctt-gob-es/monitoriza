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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.repository.AlertGraylogNoticeConfigRepository.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfigID;


/**
 * <p>Interface that provides CRUD functionality for the AlertGraylogNoticeConfigRepository entity.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Repository
public interface AlertGraylogNoticeConfigRepository extends JpaRepository<AlertGraylogNoticeConfig, AlertGraylogNoticeConfigID> {

	@Query("SELECT AG FROM AlertGraylogNoticeConfig AG, AlertConfigMonitoriza AC, AlertConfigSystem ACS"
			+ " WHERE ACS.alertConfigMonitoriza.idAlertConfigMonitoriza = AC.idAlertConfigMonitoriza"
			+ " AND ACS.idNotSysConfig = AG.idNotSysConfig"
			+ " AND AC.idAlertConfigMonitoriza =: idAlertConfig")
	List<AlertGraylogNoticeConfig> findByAlertConfig(@Param("idAlertConfig") final Long idAlertConfig);
	
	/**
	 * 
	 * @param idNotSysConfig
	 */
	List<AlertGraylogNoticeConfig> findByIdNotSysConfig(final Long idNotSysConfig);
}
