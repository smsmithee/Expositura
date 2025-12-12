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

import com.expositura.model.ccd.Performer1;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Performer1 XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class Performer1Parser {
  
  public static Performer1 fromXml(final Node node) {
    final Performer1 performer1 = new Performer1();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        performer1.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        performer1.setTypeCode(typeCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> performer1.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> performer1.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> performer1.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|functionCode" -> performer1.setFunctionCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|time" -> performer1.setTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|assignedEntity" -> performer1.setAssignedEntity(AssignedEntityParser.fromXml(child));
        }
      }
    }
    
    return Performer1.isEmpty(performer1) ? null : performer1;
  }
          
}
