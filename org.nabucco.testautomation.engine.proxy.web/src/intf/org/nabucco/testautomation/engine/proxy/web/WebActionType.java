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

import org.nabucco.testautomation.engine.proxy.SubEngineActionType;

/**
 * WebActionType
 * 
 * @author Frank Ratschinski, PRODYNA AG
 *
 */
public enum WebActionType implements SubEngineActionType {

    /**
     * The ActionType to enter text into an input field.
     */
    ENTER("Enter text into text input field"),
    
    /**
     * The ActionType to perform a left click with mouse.
     */
    LEFTCLICK("Left mouse click"),
    
    /**
     * The ActionType to read a text.
     */
    READ("Reading text"),
    
    /**
     * The ActionType to select an option from a dropdown box.
     */
    SELECT("Seleting option from dropdown"),
    
    /**
     * The ActionType to cleat the text of an input field.
     */
    CLEAR("Clearing text from text input field"),
    
    /**
     * The ActionType to press a key.
     */
    PRESS_KEY("Presses a certain key"),
    
    /**
     * The ActionType to close a window.
     */
    CLOSE("Closes a window"),
    
    /**
     * The ActionType to simulate the GoBack-Button of the browser.
     */
    GO_BACK("Simulates the GoBack-Button of the browser"),
    
    /**
     * The ActionType to open a window.
     */
    OPEN("Openes a window"),
    
    /**
     * The ActionType to simulate the Refresh-Button of the browser.
     */
    REFRESH("Simulates the Refresh-Button of the browser"),
    
    /**
     * The ActionType to maximize a window.
     */
    MAXIMIZE("Maximizes a window"),
    
    /**
     * The ActionType to create a cookie or screenshot.
     */
    CREATE("Creates a cookie or screenshot"),
    
    /**
     * The ActionType to delete a cookie.
     */
    DELETE("Deletes a cookie"),
    
    /**
     * The ActionType to confirm a dialog.
     */
    CONFIRM("Confirms a dialog"),
    
    /**
     * The ActionType to cancel a dialog.
     */
    CANCEL("Cancels a dialog"),
    
    /**
     * The ActionType to get a table cell.
     */
    GET_CELL("Gets a certain cell of a table"),
    
    /**
     * The ActionType to evaluate a JavaScript snippet.
     */
    EVALUATE("Evaluates a JavaScript snippet"),
    
    /**
     * The ActionType to perform a doubleclick.
     */
    DOUBLE_CLICK("Left mouse doubleclick");
    
    private String description;
    
    private WebActionType(String desc) {
        this.description = desc;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
