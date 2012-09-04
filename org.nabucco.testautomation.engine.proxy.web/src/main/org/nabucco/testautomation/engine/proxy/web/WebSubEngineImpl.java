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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.proxy.SubEngineActionType;
import org.nabucco.testautomation.engine.proxy.SubEngineOperationType;
import org.nabucco.testautomation.engine.proxy.base.AbstractSubEngine;
import org.nabucco.testautomation.engine.proxy.exception.SubEngineException;
import org.nabucco.testautomation.engine.proxy.web.component.WebComponent;
import org.nabucco.testautomation.engine.proxy.web.component.browser.BrowserWindowImpl;
import org.nabucco.testautomation.engine.proxy.web.component.button.WebButtonImpl;
import org.nabucco.testautomation.engine.proxy.web.component.captcha.WebCaptchaImpl;
import org.nabucco.testautomation.engine.proxy.web.component.checkbox.WebCheckBoxImpl;
import org.nabucco.testautomation.engine.proxy.web.component.cookie.CookieImpl;
import org.nabucco.testautomation.engine.proxy.web.component.dialog.WebDialogImpl;
import org.nabucco.testautomation.engine.proxy.web.component.document.WebDocumentImpl;
import org.nabucco.testautomation.engine.proxy.web.component.dropdownbox.WebDropDownBoxImpl;
import org.nabucco.testautomation.engine.proxy.web.component.element.WebElementImpl;
import org.nabucco.testautomation.engine.proxy.web.component.hyperlink.WebHyperlinkImpl;
import org.nabucco.testautomation.engine.proxy.web.component.javascript.JavaScriptImpl;
import org.nabucco.testautomation.engine.proxy.web.component.menu.WebContextMenuImpl;
import org.nabucco.testautomation.engine.proxy.web.component.screenshot.ScreenshotImpl;
import org.nabucco.testautomation.engine.proxy.web.component.table.WebTableImpl;
import org.nabucco.testautomation.engine.proxy.web.component.textfield.WebTextFieldImpl;
import org.nabucco.testautomation.engine.proxy.web.component.webpage.WebPageImpl;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.ActionResponse;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import org.nabucco.testautomation.settings.facade.datatype.engine.SubEngineType;

import com.thoughtworks.selenium.Selenium;

/**
 * WebSubEngineImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public class WebSubEngineImpl extends AbstractSubEngine implements WebEngine {

    private static final long serialVersionUID = 1L;

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebSubEngineImpl.class);

    private WebComponent webButton;

    private WebComponent webCheckBox;

    private WebComponent webHyperlink;

    private WebComponent webDropDownbox;

    private WebComponent webCaptcha;

    private WebComponent webDocument;

    private WebComponent webPage;

    private WebComponent webTextField;

    private WebComponent webElement;

    private WebComponent webContextMenu;

    private WebComponent webDialog;

    private WebComponent webTable;

    private WebComponent browserWindow;

    private WebComponent cookie;

    private WebComponent screenshot;

    private WebComponent javaScript;

    /**
     * Constructs a new instance with the given {@link MetadataCache}, selenium instance and logger.
     * 
     * @param metadataCache
     *            the cache with the proxy-specific metadata implementations
     * @param selenium
     *            the selenium instance to use
     */
    public WebSubEngineImpl(Selenium selenium) {
        super();
        this.webButton = new WebButtonImpl(selenium);
        this.webCheckBox = new WebCheckBoxImpl(selenium);
        this.webDropDownbox = new WebDropDownBoxImpl(selenium);
        this.webHyperlink = new WebHyperlinkImpl(selenium);
        this.webPage = new WebPageImpl(selenium);
        this.webCaptcha = new WebCaptchaImpl(selenium);
        this.webDocument = new WebDocumentImpl(selenium);
        this.webTextField = new WebTextFieldImpl(selenium);
        this.webElement = new WebElementImpl(selenium);
        this.webContextMenu = new WebContextMenuImpl(selenium);
        this.webDialog = new WebDialogImpl(selenium);
        this.webTable = new WebTableImpl(selenium);
        this.browserWindow = new BrowserWindowImpl(selenium);
        this.cookie = new CookieImpl(selenium);
        this.screenshot = new ScreenshotImpl(selenium);
        this.javaScript = new JavaScriptImpl(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse executeSubEngineOperation(SubEngineOperationType operationType,
            SubEngineActionType actionType, List<Metadata> metadataList, PropertyList propertyList, TestContext context)
            throws SubEngineException {

        WebEngineOperationType webEngineOperationType = (WebEngineOperationType) operationType;

        // execute operation
        switch (webEngineOperationType) {

        case WEB_BUTTON: {
            return this.getWebButton().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_CHECKBOX: {
            return this.getWebCheckbox().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_DROPDOWNBOX: {
            return this.getWebDropDownBox().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_HYPERLINK: {
            return this.getWebHyperlink().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_CAPTCHA: {
            return this.getWebCaptcha().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_PAGE: {
            return this.getWebPage().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_TEXTFIELD: {
            return this.getWebTextField().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_DOCUMENT: {
            return this.getWebDocument().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_ELEMENT: {
            return this.getWebElement().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_CONTEXT_MENU: {
            return this.getWebContextMenu().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_DIALOG: {
            return this.getWebDialog().execute(context, propertyList, metadataList, actionType);
        }
        case WEB_TABLE: {
            return this.getWebTable().execute(context, propertyList, metadataList, actionType);
        }
        case BROWSER_WINDOW: {
            return this.getBrowserWindow().execute(context, propertyList, metadataList, actionType);
        }
        case COOKIE: {
            return this.getCookie().execute(context, propertyList, metadataList, actionType);
        }
        case SCREENSHOT: {
            return this.getScreenshot().execute(context, propertyList, metadataList, actionType);
        }
        case JAVA_SCRIPT: {
            return this.getJavaScript().execute(context, propertyList, metadataList, actionType);
        }
        default: {
            String error = "Unsupported WebEngineOperationType = '" + operationType + "'";
            logger.error(error);
            throw new UnsupportedOperationException(error);
        }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebButton() {
        return this.webButton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebCaptcha() {
        return this.webCaptcha;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebCheckbox() {
        return this.webCheckBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebDocument() {
        return this.webDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebDropDownBox() {
        return this.webDropDownbox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebHyperlink() {
        return this.webHyperlink;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebPage() {
        return this.webPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebTextField() {
        return this.webTextField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebElement() {
        return this.webElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebTable() {
        return this.webTable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebDialog() {
        return this.webDialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getWebContextMenu() {
        return this.webContextMenu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getBrowserWindow() {
        return this.browserWindow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getCookie() {
        return this.cookie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getScreenshot() {
        return this.screenshot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebComponent getJavaScript() {
        return this.javaScript;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, SubEngineActionType> getActions() {
        Map<String, SubEngineActionType> actions = new HashMap<String, SubEngineActionType>();

        for (WebActionType action : WebActionType.values()) {
            actions.put(action.toString(), action);
        }
        return actions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, SubEngineOperationType> getOperations() {
        Map<String, SubEngineOperationType> operations = new HashMap<String, SubEngineOperationType>();

        for (WebEngineOperationType operation : WebEngineOperationType.values()) {
            operations.put(operation.toString(), operation);
        }
        return operations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubEngineType getType() {
        return SubEngineType.WEB;
    }

}
