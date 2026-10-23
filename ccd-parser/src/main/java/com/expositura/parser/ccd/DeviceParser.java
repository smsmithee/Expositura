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

import com.expositura.model.ccd.Device;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the DataEnterer XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class DeviceParser {
  
  public static Device fromXml(final Node node) {
    final Device device = new Device();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        device.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        device.setClassCode(classCode.getNodeValue());
      }
      
      // determinerCode
      final Node determinerCode = attributes.getNamedItem("determinerCode");
      if (determinerCode != null) {
        device.setDeterminerCode(determinerCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> device.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> device.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> device.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> device.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|manufacturerModelName" -> device.setManufacturerModelName(ScParser.fromXml(child));
          case "urn:hl7-org:v3|softwareName" -> device.setSoftwareName(ScParser.fromXml(child));
        }
      }
    }
    
    return Device.isEmpty(device) ? null : device;
  }
          
}
