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
package org.nabucco.testautomation.engine.proxy.web.component.table;

import org.nabucco.testautomation.engine.base.util.PropertyHelper;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * GetCellCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class GetCellCommand extends AbstractWebComponentCommand {

	/**
	 * @param selenium
	 */
	public GetCellCommand(Selenium selenium) {
		super(selenium);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		String tableId = this.getComponentLocator(metadata);
		int col = this.getColumn(metadata, properties);
		int row = this.getRow(metadata, properties);
		String tableCellAddress = tableId + DOT + row + DOT + col;
		
		this.start();
		this.waitForElement(tableId);
        String text = this.getSelenium().getTable(tableCellAddress);
        this.stop();
        
        StringProperty property = (StringProperty) PropertyHelper.getFromList(properties, PropertyType.STRING).cloneObject();
        property.setValue(text);
        PropertyList returnProperties = PropertyHelper.createPropertyList(RETURN_PROPERTIES);
        this.add(property, returnProperties);
        return returnProperties;
	}
	
	private int getColumn(Metadata metadata, PropertyList properties) {
		
		// First, check PropertyList from Action
		Property colProperty = PropertyHelper.getFromList(properties, PropertyType.INTEGER, COLUMN);

		if (colProperty != null && colProperty.getType() == PropertyType.INTEGER) {
			return ((IntegerProperty) colProperty).getValue().getValue();
		}
		
		// Second, check PropertyList from Metadata
		colProperty = PropertyHelper.getFromList(metadata.getPropertyList(), PropertyType.INTEGER, COLUMN);
		
		if (colProperty != null && colProperty.getType() == PropertyType.INTEGER) {
			return ((IntegerProperty) colProperty).getValue().getValue();
		}
		
		return 0;
	}
	
	private int getRow(Metadata metadata, PropertyList properties) {
		
		// First, check PropertyList from Action
		Property rowProperty = PropertyHelper.getFromList(properties, PropertyType.INTEGER, ROW);

		if (rowProperty != null && rowProperty.getType() == PropertyType.INTEGER) {
			return ((IntegerProperty) rowProperty).getValue().getValue();
		}
		
		// Second, check PropertyList from Metadata
		rowProperty = PropertyHelper.getFromList(metadata.getPropertyList(), PropertyType.INTEGER, ROW);
		
		if (rowProperty != null && rowProperty.getType() == PropertyType.INTEGER) {
			return ((IntegerProperty) rowProperty).getValue().getValue();
		}
		
		return 0;
	}


}
