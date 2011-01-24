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
package org.nabucco.testautomation.engine.proxy.web.control;

/**
 * WebEngineTest
 * 
 * @author Stephan Eichmann; PRODYNA AG
 * 
 */
public class WebEngineTest {
    // private WebEngineFacadeImpl testEngine;
    //
    //
    // @Before
    // public void setUp() throws Exception {
    // testEngine = new WebEngineFacadeImpl();
    // testEngine.onInit();
    // }
    //	
    // private WebInputField getEmptyWebInputField() {
    // return new WebInputField() {
    // @Override
    // public String getCommand() {
    // return "obvious";
    // }
    // };
    // }
    //	
    // @Test(expected=IllegalArgumentException.class)
    // public void testWrongActionTypeEnumeration() {
    // WebButton button = testEngine.getWebButton();
    // List<Metadata> list = new ArrayList<Metadata>();
    // list.add(getEmptyWebInputField());
    //		
    // button.execute(new TestContext(), new ArrayList<Property>(), list, SwingActionType.CLOSE);
    // }
    //	
    // @Test
    // public void testWebInput() throws Exception {
    // WebTextInput webTextInput = testEngine.getWebTextInput();
    //    	
    // //Fill Context
    // TestContext context = new TestContext();
    // Property testScriptProperty = new StringProperty("search.input", "Prodyna AG");
    // context.put(testScriptProperty);
    //		
    // //Fill search property with a value
    // Property searchInputProperty = new StringProperty("search.input");
    // List<Property> propertyList = new ArrayList<Property>();
    // propertyList.add(searchInputProperty);
    //		
    //		
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("q");
    // metadataList.add(field);
    //		
    // webTextInput.execute(context , propertyList, metadataList, WebActionType.ENTER);
    //    	
    // webButtonClick();
    //		
    // //Verify
    // Assert.assertTrue(testEngine.getSelenium().isTextPresent("ProDyna ist als Systemhaus auf Consulting und Anwendungsentwicklung für verschiedene Branchen spezialisiert"));
    // }
    //	
    // public void webButtonClick() throws Exception {
    //	
    // WebButton webButton = testEngine.getWebButton();
    // TestContext context = new TestContext();
    // List<Property> propertyList = new ArrayList<Property>();
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("btnG");
    // metadataList.add(field);
    //		
    // webButton.execute(context , propertyList, metadataList, WebActionType.LEFTCLICK);
    // }
    //    
    //    
    // @Test
    // public void testWebButtonElementIdClick() throws Exception {
    // WebButton webButton = testEngine.getWebButton();
    // TestContext context = new TestContext();
    // List<Property> propertyList = new ArrayList<Property>();
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("bla");
    // Property elementId = new StringProperty(AbstractWebComponent.ELEMENT_ID, "btnI");
    // field.addProperty(elementId);
    // metadataList.add(field);
    //		
    // webButton.execute(context , propertyList, metadataList, WebActionType.LEFTCLICK);
    //    	
    // Assert.assertTrue(testEngine.getSelenium().isTextPresent("Sie automatisch zur ersten Webseite, die für Ihre Suchanfrage angezeigt wird."));
    // }
    //    
    // @Test
    // public void testWebLinkClick() throws Exception {
    // WebLink webLink = testEngine.getWebLink();
    // TestContext context = new TestContext();
    // List<Property> propertyList = new ArrayList<Property>();
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("Erweiterte Suche");
    // metadataList.add(field);
    //		
    // webLink.execute(context , propertyList, metadataList, WebActionType.LEFTCLICK);
    //    	
    // Assert.assertTrue(testEngine.getSelenium().isTextPresent("Erweiterte Suche"));
    // }
    //    
    // @Test(expected=SeleniumException.class)
    // public void testWrongIdentifierClick() throws Exception {
    // WebButton webButton = testEngine.getWebButton();
    // TestContext context = new TestContext();
    // List<Property> propertyList = new ArrayList<Property>();
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("bla");
    // metadataList.add(field);
    //		
    // webButton.execute(context , propertyList, metadataList, WebActionType.LEFTCLICK);
    // }
    //    
    // @Test
    // public void testWebButtonXpathClick() throws Exception {
    // WebButton webButton = testEngine.getWebButton();
    // TestContext context = new TestContext();
    // List<Property> propertyList = new ArrayList<Property>();
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("bla");
    // Property elementId = new StringProperty(AbstractWebComponent.XPATH,
    // "//input[@name='btnI']");
    // field.addProperty(elementId);
    // metadataList.add(field);
    //		
    // webButton.execute(context , propertyList, metadataList, WebActionType.LEFTCLICK);
    //    	
    // Assert.assertTrue(testEngine.getSelenium().isTextPresent("Sie automatisch zur ersten Webseite, die für Ihre Suchanfrage angezeigt wird."));
    // }
    //    
    // @Test
    // public void testOpenWebPageAndSelectDropDownBox() throws Exception {
    // //Open Sub Page
    // WebPage page = testEngine.getWebSubPage();
    // TestContext context = new TestContext();
    // List<Property> propertyList = new ArrayList<Property>();
    // List<Metadata> metadataList = new ArrayList<Metadata>();
    //		
    // WebInputField field = getEmptyWebInputField();
    //		
    // field.setId("bla");
    // Property subUrl = new StringProperty(AbstractWebComponent.SUB_URL, "advanced_search?hl=de");
    // field.addProperty(subUrl);
    // metadataList.add(field);
    //		
    // page.execute(context , propertyList, metadataList, WebActionType.ENTER);
    //		
    // Assert.assertTrue(testEngine.getSelenium().isTextPresent("Erweiterte Suche"));
    //		
    // //Change Drop Down Selection
    // WebDropDownBox box = testEngine.getWebDropDownBox();
    // metadataList.clear();
    //		
    // WebInputField dropDownField = getEmptyWebInputField();
    //		
    // dropDownField.setId("num");
    // metadataList.add(dropDownField);
    //		
    // //Selection property
    // Property selectionProperty = new StringProperty("selection");
    // propertyList.add(selectionProperty);
    //		
    // //Fill selection property
    // Property selectionValue = new StringProperty("selection", "30 Ergebnisse");
    // context.put(selectionValue);
    //		
    // box.execute(context, propertyList, metadataList, WebActionType.SELECT);
    //		
    // Assert.assertEquals("30 Ergebnisse", testEngine.getSelenium().getSelectedLabel("num"));
    //		
    // }
    //  
    // @After
    // public void tearDown() throws Exception {
    // testEngine.onDestroy();
    // }
}
