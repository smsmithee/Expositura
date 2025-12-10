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

import com.expositura.model.ccd.IvlPq;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a IVL_PQ datatype XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class IvlPqParser {
  
  public static IvlPq fromXml(final Node node) {
    final IvlPq ivlPq = new IvlPq();
    
    // Start by getting the attributes
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        ivlPq.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // unit
      final Node unit = attributes.getNamedItem("unit");
      if (unit != null) {
        ivlPq.setUnit(unit.getNodeValue());
      }

      // value
      final Node value = attributes.getNamedItem("value");
      if (value != null) {
        ivlPq.setValue(Double.valueOf(value.getNodeValue()));
      }
      
      // operator
      final Node operator = attributes.getNamedItem("operator");
      if (operator != null) {
        ivlPq.setOperator(operator.getNodeValue());
      }
      
    }
    
    // Now work thru children
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // This element may have text content (base64 string)
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|translation" -> ivlPq.addTranslation(PqrParser.fromXml(child));
          case "urn:hl7-org:v3|low" -> ivlPq.setLow(IvxbPqParser.fromXml(child));
          case "urn:hl7-org:v3|center" -> ivlPq.setCenter(PqParser.fromXml(child));
          case "urn:hl7-org:v3|width" -> ivlPq.setWidth(PqParser.fromXml(child));
          case "urn:hl7-org:v3|high" -> ivlPq.setHigh(IvxbPqParser.fromXml(child));
        }
      } 
    }
    
    return IvlPq.isEmpty(ivlPq) ? null : ivlPq;
  }
          
}
