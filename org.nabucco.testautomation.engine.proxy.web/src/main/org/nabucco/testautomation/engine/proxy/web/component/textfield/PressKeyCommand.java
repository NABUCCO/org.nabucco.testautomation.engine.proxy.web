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

import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.facade.datatype.property.BooleanProperty;
import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * PressKeyCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class PressKeyCommand extends AbstractWebComponentCommand {

	/**
	 * @param selenium
	 */
	public PressKeyCommand(Selenium selenium) {
		super(selenium);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		String textFieldId = this.getComponentLocator(metadata);
		String key = EMPTY_STRING;
		boolean ctrl = false;
		boolean shift = false;
		boolean alt = false;
		
		for (PropertyContainer container : properties.getPropertyList()) {
			Property property = container.getProperty();
	    	
			switch (property.getType()) {
	    	
	    	case STRING:
	    		key = ((StringProperty) property).getValue().getValue();
	    		break;
	    		
	    	case INTEGER:
	    		key = "\\" + ((IntegerProperty) property).getValue().getValue();
	    		break;
	    		
	    	case BOOLEAN:
	    		BooleanProperty prop = (BooleanProperty) property;
	    		
	    		if (prop.getValue() != null && prop.getValue().getValue() != null) {
	    		
		    		if (prop.getName().getValue().equals(CTRL)) {
		    			ctrl = prop.getValue().getValue();
		    		} else if (prop.getName().getValue().equals(SHIFT)) {
		    			shift = prop.getValue().getValue();
		    		} else if (prop.getName().getValue().equals(ALT)) {
		    			alt = prop.getValue().getValue();
		    		}
	    		}
	    		break;
	    	}
		}
    	
    	this.start();
    	this.waitForElement(textFieldId);
    	
    	if (ctrl) {
    		this.getSelenium().controlKeyDown();
    	}
    	
    	if (shift) {
    		this.getSelenium().shiftKeyDown();
    	}
    	
    	if (alt) {
    		this.getSelenium().altKeyDown();
    	}

    	this.getSelenium().keyPress(textFieldId, key);
		
    	if (ctrl) {
    		this.getSelenium().controlKeyUp();
    	}
    	
    	if (shift) {
    		this.getSelenium().shiftKeyUp();
    	}
    	
    	if (alt) {
    		this.getSelenium().altKeyUp();
    	}
    	
        this.stop();
        return null;
	}

}
