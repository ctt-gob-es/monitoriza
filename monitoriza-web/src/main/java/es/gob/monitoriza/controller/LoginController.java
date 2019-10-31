/* 
// Copyright (C) 2018, Gobierno de España
// This program is licensed and may be used, modified and redistributed under the terms
// of the European Public License (EUPL), either version 1.1 or (at your
// option) any later version as soon as they are approved by the European Commission.
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// or implied. See the License for the specific language governing permissions and
// more details.
// You should have received a copy of the EUPL1.1 license
// along with this program; if not, you may find it at
// http://joinup.ec.europa.eu/software/page/eupl/licence-eupl

/** 
 * <b>File:</b><p>es.gob.monitoriza.controller.UserController.java.</p>
 * <b>Description:</b><p>Class that maps the request for the login form to the controller.</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 6 mar. 2018.
 */
package es.gob.monitoriza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 
 * <p>Class that maps the request for the login form to the controller.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 6 mar. 2018.
 */
@Controller
public class LoginController {
	
	/**
	 * Method that maps the root request for the application to the controller to the login view.  
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login.html";
    }

}
