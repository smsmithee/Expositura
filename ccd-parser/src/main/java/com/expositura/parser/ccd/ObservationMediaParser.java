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

import com.expositura.model.ccd.ObservationMedia;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the ObservationMedia XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ObservationMediaParser {
  
  public static ObservationMedia fromXml(final Node node) {
    final ObservationMedia observationMedia = new ObservationMedia();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        observationMedia.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // ID
      final Node id = attributes.getNamedItem("ID");
      if (id != null) {
        observationMedia.setXsId(id.getNodeValue());
      }

      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        observationMedia.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        observationMedia.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> observationMedia.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> observationMedia.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> observationMedia.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> observationMedia.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|languageCode" -> observationMedia.setLanguageCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|value" -> observationMedia.setValue(EdParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> observationMedia.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|specimen" -> observationMedia.addSpecimen(SpecimenParser.fromXml(child));
          case "urn:hl7-org:v3|performer" -> observationMedia.addPerformer(Performer2Parser.fromXml(child));
          case "urn:hl7-org:v3|author" -> observationMedia.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> observationMedia.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|partipant" -> observationMedia.addParticipant(Participant2Parser.fromXml(child));
          case "urn:hl7-org:v3|entryRelationship" -> observationMedia.addEntryRelationship(EntryRelationshipParser.fromXml(child));
          case "urn:hl7-org:v3|reference" -> observationMedia.addReference(ReferenceParser.fromXml(child));
          case "urn:hl7-org:v3|precondition" -> observationMedia.addPrecondition(PreconditionParser.fromXml(child));
          case "urn:hl7-org:sdtc|precondition" -> observationMedia.addPrecondition2(Precondition2Parser.fromXml(child));
        }
      }
    }
    
    return ObservationMedia.isEmpty(observationMedia) ? null : observationMedia;
  }
          
}
