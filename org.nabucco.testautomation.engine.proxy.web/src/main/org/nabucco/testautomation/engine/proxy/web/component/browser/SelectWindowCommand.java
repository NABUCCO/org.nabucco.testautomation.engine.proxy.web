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
package org.nabucco.testautomation.engine.proxy.web.component.browser;

import org.nabucco.testautomation.engine.base.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * SelectWindowCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SelectWindowCommand extends AbstractWebComponentCommand {

	/**
	 * @param selenium
	 */
	public SelectWindowCommand(Selenium selenium) {
		super(selenium);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		String windowId = getWindowId(metadata, properties);
		
		this.start();
		this.getSelenium().selectWindow(windowId);
		this.stop();
		return null;
	}
	
	private String getWindowId(Metadata metadata, PropertyList properties) {
		
		// First, check PropertyList from Action
		Property idProperty = PropertyHelper.getFromList(properties,
				WINDOW_ID);

		if (idProperty != null && idProperty.getType() == PropertyType.STRING) {
			return ((StringProperty) idProperty).getValue().getValue();
		}
		
		// Second, check PropertyList from Metadata
		idProperty = PropertyHelper.getFromList(metadata.getPropertyList(),
				WINDOW_ID);
		
		if (idProperty != null && idProperty.getType() == PropertyType.STRING) {
			return ((StringProperty) idProperty).getValue().getValue();
		}
		
		return EMPTY_STRING;		
	}

}
