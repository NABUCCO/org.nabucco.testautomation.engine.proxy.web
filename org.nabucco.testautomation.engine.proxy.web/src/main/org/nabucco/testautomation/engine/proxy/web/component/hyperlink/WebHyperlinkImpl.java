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
package org.nabucco.testautomation.engine.proxy.web.component.hyperlink;

import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.web.WebActionType;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponent;
import org.nabucco.testautomation.engine.proxy.web.component.WebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.ActionResponse;
import org.nabucco.testautomation.result.facade.datatype.status.ActionStatusType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * WebHyperlinkImpl
 * 
 * @author Stefen Schmidt, PRODYNA AG
 */
public class WebHyperlinkImpl extends AbstractWebComponent {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link WebHyperlinkImpl} instance.
     * 
     * @param selenium
     *            the selenium instance.
     */
    public WebHyperlinkImpl(Selenium selenium) {
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
	
	        case LEFTCLICK:
	        case SELECT:
	        	command = new ClickHyperlinkCommand(this.getSelenium());
	            break;
	
	        default:
	        	return failResult(metadata, actionType, "Unsupported WebActionType for WebHyperlink: '" + actionType + "'");
	        }
	        
	        // Execute WebCommand
			PropertyList returnProperties = command.execute(metadata, propertyList);
			
			result.setMessage("Executed WebHyperlink action='" + actionType + "'");
			result.setReturnProperties(returnProperties);
			result.setActionStatus(ActionStatusType.EXECUTED);
			return result;
        } catch (WebComponentException ex) {
        	String errorMessage = "Could not execute WebHyperlink-command. Cause: " + ex.getMessage();
            this.error(errorMessage);
			result.setErrorMessage(errorMessage);
            result.setActionStatus(ActionStatusType.FAILED);
            return result;
        } catch (Exception ex) {
        	this.fatal(ex);
            result.setErrorMessage("Could not execute WebHyperlink-command. Cause: " + ex.toString());
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
    	// No Properties required
	}

}
