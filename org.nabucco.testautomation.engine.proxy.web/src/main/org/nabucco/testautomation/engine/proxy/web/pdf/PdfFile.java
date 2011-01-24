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
package org.nabucco.testautomation.engine.proxy.web.pdf;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.pdf.PdfReader;

/**
 * Represents a PDF file that may be read
 * 
 * @author Nicolas Moser, PRODYNA AG
 * 
 */
public class PdfFile implements Closeable {

    private PdfReader reader;

    /**
     * Creates a new PDF file based on the file pathname.
     * 
     * @param pathName
     *            the pathname
     * 
     * @throws IOException
     */
    public PdfFile(String pathName) throws IOException {
        this.reader = new PdfReader(pathName);
    }

    /**
     * Creates a new PDF file based on a URL.
     * 
     * @param url
     *            the URL to the file
     * 
     * @throws IOException
     */
    public PdfFile(URL url) throws IOException {
        this.reader = new PdfReader(url);
    }

    /**
     * Reads the text from a PDF file to a String.
     * 
     * @return the content text
     * 
     * @throws IOException
     */
    public String read() throws IOException {

        int numberOfPages = this.reader.getNumberOfPages();

        StringBuffer builder = new StringBuffer();

        Pattern pattern = Pattern.compile("\\[\\(.*\\)\\s\\]");

        for (int i = 1; i <= numberOfPages; i++) {
            String content = new String(this.reader.getPageContent(i));
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                builder.append(matcher.group(0));
            }
        }

        String result = builder.toString().replace("[(", "").replace(") ]", "").replace(") ( ) (",
                "");

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        this.reader.close();
    }

}
