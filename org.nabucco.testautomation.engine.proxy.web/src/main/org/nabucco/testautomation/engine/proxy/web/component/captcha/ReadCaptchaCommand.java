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
package org.nabucco.testautomation.engine.proxy.web.component.captcha;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.image.ImageData;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.cache.ImageCache;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.trace.ActionTrace;
import org.nabucco.testautomation.result.facade.datatype.trace.ScreenshotTrace;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * ReadCaptchaCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ReadCaptchaCommand extends AbstractWebComponentCommand {

    private static final String CAPTCHA_WINDOW = "CaptchaWindow";

    private static final String SRC_ATTRIBUTE = "@src";

    private byte[] imageData;

    /**
     * @param selenium
     */
    public ReadCaptchaCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String captchaId = this.getComponentLocator(metadata);

        try {
            this.waitForElement(captchaId);
            captchaId += SRC_ATTRIBUTE;
            String imageLocation = this.getSelenium().getAttribute(captchaId);
            URI uri = new URI(imageLocation);

            if (!uri.isAbsolute()) {
                URI currentLocation = new URI(this.getSelenium().getLocation());
                uri = currentLocation.resolve(uri);
            }
            URL url = uri.toURL();

            this.start();
            this.getSelenium().openWindow(url.toString(), CAPTCHA_WINDOW);
            this.getSelenium().waitForPopUp(CAPTCHA_WINDOW, this.getTimeout());
            this.getSelenium().selectWindow(CAPTCHA_WINDOW);
            this.imageData = decode(this.getSelenium().captureEntirePageScreenshotToString("background=#FFFFFF"));
            this.getSelenium().close();
            this.getSelenium().selectWindow(null);
            this.stop();
            return null;
        } catch (IOException ex) {
            this.stop();
            this.setException(ex);
            throw new WebComponentException("Error during captcha identification: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            this.stop();
            this.setException(ex);
            throw new WebComponentException("Error locating WebCaptcha: " + ex.getMessage());
        }
    }

    private byte[] decode(String encodedImage) {
        byte[] imageData = Base64.decodeBase64(encodedImage);
        return imageData;
    }

    @Override
    public ActionTrace getActionTrace() {
        ScreenshotTrace trace = TestResultHelper.createScreenshotTrace();
        long duration = this.getStop() - this.getStart();
        trace.setStartTime(new Date(this.getStart()));
        trace.setEndTime(new Date(this.getStop() > 0 ? this.getStop() : System.currentTimeMillis()));
        trace.setDuration(duration >= 0 ? duration : System.currentTimeMillis() - this.getStart());
        trace.setStackTrace(TestResultHelper.getStackTrace(this.getException()));

        // Put Image into Cache
        Identifier imageId = new Identifier(UUID.randomUUID().getMostSignificantBits());
        ImageData image = new ImageData(imageData);
        ImageCache.getInstance().put(imageId, image);
        trace.setImageId(imageId);
        return trace;
    }

}
