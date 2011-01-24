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
package org.nabucco.testautomation.engine.proxy.web;

import org.nabucco.testautomation.engine.proxy.SubEngine;
import org.nabucco.testautomation.engine.proxy.web.component.WebComponent;


/**
 * WebEngine
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public interface WebEngine extends SubEngine {

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a web button.
     * 
     * @return the WebButton
     */
    public WebComponent getWebButton();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a text input field.
     * 
     * @return the WebTextField
     */
    public WebComponent getWebTextField();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a checkbox.
     * 
     * @return the WebCheckbox
     */
    public WebComponent getWebCheckbox();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a dropdown box.
     * 
     * @return the WebDropDownBox
     */
    public WebComponent getWebDropDownBox();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a web page.
     * 
     * @return the WebPage
     */
    public WebComponent getWebPage();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a captcha component.
     * 
     * @return the WebCaptcha
     */
    public WebComponent getWebCaptcha();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a web link.
     * 
     * @return the WebHyperlink
     */
    public WebComponent getWebHyperlink();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a web document.
     * 
     * @return the WebDocument
     */
    public WebComponent getWebDocument();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access any html element.
     * 
     * @return the WebElement
     */
    public WebComponent getWebElement();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access a html table.
     * 
     * @return the WebTable
     */
    public WebComponent getWebTable();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access any JavaScript-Dialog.
     * 
     * @return the WebDialog
     */
    public WebComponent getWebDialog();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access any ContextMenu.
     * 
     * @return the WebContextMenu
     */
    public WebComponent getWebContextMenu();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access any browser window.
     * 
     * @return the BrowserWindow
     */
    public WebComponent getBrowserWindow();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to access any Cookie.
     * 
     * @return the Cookie
     */
    public WebComponent getCookie();
    
    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to create a screenshot of the browser.
     * 
     * @return the Screenshot
     */
    public WebComponent getScreenshot();

    /**
     * Gets an implementation of {@link WebEngineOperation}
     * to evaluate a JavaScript snippet.
     * 
     * @return the JavaScript
     */
    public WebComponent getJavaScript();

}
