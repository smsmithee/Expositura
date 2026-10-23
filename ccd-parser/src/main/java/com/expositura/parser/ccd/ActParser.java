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

import com.expositura.model.ccd.Act;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Act XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ActParser {
  
  public static Act fromXml(final Node node) {
    final Act act = new Act();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        act.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        act.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        act.setMoodCode(moodCode.getNodeValue());
      }
      
      // negationInd
      final Node negationInd = attributes.getNamedItem("negationInd");
      if (negationInd != null) {
        act.setNegationInd(Boolean.valueOf(negationInd.getNodeValue()));
      }
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> act.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> act.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> act.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> act.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> act.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> act.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> act.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> act.setEffectiveTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|priorityCode" -> act.setPriorityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|languageCode" -> act.setLanguageCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> act.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> act.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> act.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> act.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> act.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> act.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> act.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> act.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> act.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> act.addPrecondition2(Precondition2Parser.fromXml(child));
          case "urn:hl7-org:sdtc|inFulfillmentOf1" -> act.addInFulfillmentOf1(InFulfillmentOf1Parser.fromXml(child));
        }
      }
    }
    
    return Act.isEmpty(act) ? null : act;
  }
          
}
