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

import com.expositura.model.ccd.Order;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Order XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class OrderParser {
  
  public static Order fromXml(final Node node) {
    final Order order = new Order();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        order.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        order.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        order.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> order.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> order.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> order.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> order.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> order.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|priorityCode" -> order.setPriorityCode(CeParser.fromXml(child));
        }
      }
    }
    
    return Order.isEmpty(order) ? null : order;
  }
          
}
