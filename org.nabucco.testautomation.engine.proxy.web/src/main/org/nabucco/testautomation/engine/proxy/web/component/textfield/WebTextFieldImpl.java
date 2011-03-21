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

import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.web.WebActionType;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponent;
import org.nabucco.testautomation.engine.proxy.web.component.WebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyComponent;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.result.facade.datatype.ActionResponse;
import org.nabucco.testautomation.result.facade.datatype.status.ActionStatusType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * Implementation of selenium actions for a HTML input field
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class WebTextFieldImpl extends AbstractWebComponent {

	private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link WebTextFieldImpl} instance.
     * 
     * @param selenium
     *            the selenium instance.
     */
	public WebTextFieldImpl(Selenium selenium) {
        super(selenium);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    protected ActionResponse internalExecute(TestContext context, PropertyList propertyList,
            Metadata metadata, WebActionType actionType) {

    	ActionResponse result = TestResultHelper.createActionResponse();
        WebComponentCommand command = null;

        try {
	        switch (actionType) {
	
	        case ENTER:
	            command = new EnterTextCommand(this.getSelenium());
	            break;
	
	        case READ:
	        	command = new ReadTextFieldCommand(this.getSelenium());
	            break;
	            
	        case CLEAR:
	        	command = new ClearTextFieldCommand(this.getSelenium());
	            break;
	        	
	        case PRESS_KEY:
	        	command = new PressKeyCommand(this.getSelenium());
	            break;
	
	        default:
	            return failResult(metadata, actionType, "Unsupported WebActionType for WebTextField: '" + actionType + "'");
	        }
	        
	        // Execute WebCommand
			PropertyList returnProperties = command.execute(metadata, propertyList);
			
			result.setMessage("Executed WebTextField action='" + actionType + "'");
			result.setReturnProperties(returnProperties);
			result.setActionStatus(ActionStatusType.EXECUTED);
			return result;
        } catch (WebComponentException ex) {
        	String errorMessage = "Could not execute " + actionType
			+ " on WebTextField '" + metadata.getName().getValue()
			+ "'. Cause: " + ex.getMessage();
            this.error(errorMessage);
			result.setErrorMessage(errorMessage);
            result.setActionStatus(ActionStatusType.FAILED);
            return result;
        } catch (Exception ex) {
        	this.fatal(ex);
            result.setErrorMessage("Could not execute " + actionType
					+ " on WebTextField '" + metadata.getName().getValue()
					+ "'. Cause: " + ex.toString());
            result.setActionStatus(ActionStatusType.FAILED);
            return result;
        } finally {
        	
        	if (context.isTracingEnabled() && command != null) {
        		result.setActionTrace(command.getActionTrace());
        	}
        }
    }

	@Override
	protected void validateProperties(PropertyList propertyList, WebActionType actionType) {
		
		switch (actionType) {
		case ENTER:
			if (propertyList.getPropertyList().size() < 1
					|| !(propertyList.getPropertyList().get(0).getProperty() instanceof PropertyComponent)) {
				throw new IllegalArgumentException("Enter requires 1 Property of Type PropertyComponent");
			}
			break;
		case READ:
			if (propertyList.getPropertyList().size() < 1
					|| propertyList.getPropertyList().get(0).getProperty().getType() != PropertyType.STRING) {
				throw new IllegalArgumentException("Read requires 1 Property of Type String");
			}
			break;
		case CLEAR:
			break;
		case PRESS_KEY:
			if (propertyList.getPropertyList().size() < 1) {
				throw new IllegalArgumentException("PressKey requires at least 1 Property of Type String or Integer");
			}
			break;
		}
	}

}
