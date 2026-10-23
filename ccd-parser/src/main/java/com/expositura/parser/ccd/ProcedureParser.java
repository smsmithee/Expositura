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

import com.expositura.model.ccd.Procedure;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Procedure XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ProcedureParser {
  
  public static Procedure fromXml(final Node node) {
    final Procedure procedure = new Procedure();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        procedure.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        procedure.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        procedure.setMoodCode(moodCode.getNodeValue());
      }
      
      // negationInd
      final Node negationInd = attributes.getNamedItem("negationInd");
      if (negationInd != null) {
        procedure.setNegationInd(Boolean.valueOf(negationInd.getNodeValue()));
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> procedure.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> procedure.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> procedure.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> procedure.addId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|category" -> procedure.addCategory(CdParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> procedure.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> procedure.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> procedure.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> procedure.setEffectiveTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|priorityCode" -> procedure.setPriorityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|languageCode" -> procedure.setLanguageCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|methodCode" -> procedure.addMethodCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|approachSiteCode" -> procedure.addApproachSiteCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|targetSiteCode" -> procedure.addTargetSiteCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> procedure.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> procedure.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> procedure.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> procedure.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> procedure.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> procedure.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> procedure.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> procedure.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> procedure.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> procedure.addPrecondition2(Precondition2Parser.fromXml(child));
          case "urn:hl7-org:sdtc|inFulfillmentOf1" -> procedure.addInFulfillmentOf1(InFulfillmentOf1Parser.fromXml(child));
        }
      }
    }
    
    return Procedure.isEmpty(procedure) ? null : procedure;
  }
          
}
