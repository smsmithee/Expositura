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

import com.expositura.model.ccd.RegionOfInterest;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the RegionOfInterest XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class RegionOfInterestParser {
  
  public static RegionOfInterest fromXml(final Node node) {
    final RegionOfInterest regionOfInterest = new RegionOfInterest();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        regionOfInterest.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // ID
      final Node xsId = attributes.getNamedItem("ID");
      if (xsId != null) {
        regionOfInterest.setXsId(xsId.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        regionOfInterest.setClassCode(classCode.getNodeValue());
      }

      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        regionOfInterest.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> regionOfInterest.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> regionOfInterest.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> regionOfInterest.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> regionOfInterest.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> regionOfInterest.setCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|value" -> regionOfInterest.addValue(IntParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> regionOfInterest.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> regionOfInterest.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> regionOfInterest.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> regionOfInterest.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> regionOfInterest.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> regionOfInterest.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> regionOfInterest.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> regionOfInterest.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> regionOfInterest.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> regionOfInterest.addPrecondition2(Precondition2Parser.fromXml(child));
        }
      }
    }
    
    return RegionOfInterest.isEmpty(regionOfInterest) ? null : regionOfInterest;
  }
          
}
