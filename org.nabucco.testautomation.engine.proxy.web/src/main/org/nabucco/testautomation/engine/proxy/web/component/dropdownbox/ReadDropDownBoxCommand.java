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
package org.nabucco.testautomation.engine.proxy.web.component.dropdownbox;

import org.nabucco.testautomation.property.facade.datatype.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * ReadDropDownBoxCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ReadDropDownBoxCommand extends AbstractWebComponentCommand {

    /**
     * @param selenium
     */
    public ReadDropDownBoxCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String dropDownId = this.getComponentLocator(metadata);
        TextProperty property = (TextProperty) properties.getPropertyList().get(0).getProperty().cloneObject();

        this.start();
        this.waitForElement(dropDownId);
        String selectedLabel = this.getSelenium().getSelectedLabel(dropDownId);
        this.stop();

        property.setValue(selectedLabel);
        PropertyList returnProperties = PropertyHelper.createPropertyList(RETURN_PROPERTIES);
        this.add(property, returnProperties);
        return returnProperties;
    }

}
