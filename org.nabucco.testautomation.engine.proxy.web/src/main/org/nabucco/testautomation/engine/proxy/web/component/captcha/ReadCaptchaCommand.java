/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.engine.proxy.web.component.captcha;

import java.io.IOException;
import java.net.URL;

import org.nabucco.testautomation.engine.base.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.captcha.Captcha;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * ReadCaptchaCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ReadCaptchaCommand extends AbstractWebComponentCommand {

	/**
	 * @param selenium
	 */
	public ReadCaptchaCommand(Selenium selenium) {
		super(selenium);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		String captchaId = this.getComponentLocator(metadata);
		StringProperty property = (StringProperty) properties.getPropertyList().get(0).getProperty().cloneObject();
		
		try {
            StringBuilder location = new StringBuilder();

            this.start();
            this.waitForElement(captchaId);
            String root = this.getSelenium().getLocation();
            String attribute = this.getSelenium().getAttribute(captchaId);

            location.append(root.substring(0, root.indexOf(attribute.substring(0, 15))));
            location.append(attribute);

            URL url = new URL(location.toString());
            Captcha captcha = new Captcha(url);
            
            String code = captcha.readSequence();
            this.stop();
            property.setValue(code);
            PropertyList returnProperties = PropertyHelper.createPropertyList(RETURN_PROPERTIES);
            this.add(property, returnProperties);
            return returnProperties;
        } catch (IOException ex) {
        	this.stop();
        	this.setException(ex);
            throw new WebComponentException("Error during captcha identification: " + ex.getMessage());
        }
	}

}
