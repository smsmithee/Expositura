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

import com.expositura.model.ccd.Pqr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a PQR datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class PqrParser {
  
  public static Pqr fromXml(final Node node) {
    final Pqr pqr = new Pqr();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        pqr.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // code
      final Node code = attributes.getNamedItem("code");
      if (code != null) {
        pqr.setCode(code.getNodeValue());
      }
      
      // codeSystem
      final Node codeSystem = attributes.getNamedItem("codeSystem");
      if (codeSystem != null) {
        pqr.setCodeSystem(codeSystem.getNodeValue());
      }
      
      // codeSystemName
      final Node codeSystemName = attributes.getNamedItem("codeSystemName");
      if (codeSystemName != null) {
        pqr.setCodeSystemName(codeSystemName.getNodeValue());
      }
      
      // codeSystemVersion
      final Node codeSystemVersion = attributes.getNamedItem("codeSystemVersion");
      if (codeSystemVersion != null) {
        pqr.setCodeSystemVersion(codeSystemVersion.getNodeValue());
      }
      
      // displayName
      final Node displayName = attributes.getNamedItem("displayName");
      if (displayName != null) {
        pqr.setDisplayName(displayName.getNodeValue());
      }
      
      // sdtcValueSet
      final Node sdtcValueSet = attributes.getNamedItemNS("urn:hl7-org:sdtc","valueSet");
      if (sdtcValueSet != null) {
        pqr.setSdtcValueSet(sdtcValueSet.getNodeValue());
      }
      
      // sdtcValueSetVersion
      final Node sdtcValueSetVersion = attributes.getNamedItemNS("urn:hl7-org:sdtc","valueSetVersion");
      if (sdtcValueSetVersion != null) {
        pqr.setSdtcValueSetVersion(sdtcValueSetVersion.getNodeValue());
      }
      
      // value
      final Node value = attributes.getNamedItem("value");
      if (value != null) {
        pqr.setValue(Double.valueOf(value.getNodeValue()));
      }
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|originalText" -> pqr.setOriginalText(EdParser.fromXml(child));
        }
      }
    }
    
    return Pqr.isEmpty(pqr) ? null : pqr;
  }
          
}
