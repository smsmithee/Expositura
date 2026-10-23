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

import com.expositura.model.ccd.Observation;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Observation XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ObservationParser {
  
  public static Observation fromXml(final Node node) {
    final Observation observation = new Observation();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        observation.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        observation.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        observation.setMoodCode(moodCode.getNodeValue());
      }
      
      // negationInd
      final Node negationInd = attributes.getNamedItem("negationInd");
      if (negationInd != null) {
        observation.setNegationInd(Boolean.valueOf(negationInd.getNodeValue()));
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> observation.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> observation.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> observation.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> observation.addId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|category" -> observation.addCategory(CdParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> observation.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|derivationExpr" -> observation.setDerivationExpr(StParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> observation.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> observation.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> observation.setEffectiveTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|priorityCode" -> observation.setPriorityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|repeatNumber" -> observation.setRepeatNumber(IvlIntParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> observation.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> observation.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> observation.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> observation.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> observation.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> observation.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> observation.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> observation.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> observation.addPrecondition2(Precondition2Parser.fromXml(child));
          case "urn:hl7-org:sdtc|inFulfillmentOf1" -> observation.addInFulfillmentOf1(InFulfillmentOf1Parser.fromXml(child));
        }
      }
    }
    
    return Observation.isEmpty(observation) ? null : observation;
  }
          
}
