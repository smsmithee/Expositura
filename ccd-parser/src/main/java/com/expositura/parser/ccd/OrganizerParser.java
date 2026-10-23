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

import com.expositura.model.ccd.Organizer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Organizer XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class OrganizerParser {
  
  public static Organizer fromXml(final Node node) {
    final Organizer organizer = new Organizer();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        organizer.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        organizer.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        organizer.setMoodCode(moodCode.getNodeValue());
      }
            
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> organizer.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> organizer.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> organizer.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> organizer.addId(IiParser.fromXml(child));
          case "urn:hl7-org:sdtc|category" -> organizer.addCategory(CdParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> organizer.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:sdtc|text" -> organizer.setText(EdParser.fromXml(child));
          case "urn:hl7-org:v3|statusCode" -> organizer.setStatusCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|effectiveTime" -> organizer.setEffectiveTime(IvlTsParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> organizer.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> organizer.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> organizer.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> organizer.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> organizer.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> organizer.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> organizer.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> organizer.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> organizer.addPrecondition2(Precondition2Parser.fromXml(child));
          case "urn:hl7-org:v3|component" -> organizer.addComponent(OrganizerComponentParser.fromXml(child));
        }
      }
    }
    
    return Organizer.isEmpty(organizer) ? null : organizer;
  }
          
}
