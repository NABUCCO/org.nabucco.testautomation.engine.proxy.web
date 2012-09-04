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
package org.nabucco.testautomation.engine.proxy.web.exception;

import org.nabucco.testautomation.engine.proxy.exception.SubEngineException;

/**
 * WebComponentException
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class WebComponentException extends SubEngineException {

    private static final long serialVersionUID = 1L;

    public WebComponentException() {
        super();
    }

    public WebComponentException(Exception cause) {
        super(cause);
    }

    public WebComponentException(String message, Exception cause) {
        super(message, cause);
    }

    public WebComponentException(String message) {
        super(message);
    }

}
