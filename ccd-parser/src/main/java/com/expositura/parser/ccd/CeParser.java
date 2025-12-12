/*
 * Copyright 2025 Sean Smith.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expositura.parser.ccd;

import com.expositura.model.ccd.Ce;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a CE datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class CeParser {
  
  public static Ce fromXml(final Node node) {
    final Ce ce = new Ce();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        ce.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // code
      final Node code = attributes.getNamedItem("code");
      if (code != null) {
        ce.setCode(code.getNodeValue());
      }
      
      // codeSystem
      final Node codeSystem = attributes.getNamedItem("codeSystem");
      if (codeSystem != null) {
        ce.setCodeSystem(codeSystem.getNodeValue());
      }
      
      // codeSystemName
      final Node codeSystemName = attributes.getNamedItem("codeSystemName");
      if (codeSystemName != null) {
        ce.setCodeSystemName(codeSystemName.getNodeValue());
      }
      
      // codeSystemVersion
      final Node codeSystemVersion = attributes.getNamedItem("codeSystemVersion");
      if (codeSystemVersion != null) {
        ce.setCodeSystemVersion(codeSystemVersion.getNodeValue());
      }
      
      // displayName
      final Node displayName = attributes.getNamedItem("displayName");
      if (displayName != null) {
        ce.setDisplayName(displayName.getNodeValue());
      }
      
      // sdtcValueSet
      final Node valueSet = attributes.getNamedItemNS("urn:hl7-org:sdtc","valueSet");
      if (valueSet != null) {
        ce.setValueSet(valueSet.getNodeValue());
      }
      
      // sdtcValueSetVersion
      final Node valueSetVersion = attributes.getNamedItemNS("urn:hl7-org:sdtc","valueSetVersion");
      if (valueSetVersion != null) {
        ce.setValueSetVersion(valueSetVersion.getNodeValue());
      }
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|originalText" -> ce.setOriginalText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|translation" -> ce.addTranslation(CdParser.fromXml(child));
        }
      }
    }
    
    return Ce.isEmpty(ce) ? null : ce;
  }
          
}
