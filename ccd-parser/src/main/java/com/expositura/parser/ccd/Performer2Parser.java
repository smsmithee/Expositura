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

import com.expositura.model.ccd.Performer2;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Performer2 XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class Performer2Parser {
  
  public static Performer2 fromXml(final Node node) {
    final Performer2 performer2 = new Performer2();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        performer2.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        performer2.setTypeCode(typeCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> performer2.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> performer2.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> performer2.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|functionCode" -> performer2.setFunctionCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|time" -> performer2.setTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|modeCode" -> performer2.setMoodCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|assignedEntityCode" -> performer2.setAssignedEntity(AssignedEntityParser.fromXml(child));
        }
      }
    }
    
    return Performer2.isEmpty(performer2) ? null : performer2;
  }
          
}
