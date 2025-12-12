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

import com.expositura.model.ccd.Participant1;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Participant1 XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class Participant1Parser {
  
  public static Participant1 fromXml(final Node node) {
    final Participant1 participant1 = new Participant1();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        participant1.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        participant1.setTypeCode(typeCode.getNodeValue());
      }
      
      // contextControlCode
      final Node contextControlCode = attributes.getNamedItem("contextControlCode");
      if (contextControlCode != null) {
        participant1.setContextControlCode(contextControlCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> participant1.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> participant1.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> participant1.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|functionCode" -> participant1.setFunctionCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|time" -> participant1.setTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|associatedEntity" -> participant1.setAssociatedEntity(AssociatedEntityParser.fromXml(child));
        }
      }
    }
    
    return Participant1.isEmpty(participant1) ? null : participant1;
  }
          
}
