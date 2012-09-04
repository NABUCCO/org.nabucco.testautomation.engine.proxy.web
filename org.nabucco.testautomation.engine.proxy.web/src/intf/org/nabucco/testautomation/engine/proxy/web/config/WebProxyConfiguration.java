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

import java.io.File;

import org.nabucco.testautomation.engine.proxy.config.ProxyEngineConfiguration;

/**
 * WebProxyConfiguration
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public interface WebProxyConfiguration extends ProxyEngineConfiguration {

    /**
     * Gets the configured server port.
     * 
     * @return the server port
     */
    public int getServerPort();

    /**
     * Gets the configured hostname.
     * 
     * @return the hostname
     */
    public String getServerHost();

    /**
     * Gets the configured browser.
     * 
     * @return the browser name
     */
    public String getBrowser();

    /**
     * Gets the configured browser path.
     * 
     * @return the browser path
     */
    public String getBrowserPath();

    /**
     * Gets the configured base URL.
     * 
     * @return the base URL
     */
    public String getBaseURL();

    /**
     * Gets the configured server timeout.
     * 
     * @return the server timeout
     */
    public int getRcServerTimeout();

    /**
     * Gets the configured directory of the profile.
     * 
     * @return the profile directory
     */
    public File getProfileDirectory();

    /**
     * Gets the configured SSL Certificate trust.
     * 
     * @return the trust
     */
    public boolean getTrustAllSSLCertificates();

}
