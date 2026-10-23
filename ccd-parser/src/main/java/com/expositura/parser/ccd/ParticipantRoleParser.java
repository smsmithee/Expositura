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

import com.expositura.model.ccd.ParticipantRole;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the ParticipantRole XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ParticipantRoleParser {
  
  public static ParticipantRole fromXml(final Node node) {
    final ParticipantRole participantRole = new ParticipantRole();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        participantRole.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        participantRole.setClassCode(classCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> participantRole.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> participantRole.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> participantRole.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> participantRole.addId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|identifiedBy" -> participantRole.addIdentifiedBy(IdentifiedByParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> participantRole.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:sdtc|specialty" -> participantRole.addSpecialty(CeParser.fromXml(child));
          case "urn:hl7-org:v3|addr" -> participantRole.addAddr(AdParser.fromXml(child));
          case "urn:hl7-org:v3|telecom" -> participantRole.addTelecom(TelParser.fromXml(child));
          case "urn:hl7-org:v3|playingDevice" -> participantRole.setPlayingDevice(DeviceParser.fromXml(child));
          case "urn:hl7-org:v3|playingEntity" -> participantRole.setPlayingEntity(PlayingEntityParser.fromXml(child));
          case "urn:hl7-org:v3|scopingEntity" -> participantRole.setScopingEntity(EntityParser.fromXml(child));
        }
      }
    }
    
    return ParticipantRole.isEmpty(participantRole) ? null : participantRole;
  }
          
}
