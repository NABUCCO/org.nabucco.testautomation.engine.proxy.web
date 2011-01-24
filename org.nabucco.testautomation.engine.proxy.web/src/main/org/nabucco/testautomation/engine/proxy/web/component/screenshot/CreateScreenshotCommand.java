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
package org.nabucco.testautomation.engine.proxy.web.component.screenshot;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.image.ImageData;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.cache.ImageCache;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.trace.ActionTrace;
import org.nabucco.testautomation.result.facade.datatype.trace.ScreenshotTrace;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import com.thoughtworks.selenium.Selenium;

/**
 * CreateScreenshotCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class CreateScreenshotCommand extends AbstractWebComponentCommand {

	private String screenshot;
	
	/**
	 * @param selenium
	 */
	public CreateScreenshotCommand(Selenium selenium) {
		super(selenium);
	}

	@Override
	public PropertyList executeCallback(Metadata metadata,
			PropertyList properties) throws WebComponentException {
		
		this.start();
		this.screenshot = this.getSelenium().captureEntirePageScreenshotToString("background=#FFFFFF");
		this.stop();
		
		return null;
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
		ImageData image = decode(screenshot);
		ImageCache.getInstance().put(imageId, image);
		trace.setImageId(imageId);
		
		return trace;
	}

	/**
	 * @param encodedImage
	 * @return
	 */
	private ImageData decode(String encodedImage) {
		byte[] imageData = Base64.decodeBase64(encodedImage);
		return new ImageData(imageData);
	}

}
