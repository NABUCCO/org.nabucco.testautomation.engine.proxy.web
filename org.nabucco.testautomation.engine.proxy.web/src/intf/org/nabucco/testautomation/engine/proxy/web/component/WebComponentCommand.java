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
package org.nabucco.testautomation.engine.proxy.web.component;

import org.nabucco.testautomation.engine.proxy.ProxyCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

/**
 * WebComponentCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public interface WebComponentCommand extends ProxyCommand {

    public static final String EMPTY_STRING = "";

    public static final String DOT = ".";

    public static final String SLASH = "/";

    public static final String AT = "@";

    public static final String AJAX = "AJAX";

    public static final String TIMEOUT = "TIMEOUT";

    public static final String ELEMENT_ID = "ELEMENT_ID";

    public static final String DOM = "DOM";

    public static final String XPATH = "XPATH";

    public static final String LINK = "LINK";

    public static final String URL = "URL";

    public static final String NAME = "NAME";

    public static final String VALUE = "VALUE";

    public static final String WINDOW_ID = "WINDOW_ID";

    public static final String ROW = "ROW";

    public static final String COLUMN = "COLUMN";

    public static final String RETURN_PROPERTIES = "ReturnProperties";

    public static final String DEFAULT_TIMEOUT = "30000";

    public static final String ALT = "ALT";

    public static final String SHIFT = "SHIFT";

    public static final String CTRL = "CTRL";

    /**
     * 
     * @param metadata
     * @param properties
     * @return
     * @throws WebComponentException
     */
    public PropertyList execute(Metadata metadata, PropertyList properties) throws WebComponentException;

}
