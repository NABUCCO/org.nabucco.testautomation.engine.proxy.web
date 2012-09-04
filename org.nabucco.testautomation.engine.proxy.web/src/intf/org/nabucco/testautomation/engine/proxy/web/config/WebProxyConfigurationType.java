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
package org.nabucco.testautomation.engine.proxy.web.config;

/**
 * WebProxyConfigurationType
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public enum WebProxyConfigurationType {

    /**
     * Constant for the property-key for the server port.
     */
    SERVER_PORT("serverPort"),

    /**
     * Constant for the property-key for the hostname.
     */
    SERVER_HOST("serverHost"),

    /**
     * Constant for the property-key for the browser type.
     */
    BROWSER("browser"),

    /**
     * Constant for the property-key for the browser path.
     */
    BROWSER_PATH("browserPath"),

    /**
     * Constant for the property-key for the base URL.
     */
    BASE_URL("baseURL"),

    /**
     * Constant for the property-key for the server timeout.
     */
    RC_SERVER_TIMEOUT("rcServerTimeout"),

    /**
     * Constant for the property-key for the firefox profile directory.
     */
    FIREFOX_PROFILE_DIR("firefoxProfileDir"),

    /**
     * Constant for the property-key for the firefox profile directory.
     */
    TRUST_ALL_SSL_CERTIFICATES("trustAllSSLCertificates");

    private String key;

    private WebProxyConfigurationType(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

}
