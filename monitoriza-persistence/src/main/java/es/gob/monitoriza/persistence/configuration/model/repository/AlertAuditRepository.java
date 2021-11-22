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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.repository.AlertAuditRepository.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.gob.monitoriza.persistence.configuration.dto.AuditToStatisticDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAudit;


/**
 * <p>Interface that provides CRUD functionality for the AlertAudit entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
@Repository
public interface AlertAuditRepository extends JpaRepository<AlertAudit, Long> {
	
	@Query(value = "SELECT APP_NAME as appName, ALERT_NAME as typeName, APP_TEMPLATE_NAME as templateName, NODE as nodeName, SEVERITY as severityName, MOMENT as moment, count(*) as occurrences from (select APP_NAME, ALERt_NAME, APP_TEMPLATE_NAME, NODE, SEVERITY, TO_CHAR(TIMESTAMP, 'YYYYMMDD') AS MOMENT from alert_audit where timestamp between :begin and :end) group by APP_NAME, ALERt_NAME, APP_TEMPLATE_NAME, NODE, SEVERITY, MOMENT", nativeQuery = true)
	List<AuditToStatisticDTO> auditToStatisticsDumpData(@Param("begin") final Date begin, @Param("end") final Date end);
}
