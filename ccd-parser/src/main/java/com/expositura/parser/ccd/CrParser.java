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

import com.expositura.model.ccd.Cr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a CR datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class CrParser {
  
  public static Cr fromXml(final Node node) {
    final Cr cr = new Cr();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        cr.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // inverted
      final Node inverted = attributes.getNamedItem("inverted");
      if (inverted != null) {
        cr.setInverted(Boolean.valueOf(inverted.getNodeValue()));
      }
      
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|name" -> cr.setName(CvParser.fromXml(child));
          case "urn:hl7-org:v3|value" -> cr.setValue(CdParser.fromXml(child));
        }
      }
    }
    
    return Cr.isEmpty(cr) ? null : cr;
  }
          
}
