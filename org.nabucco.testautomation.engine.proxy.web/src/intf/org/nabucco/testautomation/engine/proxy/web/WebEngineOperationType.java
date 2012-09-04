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
package org.nabucco.testautomation.engine.proxy.web;

import org.nabucco.testautomation.engine.proxy.SubEngineOperationType;

/**
 * WebEngineOperationType
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public enum WebEngineOperationType implements SubEngineOperationType {

    /**
     * The OperationType for a web button.
     */
    WEB_BUTTON,

    /**
     * The OperationType for a checkbox.
     */
    WEB_CHECKBOX,

    /**
     * The OperationType for a dropdown box.
     */
    WEB_DROPDOWNBOX,

    /**
     * The OperationType for a web link.
     */
    WEB_HYPERLINK,

    /**
     * The OperationType for a text input field.
     */
    WEB_TEXTFIELD,

    /**
     * The OperationType for a catcha component.
     */
    WEB_CAPTCHA,

    /**
     * The OperationType for a web document.
     */
    WEB_DOCUMENT,

    /**
     * The OperationType for a web page.
     */
    WEB_PAGE,

    /**
     * The OperationType for any HTML element.
     */
    WEB_ELEMENT,

    /**
     * The OperationType for a JavaScript-Dialog.
     */
    WEB_DIALOG,

    /**
     * The OperationType for a Context Menu.
     */
    WEB_CONTEXT_MENU,

    /**
     * The OperationType for a table.
     */
    WEB_TABLE,

    /**
     * The OperationType for a Browser-Window.
     */
    BROWSER_WINDOW,

    /**
     * The OperationType for a Browser-Screenshot.
     */
    SCREENSHOT,

    /**
     * The OperationType for a Browser-Cookie.
     */
    COOKIE,

    /**
     * The OperationType for JavaScript
     */
    JAVA_SCRIPT;

}
