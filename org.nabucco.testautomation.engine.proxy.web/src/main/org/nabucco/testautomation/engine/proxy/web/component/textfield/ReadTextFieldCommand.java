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
package org.nabucco.testautomation.engine.proxy.web.component.textfield;

import org.nabucco.testautomation.engine.base.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * ReadTextFieldCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ReadTextFieldCommand extends AbstractWebComponentCommand {

	/**
	 * @param selenium
	 */
	public ReadTextFieldCommand(Selenium selenium) {
		super(selenium);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		String textFieldId = this.getComponentLocator(metadata);
		
		this.start();
		this.waitForElement(textFieldId);
        String text = this.getSelenium().getValue(textFieldId);
        this.stop();

        StringProperty property = (StringProperty) properties.getPropertyList().get(0).getProperty().cloneObject();
        property.setValue(text);
        PropertyList returnProperties = PropertyHelper.createPropertyList(RETURN_PROPERTIES);
        this.add(property, returnProperties);
        return returnProperties;
	}

}
