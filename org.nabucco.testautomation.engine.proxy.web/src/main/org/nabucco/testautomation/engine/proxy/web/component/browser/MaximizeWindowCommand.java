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

import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * MaximizeWindowCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class MaximizeWindowCommand extends AbstractWebComponentCommand {

    /**
     * @param selenium
     */
    public MaximizeWindowCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        this.start();
        this.getSelenium().windowMaximize();
        this.stop();
        return null;
    }

}
