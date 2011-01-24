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
package org.nabucco.testautomation.engine.proxy.web.config;

import java.io.File;

import org.nabucco.testautomation.engine.proxy.config.AbstractProxyEngineConfiguration;
import org.nabucco.testautomation.engine.proxy.web.config.WebProxyConfiguration;
import org.nabucco.testautomation.engine.proxy.web.config.WebProxyConfigurationType;

import org.nabucco.testautomation.facade.datatype.engine.proxy.ProxyConfiguration;

/**
 * DatabaseProxyConfigImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public class WebProxyConfigImpl extends AbstractProxyEngineConfiguration implements
		WebProxyConfiguration {

	private static final int DEFAULT_SELENIUM_RC_TIMEOUT = 30;

	private static final int DEFAULT_SELENIUM_PORT = 4444;

	/**
     * Creates a new instance getting the configuration from
     * the given Properties.
     * 
     * @param the classloader that loaded the proxy
     * @param properties the properties containing the configuration
     */
	public WebProxyConfigImpl(ClassLoader classloader,
			ProxyConfiguration configuration) {
		super(classloader, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getBaseURL() {
		return getConfigurationValue(WebProxyConfigurationType.BASE_URL.getKey());
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getBrowser() {
		return getConfigurationValue(WebProxyConfigurationType.BROWSER.getKey());
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public String getBrowserPath() {
		return getConfigurationValue(WebProxyConfigurationType.BROWSER_PATH.getKey());
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getServerHost() {
		return getConfigurationValue(WebProxyConfigurationType.SERVER_HOST.getKey());
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public int getRcServerTimeout() {
		try {
			int timeout = Integer.parseInt(getConfigurationValue(WebProxyConfigurationType.RC_SERVER_TIMEOUT.getKey()));
			return timeout;
		} catch (Exception e) {
			System.err.println("Cannot parse Property name='"
					+ WebProxyConfigurationType.RC_SERVER_TIMEOUT + "'. using default 30 seconds");
			return DEFAULT_SELENIUM_RC_TIMEOUT;
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public int getServerPort() {
		try {
			int port = Integer.parseInt(getConfigurationValue(WebProxyConfigurationType.SERVER_PORT.getKey()));
			return port;
		} catch (Exception e) {
			System.err.println("Cannot parse Property name='" + WebProxyConfigurationType.SERVER_PORT.getKey()
					+ "'. using default 4444");
			return DEFAULT_SELENIUM_PORT;
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public File getProfileDirectory() {
		String property = getConfigurationValue(WebProxyConfigurationType.FIREFOX_PROFILE_DIR.getKey());
		
		if (property == null) {
			return null;
		}
		return new File(property);
	}

}
