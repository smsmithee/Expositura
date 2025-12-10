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

import com.expositura.model.ccd.PivlTs;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a PIVL_TS datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class PivlTsParser {
  
  public static PivlTs fromXml(final Node node) {
    final PivlTs eivlTs = new PivlTs();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        eivlTs.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // operator
      final Node operator = attributes.getNamedItem("operator");
      if (operator != null) {
        eivlTs.setOperator(operator.getNodeValue());
      }
      
      // alignment
      final Node alignment = attributes.getNamedItem("alignment");
      if (alignment != null) {
        eivlTs.setAlignment(alignment.getNodeValue());
      }
      
      // institutionSpecified
      final Node institutionSpecified = attributes.getNamedItem("institutionSpecified");
      if (institutionSpecified != null) {
        eivlTs.setInstitutionSpecified(Boolean.valueOf(institutionSpecified.getNodeValue()));
      }
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // This element may have text content (base64 string)
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|phase" -> eivlTs.setPhase(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|period" -> eivlTs.setPeriod(PqParser.fromXml(child));
        }
      } 
    }
    
    return PivlTs.isEmpty(eivlTs) ? null : eivlTs;
  }
          
}
