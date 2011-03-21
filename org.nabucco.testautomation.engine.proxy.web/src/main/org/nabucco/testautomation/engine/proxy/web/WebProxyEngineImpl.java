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

import java.io.File;

import org.nabucco.testautomation.engine.base.logging.NBCTestLogger;
import org.nabucco.testautomation.engine.base.logging.NBCTestLoggingFactory;
import org.nabucco.testautomation.engine.proxy.SubEngine;
import org.nabucco.testautomation.engine.proxy.base.AbstractProxyEngine;
import org.nabucco.testautomation.engine.proxy.config.ProxyEngineConfiguration;
import org.nabucco.testautomation.engine.proxy.exception.ProxyConfigurationException;
import org.nabucco.testautomation.engine.proxy.web.config.WebProxyConfigImpl;
import org.nabucco.testautomation.engine.proxy.web.config.WebProxyConfiguration;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import org.nabucco.testautomation.facade.datatype.engine.SubEngineType;
import org.nabucco.testautomation.facade.datatype.engine.proxy.ProxyConfiguration;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * WebProxyEngineImpl
 *
 * @author Steffen Schmidt, PRODYNA AG
 *
 */
public class WebProxyEngineImpl extends AbstractProxyEngine {

	private static final NBCTestLogger logger = NBCTestLoggingFactory
			.getInstance().getLogger(WebProxyEngineImpl.class);

	private Selenium selenium;

	private SeleniumServer seleniumRCServer;

	private RemoteControlConfiguration remoteControlConfig;

	private String serverHost, browser, baseURL;

	private int serverPort;
	
	/**
     * Constructs a new ProxyEngine with {@link SubEngineType.WEB}.
     */
	protected WebProxyEngineImpl() {
		super(SubEngineType.WEB);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void initialize() throws ProxyConfigurationException {
		// nothing todo so far
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void configure(ProxyEngineConfiguration config) throws ProxyConfigurationException {
		try {
			WebProxyConfiguration webProxyConfig = (WebProxyConfigImpl) config;

			this.serverPort = webProxyConfig.getServerPort();
			this.baseURL = webProxyConfig.getBaseURL();
			this.browser = webProxyConfig.getBrowser();
			this.serverHost = webProxyConfig.getServerHost();
			int timeout = webProxyConfig.getRcServerTimeout();
			File profileDir = webProxyConfig.getProfileDirectory();

			logger.info("Configuring with  serverPort = '" + serverPort + "'");
			logger.info("Configuring with  baseURL = '" + baseURL + "'");
			logger.info("Configuring with  browser = '" + browser + "'");
			logger.info("Configuring with  serverHost = '" + serverHost + "'");
			logger.info("Configuring with  rcServerTimeout = '" + timeout + "'");
			logger.info("Configuring with  profileDir = '" + profileDir + "'");

			remoteControlConfig = new RemoteControlConfiguration();
			remoteControlConfig.setTimeoutInSeconds(timeout);
			remoteControlConfig.setPort(serverPort);
			remoteControlConfig.setDebugMode(false);
			
			if (profileDir != null) {
				remoteControlConfig.setFirefoxProfileTemplate(profileDir);
			}
			logger.info("WebProxyEngine configured");
		} catch (Exception ex) {
			throw new ProxyConfigurationException("Could not configure WebProxyEngine", ex);
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public SubEngine start() throws ProxyConfigurationException {
		try {
			logger.info("Starting WebProxyEngine ...");
			seleniumRCServer = new SeleniumServer(remoteControlConfig);
			seleniumRCServer.start();
			selenium = new DefaultSelenium(serverHost, serverPort, browser,
					baseURL);
			selenium.start("captureNetworkTraffic=true");
			selenium.open(baseURL);
			selenium.windowMaximize();
			SubEngine subEngine = null;
			subEngine = new WebSubEngineImpl(selenium);
			logger.info("WebSubEngine created");
			return subEngine;
		} catch (Exception ex) {
			throw new ProxyConfigurationException("Could not start WebProxyEngine. Cause: " + ex.getMessage());
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void stop() throws ProxyConfigurationException {
		
		if (this.selenium != null) {
			try {
				this.selenium.stop();
			} catch (Exception e) {
				logger.error(e, "Cannot stop Selenium");
			}
		}
		if (this.seleniumRCServer != null) {
			try {
				this.seleniumRCServer.stop();
			} catch (Exception e) {
				logger.error(e, "Cannot stop Selenium RC Server");
			}
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void unconfigure() throws ProxyConfigurationException {
		selenium = null;
		seleniumRCServer = null;
		remoteControlConfig = null;
		serverHost = null; 
		browser = null; 
		baseURL = null;
		serverPort = 0;
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	protected ProxyEngineConfiguration getProxyConfiguration(
			ProxyConfiguration configuration) {
		WebProxyConfiguration config = new WebProxyConfigImpl(
				getProxySupport().getProxyClassloader(), configuration);
		return config;
	}

}
