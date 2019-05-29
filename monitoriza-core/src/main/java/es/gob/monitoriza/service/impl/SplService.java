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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.PlatformAfirmaService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for PlatformAfirma.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.dto.SplDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SplMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.SplRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.SplDatatableRepository;
import es.gob.monitoriza.service.ISplService;


/**
 * <p>Class that implements the communication with the operations of the persistence layer for SplMonitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 15/03/2019.
 */
@Service("splService")
public class SplService implements ISplService {

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence
	 * of the SPLs.
	 */
	@Autowired
    private SplRepository repository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence
	 * of the SPL DataTables.
	 */
	@Autowired
    private SplDatatableRepository dtRepository;

	@Override
	public SplMonitoriza getSplById(final Long splId) {
		return this.repository.findByIdSpl(splId);
	}

	@Override
	public void deleteSplById(final Long splId) {
		this.repository.deleteById(splId);
	}

	@Override
	public void deletePlatform(final SplMonitoriza spl) {
		this.repository.delete(spl);
	}

	@Override
	public Iterable<SplMonitoriza> getAllSpl() {
		return this.repository.findAll();
	}

	@Override
	public SplMonitoriza saveSpl(final SplDTO splDto) {

		SplMonitoriza spl;
		if (splDto.getIdSpl() != null) {
			spl = this.repository.findByIdSpl(splDto.getIdSpl());
		} else {
			spl = new SplMonitoriza();
		}

		spl.setName(splDto.getName());
		spl.setDescription(splDto.getDescription());
		spl.setType(splDto.getType());
		spl.setUrl(splDto.getUrl());
		spl.setKey(splDto.getKey());
	

		return this.repository.save(spl);
	}

	@Override
	public DataTablesOutput<SplMonitoriza> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}
}
