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
 * <b>File:</b><p>es.gob.monitoriza.controller.StatsVipController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20/11/2018.
 */
package es.gob.monitoriza.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20/11/2018.
 */
@Controller
public class StatsVipController {
	
	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of users to the view.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @param request Request object
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "statsvip", method = RequestMethod.GET)
	public String status(final Model model, final HttpServletRequest request) {
		
		return "fragments/statsvip.html";
	}

}
