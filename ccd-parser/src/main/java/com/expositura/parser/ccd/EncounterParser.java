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

import com.expositura.model.ccd.Encounter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Encounter XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class EncounterParser {
  
  public static Encounter fromXml(final Node node) {
    final Encounter encounter = new Encounter();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        encounter.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        encounter.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        encounter.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> encounter.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> encounter.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> encounter.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> encounter.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> encounter.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> encounter.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> encounter.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> encounter.setEffectiveTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:sdtc|dischargeDispositionCode" -> encounter.setDischargeDispositionCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|priorityCode" -> encounter.setPriorityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> encounter.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> encounter.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> encounter.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> encounter.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> encounter.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> encounter.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> encounter.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> encounter.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> encounter.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> encounter.addPrecondition2(Precondition2Parser.fromXml(child));
          case "urn:hl7-org:sdtc|inFulfillmentOf1" -> encounter.addInFulfillmentOf1(InFulfillmentOf1Parser.fromXml(child));
        }
      }
    }
    
    return Encounter.isEmpty(encounter) ? null : encounter;
  }
          
}
