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
package org.nabucco.testautomation.engine.proxy.web.component;

import java.util.List;

import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.base.logging.NBCTestLogger;
import org.nabucco.testautomation.engine.base.logging.NBCTestLoggingFactory;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.SubEngineActionType;
import org.nabucco.testautomation.engine.proxy.web.WebActionType;
import org.nabucco.testautomation.engine.proxy.web.WebEngineOperation;
import org.nabucco.testautomation.engine.proxy.web.component.WebComponent;

import org.nabucco.testautomation.facade.datatype.PropertyFactory;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.result.facade.datatype.ActionResponse;
import org.nabucco.testautomation.result.facade.datatype.status.ActionStatusType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * Abstract base class for all web control elements.
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public abstract class AbstractWebComponent implements WebComponent {

    private static final long serialVersionUID = 1L;
    
    private static final NBCTestLogger logger = NBCTestLoggingFactory
			.getInstance().getLogger(WebComponent.class);

    private Selenium selenium;

    /**
     * Creates a new Selenium WebComponent instance.
     * 
     * @param selenium
     *            the selenium
     * @param logger
     *            the logger
     */
    protected AbstractWebComponent(Selenium selenium) {
        this.selenium = selenium;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse execute(TestContext context, PropertyList propertyList,
            List<Metadata> metadataList, SubEngineActionType actionType) {

    	Metadata metadata = getLeaf(metadataList);
    	
    	// check request-arguments
		if (propertyList == null) {
			propertyList = (PropertyList) PropertyFactory.getInstance()
					.produceProperty(PropertyType.LIST);
		}

    	try {
    		validateArguments(context, propertyList, metadataList, actionType);
    		validateProperties(propertyList, (WebActionType) actionType);
            return internalExecute(context, propertyList, metadata, (WebActionType) actionType);
        } catch (IllegalArgumentException ex) {
        	logger.error(ex.getMessage());
        	return failResult(metadata, actionType, ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex, "Error executing WebComponent " + metadata.getName().getValue());
            return failResult(metadata, actionType, ex.getMessage());
        }
    }
    
    protected Selenium getSelenium() {
    	return this.selenium;
    }
    
    protected void info(String message) {
    	logger.info(message);
    }
    
    protected void debug(String message) {
    	logger.debug(message);
    }
    
    protected void error(String message) {
    	logger.error(message);
    }   
    
    protected void fatal(Exception ex) {
    	logger.fatal(ex);
    }

    /**
     * Pass through of
     * {@link WebEngineOperation#execute(TestContext, List, List, SubEngineActionType)}.
     * 
     * @see WebEngineOperation#execute(TestContext, List, List, SubEngineActionType)
     */
    protected abstract ActionResponse internalExecute(TestContext context, PropertyList propertyList,
            Metadata metadata, WebActionType actionType);
    
    /**
     * 
     * @param propertyList
     * @param actionType
     */
    protected abstract void validateProperties(PropertyList propertyList, WebActionType actionType);

    /**
     * Initial validation of metadata and action type.
     * 
     * @param context
     *            the test context
     * @param propertyList
     *            the list of properties
     * @param metadataList
     *            the metadata list
     * @param actionType
     *            the action type
     */
    private void validateArguments(TestContext context, PropertyList propertyList,
            List<Metadata> metadataList, SubEngineActionType actionType) {

        if (context == null) {
            throw new IllegalArgumentException("TestContext must not be null.");
        }

        if (propertyList == null) {
            throw new IllegalArgumentException("PropertyList must not be null.");
        }

        if (metadataList == null) {
            throw new IllegalArgumentException("MetadataList must not be null.");
        }

        if (actionType == null) {
            throw new IllegalArgumentException("ActionType must not be null.");
        }

        if (!(actionType instanceof WebActionType)) {
            throw new IllegalArgumentException("ActionType must be a WebActionType.");
        }
    }

    /**
     * Returns the metadata information of the ui element to work with
     * 
     * @param metadataList
     *            the list of {@link Metadata}
     * 
     * @return the leaf element to work with
     */
    private Metadata getLeaf(List<Metadata> metadataList) {
    	
    	if (metadataList.isEmpty()) {
    		throw new IllegalArgumentException("No Metadata defined");
    	}
        Metadata metadata = metadataList.get(metadataList.size() - 1);
        return metadata;
    }

    /**
     * Extracts the property out of test context that corresponds to the test script property
     * 
     * @param context
     *            the text context with all properties
     * @param scriptProperty
     *            the script property to extract the context property with
     * 
     * @return the context property
     */
    protected Property getContextProperty(TestContext context, Property scriptProperty) {

        if (scriptProperty == null) {
            String msg = "Script property is not valid [null].";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Property result = context.getProperty(scriptProperty.getName().getValue());

        if (result == null) {
            String msg = "Property with id "
                    + scriptProperty.getId()
                    + " cannot be found in given TestContext.";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }

        return result;
    }

    /**
     * Changes the execution result to failed and adds an error message.
     * 
     * @param metadata
     *            the current metadata object
     * @param actionType
     *            the action type
     * 
     * @return the failed result
     */
    protected ActionResponse failResult(Metadata metadata,
            SubEngineActionType actionType, String errorMessage) {

        StringBuilder message = new StringBuilder();
        message.append("Error executing operation on ");
        message.append(metadata != null && metadata.getOperation() != null ? metadata.getOperation().getName().getValue() : "n/a");
        message.append(" with name='");
        message.append(metadata != null && metadata.getName() != null ? metadata.getName().getValue() : "n/a");
        message.append("' and action='");
        message.append(actionType);
        message.append("'. Cause: ");
        message.append(errorMessage);

        ActionResponse result = TestResultHelper.createActionResponse();
        result.setActionStatus(ActionStatusType.FAILED);
        result.setErrorMessage(message.toString());
        return result;
    }
}
