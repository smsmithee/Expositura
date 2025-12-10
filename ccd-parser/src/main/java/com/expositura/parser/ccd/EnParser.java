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

import com.expositura.model.ccd.En;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a PN datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EnParser {
  
  public static En fromXml(final Node node) {
    final En en = new En();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        en.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // use (special case, use can have multiple values with space seperator
      final Node use = attributes.getNamedItem("use");
      if (use != null) {
        final String useValue = use.getNodeValue();
        if (useValue != null) {
          for (final String singleUse : StringUtils.split(useValue)) {
            en.addUse(singleUse);
          }
        }
      }
      
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // This datatype can have text which means it's just a string for the name
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|delimiter" -> en.addDelimiter(EnxpParser.fromXml(child));
          case "urn:hl7-org:v3|family" -> en.addFamily(EnxpParser.fromXml(child));
          case "urn:hl7-org:v3|given" -> en.addGiven(EnxpParser.fromXml(child));
          case "urn:hl7-org:v3|prefix" -> en.addPrefix(EnxpParser.fromXml(child));
          case "urn:hl7-org:v3|suffix" -> en.addSuffix(EnxpParser.fromXml(child));
          case "urn:hl7-org:v3|validTime" -> en.setValidTime(IvlTsParser.fromXml(child));
        }
      } else {
        en.setXmlText(child.getNodeValue());
      }
    }
    
    return En.isEmpty(en) ? null : en;
  }
          
}
