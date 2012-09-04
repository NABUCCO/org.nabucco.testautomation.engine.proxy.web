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
package org.nabucco.testautomation.engine.proxy.web.component.cookie;

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
 * CreateCookieCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class CreateCookieCommand extends AbstractWebComponentCommand {

    /**
     * @param selenium
     */
    public CreateCookieCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String name = getName(metadata, properties);
        String value = getValue(metadata, properties);

        this.start();
        this.getSelenium().createCookie(name + "=" + value, EMPTY_STRING);
        this.stop();

        return null;
    }

    private String getName(Metadata metadata, PropertyList properties) {

        // First, check PropertyList from Action
        Property nameProperty = PropertyHelper.getFromList(properties, NAME);

        if (nameProperty != null && nameProperty.getType() == PropertyType.TEXT) {
            return ((TextProperty) nameProperty).getValue().getValue();
        }

        // Second, check PropertyList from Metadata
        nameProperty = PropertyHelper.getFromList(metadata.getPropertyList(), NAME);

        if (nameProperty != null && nameProperty.getType() == PropertyType.TEXT) {
            return ((TextProperty) nameProperty).getValue().getValue();
        }

        return EMPTY_STRING;
    }

    private String getValue(Metadata metadata, PropertyList properties) {

        // First, check PropertyList from Action
        Property valueProperty = PropertyHelper.getFromList(properties, VALUE);

        if (valueProperty != null) {
            return PropertyHelper.toString(valueProperty);
        }

        // Second, check PropertyList from Metadata
        valueProperty = PropertyHelper.getFromList(metadata.getPropertyList(), VALUE);

        if (valueProperty != null) {
            return PropertyHelper.toString(valueProperty);
        }

        return EMPTY_STRING;
    }

}
