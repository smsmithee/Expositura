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

import com.expositura.model.ccd.Section;
import com.expositura.parser.ccd.util.ParserUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Section XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class SectionParser {
  
  public static Section fromXml(final Node node) {
    final Section section = new Section();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        section.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // xs:ID
      final Node xsId = attributes.getNamedItem("ID");
      if (xsId != null) {
        section.setXsId(xsId.getNodeValue());
      }

      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        section.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        section.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> section.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> section.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> section.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> section.setId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> section.setCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|title" -> section.setTitle(StParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> section.setText(ParserUtils.nodeToMap(child));
          case "urn:hl7-org:v3|confidentialityCode" -> section.setConfidentialityCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|languageCode" -> section.setLanguageCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|subject" -> section.setSubject(SubjectParser.fromXml(child));
          case "urn:hl7-org:v3|author" -> section.addAuthor(AuthorParser.fromXml(child));
          case "urn:hl7-org:v3|informant" -> section.addInformant(InformantParser.fromXml(child));
          case "urn:hl7-org:v3|entry" -> section.addEntry(EntryParser.fromXml(child));
          case "urn:hl7-org:v3|component" -> section.addSectionComponent(SectionComponentParser.fromXml(child));
        }
      }
    }
    
    return Section.isEmpty(section) ? null : section;
  }
          
}
