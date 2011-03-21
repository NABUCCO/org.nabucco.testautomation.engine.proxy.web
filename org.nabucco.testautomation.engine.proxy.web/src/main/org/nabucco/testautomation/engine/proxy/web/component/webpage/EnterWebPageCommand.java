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
package org.nabucco.testautomation.engine.proxy.web.component.webpage;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;


/**
 * EnterWebPageCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class EnterWebPageCommand extends AbstractWebComponentCommand {

	/**
	 * @param selenium
	 */
	protected EnterWebPageCommand(Selenium selenium) {
		super(selenium);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		// Evaluate url
        String urlToEnter = this.getComponentLocator(metadata);

        // Try URL
        try {
        	URI uri = new URI(urlToEnter);
        	
        	// check if absolute or relative URL
        	if (!uri.isAbsolute()) {
        		URI currentLocation = new URI(this.getSelenium().getLocation());
        		uri = currentLocation.resolve(uri);
        	}
            URL url = uri.toURL();
            info("Trying to open url " + url);
            this.start();
            this.getSelenium().open(url.toString());
            
            if (!this.isAjax()) {
            	this.getSelenium().waitForPageToLoad(this.getTimeout());
            }
            this.stop();
            return null;
        } catch (MalformedURLException ex) {
        	this.stop();
        	this.setException(ex);
            throw new WebComponentException("Invalid URL: " + urlToEnter);
        } catch (URISyntaxException ex) {
        	this.stop();
        	this.setException(ex);
            throw new WebComponentException("Invalid URL: " + urlToEnter);
		}
	}

}
