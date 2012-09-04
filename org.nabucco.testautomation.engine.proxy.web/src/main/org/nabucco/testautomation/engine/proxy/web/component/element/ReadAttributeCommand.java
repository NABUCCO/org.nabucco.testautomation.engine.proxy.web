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
package org.nabucco.testautomation.engine.proxy.web.component.element;

import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyType;
import org.nabucco.testautomation.property.facade.datatype.util.PropertyHelper;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * ReadAttributeCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
class ReadAttributeCommand extends AbstractWebComponentCommand {

    /**
     * @param selenium
     */
    public ReadAttributeCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String element = super.getComponentLocator(metadata);
        TextProperty content = null;
        String attributeName = null;

        for (PropertyContainer container : properties.getPropertyList()) {
            Property prop = container.getProperty();

            if (prop.getType() == PropertyType.TEXT && prop.getName().getValue().equals(NAME)) {
                attributeName = ((TextProperty) prop).getValue().getValue();
            } else if (prop.getType() == PropertyType.TEXT && content == null) {
                content = (TextProperty) prop.cloneObject();
            }
        }

        if (attributeName == null) {
            throw new IllegalArgumentException("Required TextProperty with name 'NAME' not found");
        }

        this.start();
        this.waitForElement(element);
        String attributeValue = this.getSelenium().getAttribute(element + AT + attributeName);
        this.stop();

        if (content != null) {
            content.setValue(attributeValue);
        }

        PropertyList returnProperties = PropertyHelper.createPropertyList(RETURN_PROPERTIES);
        this.add(content, returnProperties);
        return returnProperties;
    }

}
