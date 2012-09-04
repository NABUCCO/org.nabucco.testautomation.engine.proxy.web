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
package org.nabucco.testautomation.engine.proxy.web.component.table;

import java.math.BigDecimal;

import org.nabucco.testautomation.property.facade.datatype.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.NumericProperty;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * CountTableRowsCommand
 * 
 * @author Florian Schmidt, PRODYNA AG
 */
public class CountTableRowsCommand extends AbstractWebComponentCommand {

    /**
     * @param selenium
     */
    public CountTableRowsCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String tableId = this.getComponentLocator(metadata);
        if (tableId.startsWith("xpath")) {
            tableId = tableId.split("=")[1];
        } else if (tableId.startsWith("identifier")) {
            String id = "//*[@id='" + tableId.split("=")[1] + "'][1]";
            String name = "//*[@name='" + tableId.split("=")[1] + "'][1]";
            if (this.getSelenium().getXpathCount(id).intValue() > 0) {
                tableId = id;
            } else {
                tableId = name;
            }
        } else {
            throw new WebComponentException("Component locator has to be xpath or identifier.");
        }

        // Set xpath so it selects the last row wether it is a child or grandchild of table
        String path = "xpath=" + tableId + "/tr[last()]|" + tableId + "/tbody/tr[last()]";
        this.start();
        this.waitForElement(tableId);

        Number elementIndex = this.getSelenium().getElementIndex(path);
        // Element index starts with 0 so we add 1
        long count = elementIndex.longValue() + 1;
        BigDecimal number = BigDecimal.valueOf(count);
        this.stop();

        NumericProperty property = (NumericProperty) PropertyHelper.getFromList(properties, PropertyType.NUMERIC)
                .cloneObject();
        property.setValue(number);
        PropertyList returnProperties = PropertyHelper.createPropertyList(RETURN_PROPERTIES);
        this.add(property, returnProperties);
        return returnProperties;
    }

}
