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

import org.nabucco.testautomation.engine.base.logging.NBCTestLogger;
import org.nabucco.testautomation.engine.base.logging.NBCTestLoggingFactory;
import org.nabucco.testautomation.engine.base.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.base.AbstractProxyCommand;
import org.nabucco.testautomation.engine.proxy.web.component.WebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.BooleanProperty;
import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.LongProperty;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

/**
 * AbstractWebComponentCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public abstract class AbstractWebComponentCommand extends AbstractProxyCommand implements WebComponentCommand {

	private static final NBCTestLogger logger = NBCTestLoggingFactory
			.getInstance().getLogger(WebComponentCommand.class);
	
	private final Selenium selenium;
	
	private boolean ajax = false;
	
	private String timeout = DEFAULT_TIMEOUT;
	
	protected AbstractWebComponentCommand(Selenium selenium) {
		this.selenium = selenium;
	}
	
	@Override
	public PropertyList execute(Metadata metadata, PropertyList properties)
			throws WebComponentException {
		
		try {
			this.checkTimeout(metadata);
			this.checkAjax(metadata);
			return executeCallback(metadata, properties);
		} catch (WebComponentException ex) {
			throw ex;
		} catch (SeleniumException ex) {
			this.setException(ex);
			throw new WebComponentException(ex.getMessage());
		} catch (Exception ex) {
			this.setException(ex);
			logger.error(ex);
			throw new WebComponentException("Error executing WebComponentCommand. Cause: " + ex.toString());
		} finally {
			this.setRequest(this.getSelenium().captureNetworkTraffic("plain"));
		}
	}
	
	public abstract PropertyList executeCallback(Metadata metadata, PropertyList properties)
			throws WebComponentException;
	
	@Override
	protected void info(String msg) {
		logger.info(msg);
	}

	@Override
	protected void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	protected void error(String msg) {
		logger.error(msg);
	}

	@Override
	protected void warning(String msg) {
		logger.warning(msg);
	}
	
	/**
     * Returns the selenium selector (see
     * http://release.seleniumhq.org/selenium-core/1.0/reference.html) string to locate html
     * elements in selenium following precedence:
     * <ol>
     * <li>ELEMENT ID</li>
     * <li>DOM ID</li>
     * <li>XPath</li>
     * <li>LINK</li>
     * <li>URL</li>
     * <li>Metadata element ID</li>
     * </ol>
     * 
     * @see http://release.seleniumhq.org/selenium-core/1.0/reference.html
     * 
     * @param metadata
     *            the metadata element
     * 
     * @return the component locator
     */
    protected  String getComponentLocator(Metadata metadata) {

    	PropertyList propertyList = metadata.getPropertyList();
        String componentId = null;
        
        // Try XPath
        Property xpath = null;
        
        if (PropertyHelper.contains(propertyList, PropertyType.XPATH)) {
        	xpath = PropertyHelper.getFromList(propertyList, PropertyType.XPATH);
        } else {
        	xpath = PropertyHelper.getFromList(propertyList, XPATH);
        }
        
        if (xpath != null) {
            componentId = "xpath=" + PropertyHelper.toString(xpath);
            return componentId;
        }

        // Try ElementID
		Property elementID = PropertyHelper.getFromList(propertyList, ELEMENT_ID);
        if (elementID != null) {
            componentId = "identifier=" + PropertyHelper.toString(elementID);
            return componentId;
        }

        // Try DOM
        Property dom = PropertyHelper.getFromList(propertyList, DOM);
        if (dom != null) {
            componentId = "dom=" + PropertyHelper.toString(dom);
            return componentId;
        }

        // Try Link
        Property link = PropertyHelper.getFromList(propertyList, LINK);
        if (link != null) {
            componentId = "link=" + PropertyHelper.toString(link);
            return componentId;
        }

        // Try URL
        Property url = PropertyHelper.getFromList(propertyList, URL);
        if (url != null) {
            componentId = PropertyHelper.toString(url);
            return componentId;
        }

		throw new IllegalArgumentException(
				"No ComponentIdentifier defined in Metadata '"
						+ metadata.getName().getValue() + "'");
    }
    
    /**
     * 
     * @param componentIdentifier
     */
    protected boolean waitForElement(String locator) {
    	
		long timeoutBarrier = System.currentTimeMillis() + Long.parseLong(this.getTimeout());
		boolean isElementPresent = false;
		
		while (!(isElementPresent = this.getSelenium().isElementPresent(locator))
				&& System.currentTimeMillis() < timeoutBarrier) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException ex) {
			}
		}
		return isElementPresent;
    }
    
    /**
     * Checks, if a waiting timeout is configured in the given PropertyList.
     * 
	 * @param propertyList the PropertyList to check
	 */
	protected void checkTimeout(PropertyList propertyList) {

		Property timeoutProperty = PropertyHelper.getFromList(propertyList, TIMEOUT);
		
		if (timeoutProperty != null) {
			
			switch (timeoutProperty.getType()) {
			case INTEGER:
				this.timeout = ((IntegerProperty) timeoutProperty).getValue().getValue().toString();
				break;
			case LONG:
				this.timeout = ((LongProperty) timeoutProperty).getValue().toString();
				break;
			case STRING:
				this.timeout = ((StringProperty) timeoutProperty).getValue().getValue();
				break;
			}
		}
	}
	
	/**
     * Checks, if a waiting timeout is configured for the given metadata.
     * 
	 * @param metadata the Metadata to check
	 */
	protected void checkTimeout(Metadata metadata) {

		Property timeoutProperty = PropertyHelper.getFromList(metadata.getPropertyList(), TIMEOUT);
		
		if (timeoutProperty != null) {
			
			switch (timeoutProperty.getType()) {
			case INTEGER:
				this.timeout = ((IntegerProperty) timeoutProperty).getValue().getValue().toString();
				break;
			case LONG:
				this.timeout = ((LongProperty) timeoutProperty).getValue().toString();
				break;
			case STRING:
				this.timeout = ((StringProperty) timeoutProperty).getValue().getValue();
				break;
			}
		}
	}
	
    /**
     * Checks, if the given Metadata requires an Ajax-call.
     * 
	 * @param metadata the Metadata to check
	 */
	protected void checkAjax(Metadata metadata) {

		BooleanProperty ajaxProperty = (BooleanProperty) PropertyHelper
				.getFromList(metadata.getPropertyList(), PropertyType.BOOLEAN, AJAX);

		if (ajaxProperty != null && ajaxProperty.getValue() != null
				&& ajaxProperty.getValue().getValue() != null) {
			this.ajax = ajaxProperty.getValue().getValue();
		}
	}

    
    /**
     * 
     * @return
     */
    protected Selenium getSelenium() {
		return this.selenium;
	}
	
    /**
     * Clears the contained list of Properties
     * 
     * @param list
     */
    protected void clear(PropertyList list) {
    	list.getPropertyList().clear();
    }
    
    /**
     * 
     * @return
     */
    protected boolean isAjax() {
    	return this.ajax;
    }
    
    /**
     * 
     * @return
     */
    protected String getTimeout() {
    	return this.timeout;
    }
    
}
