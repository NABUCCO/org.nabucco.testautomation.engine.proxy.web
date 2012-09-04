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
package org.nabucco.testautomation.engine.proxy.web.component.document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.cache.DataCache;
import org.nabucco.testautomation.engine.proxy.web.component.AbstractWebComponentCommand;
import org.nabucco.testautomation.engine.proxy.web.exception.WebComponentException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.trace.ActionTrace;
import org.nabucco.testautomation.result.facade.datatype.trace.FileTrace;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

import com.thoughtworks.selenium.Selenium;

/**
 * ReadDocumentCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ReadDocumentCommand extends AbstractWebComponentCommand {

    private static final String HREF_ATTRIBUTE = "@href";

    private String filename;

    private byte[] document;

    /**
     * @param selenium
     */
    protected ReadDocumentCommand(Selenium selenium) {
        super(selenium);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyList executeCallback(Metadata metadata, PropertyList properties) throws WebComponentException {

        String documentId = this.getComponentLocator(metadata);

        try {
            this.start();
            this.waitForElement(documentId);
            documentId += HREF_ATTRIBUTE;
            String documentLocation = this.getSelenium().getAttribute(documentId);
            URI uri = new URI(documentLocation);

            if (!uri.isAbsolute()) {
                URI currentLocation = new URI(this.getSelenium().getLocation());
                uri = currentLocation.resolve(uri);
            }

            URL url = uri.toURL();
            InputStream in = url.openConnection().getInputStream();
            byte[] buf = new byte[1024];
            int c;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ((c = in.read(buf)) != -1) {
                baos.write(buf, 0, c);
            }
            this.document = baos.toByteArray();
            this.filename = url.getFile();

            if (this.filename.contains(SLASH)) {
                this.filename = this.filename.substring(this.filename.lastIndexOf(SLASH) + 1, this.filename.length());
            }
            this.stop();
            return null;
        } catch (IOException ex) {
            this.stop();
            this.setException(ex);
            throw new WebComponentException("Error reading WebDocument: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            this.stop();
            this.setException(ex);
            throw new WebComponentException("Error locating WebDocument: " + ex.getMessage());
        }
    }

    @Override
    public ActionTrace getActionTrace() {
        FileTrace trace = TestResultHelper.createFileTrace();
        long duration = this.getStop() - this.getStart();
        trace.setStartTime(new Date(this.getStart()));
        trace.setEndTime(new Date(this.getStop() > 0 ? this.getStop() : System.currentTimeMillis()));
        trace.setDuration(duration >= 0 ? duration : System.currentTimeMillis() - this.getStart());
        trace.setStackTrace(TestResultHelper.getStackTrace(this.getException()));

        // Put Image into Cache
        Identifier documentId = new Identifier(UUID.randomUUID().getMostSignificantBits());
        Data data = new Data(this.document);
        DataCache.getInstance().put(documentId, data);
        trace.setFileId(documentId);
        trace.setName(this.filename);
        return trace;
    }

}
