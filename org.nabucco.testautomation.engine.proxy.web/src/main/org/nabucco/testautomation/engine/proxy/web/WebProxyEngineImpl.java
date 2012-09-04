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

import java.io.File;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.testautomation.engine.proxy.SubEngine;
import org.nabucco.testautomation.engine.proxy.base.AbstractProxyEngine;
import org.nabucco.testautomation.engine.proxy.config.ProxyEngineConfiguration;
import org.nabucco.testautomation.engine.proxy.exception.ProxyConfigurationException;
import org.nabucco.testautomation.engine.proxy.web.config.WebProxyConfigImpl;
import org.nabucco.testautomation.engine.proxy.web.config.WebProxyConfiguration;
import org.nabucco.testautomation.settings.facade.datatype.engine.SubEngineType;
import org.nabucco.testautomation.settings.facade.datatype.engine.proxy.ProxyConfiguration;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * WebProxyEngineImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public class WebProxyEngineImpl extends AbstractProxyEngine {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebProxyEngineImpl.class);

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
            boolean trustAllSSLCertificates = webProxyConfig.getTrustAllSSLCertificates();

            logger.info("Configuring with  serverPort = '" + this.serverPort + "'");
            logger.info("Configuring with  baseURL = '" + this.baseURL + "'");
            logger.info("Configuring with  browser = '" + this.browser + "'");
            logger.info("Configuring with  serverHost = '" + this.serverHost + "'");
            logger.info("Configuring with  rcServerTimeout = '" + timeout + "'");
            logger.info("Configuring with  trustAllSSLCertificates = '" + trustAllSSLCertificates + "'");

            this.remoteControlConfig = new RemoteControlConfiguration();
            this.remoteControlConfig.setTimeoutInSeconds(timeout);
            this.remoteControlConfig.setPort(this.serverPort);
            this.remoteControlConfig.setDebugMode(false);
            this.remoteControlConfig.setTrustAllSSLCertificates(trustAllSSLCertificates);

            if (profileDir != null) {
                logger.info("Configuring with  profileDir = '" + profileDir.getAbsolutePath() + "'");
                this.remoteControlConfig.setFirefoxProfileTemplate(profileDir);
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
            this.seleniumRCServer = new SeleniumServer(this.remoteControlConfig);
            this.seleniumRCServer.start();
            this.selenium = new DefaultSelenium(this.serverHost, this.serverPort, this.browser, this.baseURL);
            this.selenium.start("captureNetworkTraffic=true");
            this.selenium.open(this.baseURL);
            this.selenium.windowMaximize();
            SubEngine subEngine = null;
            subEngine = new WebSubEngineImpl(this.selenium);
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
                try {
                    this.selenium.shutDownSeleniumServer();
                } catch (Exception e1) {
                    logger.error(e, "Cannot stop Selenium");
                }
            }
        }
        if (this.seleniumRCServer != null) {
            try {
                this.seleniumRCServer.stop();
            } catch (Exception e) {
                try {
                    this.seleniumRCServer.getServer().stop(false);
                } catch (InterruptedException e1) {
                    logger.error(e, "Cannot stop Selenium RC Server");
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unconfigure() throws ProxyConfigurationException {
        this.selenium = null;
        this.seleniumRCServer = null;
        this.remoteControlConfig = null;
        this.serverHost = null;
        this.browser = null;
        this.baseURL = null;
        this.serverPort = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProxyEngineConfiguration getProxyConfiguration(ProxyConfiguration configuration) {
        WebProxyConfiguration config = new WebProxyConfigImpl(getProxySupport().getProxyClassloader(), configuration);
        return config;
    }

}
