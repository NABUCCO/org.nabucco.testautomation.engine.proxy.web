/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.engine.proxy.web.component.browser;

import org.nabucco.testautomation.property.facade.datatype.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * OpenWindowCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class OpenWindowCommand extends AbstractWebComponentCommand {

    /**
     * @param selenium
     */
    public OpenWindowCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String url = getUrl(metadata, properties);
        String windowId = getWindowId(metadata, properties);

        this.start();
        this.getSelenium().openWindow(url, windowId);
        this.getSelenium().selectWindow(windowId);
        this.stop();
        return null;
    }

    private String getUrl(Metadata metadata, PropertyList properties) {

        // First, check PropertyList from Action
        Property urlProperty = PropertyHelper.getFromList(properties, URL);

        if (urlProperty != null && urlProperty.getType() == PropertyType.TEXT) {
            return ((TextProperty) urlProperty).getValue().getValue();
        }

        // Second, check PropertyList from Metadata
        urlProperty = PropertyHelper.getFromList(metadata.getPropertyList(), URL);

        if (urlProperty != null && urlProperty.getType() == PropertyType.TEXT) {
            return ((TextProperty) urlProperty).getValue().getValue();
        }

        return EMPTY_STRING;
    }

    private String getWindowId(Metadata metadata, PropertyList properties) {

        // First, check PropertyList from Action
        Property idProperty = PropertyHelper.getFromList(properties, WINDOW_ID);

        if (idProperty != null && idProperty.getType() == PropertyType.TEXT) {
            return ((TextProperty) idProperty).getValue().getValue();
        }

        // Second, check PropertyList from Metadata
        idProperty = PropertyHelper.getFromList(metadata.getPropertyList(), WINDOW_ID);

        if (idProperty != null && idProperty.getType() == PropertyType.TEXT) {
            return ((TextProperty) idProperty).getValue().getValue();
        }

        return EMPTY_STRING;
    }

}
