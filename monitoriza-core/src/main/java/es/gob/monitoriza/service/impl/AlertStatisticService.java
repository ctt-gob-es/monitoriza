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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApp;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistic;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertStatisticRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlertStatisticDatatableRepository;
import es.gob.monitoriza.service.IAlertStatisticService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("alertStatisticService")
public class AlertStatisticService implements IAlertStatisticService {

	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	@Autowired
	private AlertStatisticRepository repository;

	@Autowired
	private AlertStatisticDatatableRepository dtRepository;

	@Autowired
	private Environment env;

	@Override
	public Iterable<AlertStatistic> getAllAlertStatistic() {
		return this.repository.findAll();
	}

	@Override
	public DataTablesOutput<AlertStatistic> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public List<AlertStatistic> findByFilters(final Date minDate, final Date maxDate, final AlertDIMApp appID, final AlertDIMTemplate templateID,
			final AlertDIMType typeID, final AlertDIMNode nodeID, final AlertDIMSeverity severityID){

		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;

		final List<AlertStatistic> statisticsList = new ArrayList<AlertStatistic>();

		try {
			final String url = this.env.getProperty("spring.datasource.url");
			connection = DriverManager.getConnection(url, this.env.getProperty("spring.datasource.username"), this.env.getProperty("spring.datasource.password"));
			String query = "SELECT APP_ID, APP_NAME, TYPE_ID, TYPE_NAME, TEMPLATE_ID, TEMPLATE_NAME, NODE, NODE_NAME, SEVERITY, SEVERITY_NAME, SUM(OCURRENCY) FROM "
					+ "(SELECT APPS.APP_ID, APPS.APP_NAME, TYPES.TYPE_ID, TYPES.TYPE_NAME, TEMPLATES.TEMPLATE_ID, TEMPLATES.TEMPLATE_NAME, STATS.NODE, NODES.NODE_NAME, STATS.SEVERITY, SEVERITIES.SEVERITY_NAME, STATS.OCURRENCY FROM ALERT_STATISTICS STATS "
					+ "INNER JOIN ALERT_DIM_APPS APPS ON STATS.APP_ID = APPS.APP_ID "
					+ "INNER JOIN ALERT_DIM_TYPES TYPES ON STATS.TYPE_ID = TYPES.TYPE_ID "
					+ "INNER JOIN ALERT_DIM_TEMPLATES TEMPLATES ON STATS.TEMPLATE_ID = TEMPLATES.TEMPLATE_ID "
					+ "INNER JOIN ALERT_DIM_NODES NODES ON STATS.NODE = NODES.NODE_ID "
					+ "INNER JOIN ALERT_DIM_SEVERITY SEVERITIES ON STATS.SEVERITY = SEVERITIES.SEVERITY_ID "
					+ "WHERE 1=1 ";
			if (minDate != null && maxDate != null) {
				final SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyyMMdd");
				query += " AND STATS.TIMESTAMP >= " + formateadorFecha.format(minDate) + "AND STATS.TIMESTAMP <= " + formateadorFecha.format(maxDate);
			}
			if (appID != null) {
				query += " AND STATS.APP_ID = " + appID.getAppID();
			}
			if (templateID != null) {
				query += " AND STATS.TEMPLATE_ID = " + templateID.getIdTemplate();
			}
			if (typeID != null) {
				query += " AND STATS.TYPE_ID = " + typeID.getIdType();
			}
			if (nodeID != null) {
				query += " AND STATS.NODE_ID = " + nodeID.getIdNode();
			}
			if (severityID != null) {
				query += " AND STATS.SEVERITY_ID = " + severityID.getIdSeverity();
			}

			query += ") GROUP BY APP_ID, APP_NAME, TYPE_ID, TYPE_NAME, TEMPLATE_ID, TEMPLATE_NAME, NODE, NODE_NAME, SEVERITY, SEVERITY_NAME";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);

	       while(rs.next()){
	    	   final AlertStatistic statistic = new AlertStatistic();
	    	   statistic.setIdAlertStatistic(new Long(0));

	    	   final AlertDIMApp app = new AlertDIMApp();
	    	   app.setAppID(new Long(rs.getInt(1)));
	    	   app.setName(rs.getString(2));
	    	   statistic.setAlertDIMApp(app);

	    	   final AlertDIMType type = new AlertDIMType();
	    	   type.setIdType(new Long(rs.getInt(3)));
	    	   type.setName(rs.getString(4));
	    	   statistic.setAlertDIMType(type);

	    	   final AlertDIMTemplate template = new AlertDIMTemplate();
	    	   template.setIdTemplate(new Long(rs.getInt(5)));
	    	   template.setName(rs.getString(6));
	    	   statistic.setAlertDIMTemplate(template);

	    	   final AlertDIMNode node = new AlertDIMNode();
	    	   node.setIdNode(new Long(rs.getInt(7)));
	    	   node.setName(rs.getString(8));
	    	   statistic.setAlertDIMNode(node);

	    	   final AlertDIMSeverity severity = new AlertDIMSeverity();
	    	   severity.setIdSeverity(new Long(rs.getInt(9)));
	    	   severity.setName(rs.getString(10));

	    	   statistic.setAlertDIMSeverity(severity);
	    	   statistic.setOcurrency(new Long(rs.getInt(11)));

	    	   statisticsList.add(statistic);
           }
		} catch ( final SQLException ex ) {
			LOGGER.error("Error al recuperar estadisticas :" + ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				LOGGER.error("Error al cerrar la conexion para recuperar estadisticas :" + e);
			}

		}

		return statisticsList;
	 }
}
