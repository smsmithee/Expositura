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

import com.expositura.model.ccd.EncompassingEncounter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the EncompassingEncounter XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EncompassingEncounterParser {
  
  public static EncompassingEncounter fromXml(final Node node) {
    final EncompassingEncounter encompassingEncounter = new EncompassingEncounter();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        encompassingEncounter.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        encompassingEncounter.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        encompassingEncounter.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> encompassingEncounter.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> encompassingEncounter.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> encompassingEncounter.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> encompassingEncounter.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> encompassingEncounter.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> encompassingEncounter.setEffectiveTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:sdtc|admissionReferralSourceCode" -> encompassingEncounter.setAdmissionReferralSourceCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|dischargeDispositionCode" -> encompassingEncounter.setDischargeDispositionCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|responsibleParty" -> encompassingEncounter.setResponsibleParty(ResponsiblePartyParser.fromXml(child));
          case "urn:hl7-org:v3|encounterParticipant" -> encompassingEncounter.addEncounterParticipant(EncounterParticipantParser.fromXml(child));
          case "urn:hl7-org:v3|location" -> encompassingEncounter.setLocation(LocationParser.fromXml(child));
        }
      }
    }
    
    return EncompassingEncounter.isEmpty(encompassingEncounter) ? null : encompassingEncounter;
  }
          
}
