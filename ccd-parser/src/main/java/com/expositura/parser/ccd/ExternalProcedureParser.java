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

import com.expositura.model.ccd.ExternalProcedure;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the ExternalProcedure XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class ExternalProcedureParser {
  
  public static ExternalProcedure fromXml(final Node node) {
    final ExternalProcedure externalAct = new ExternalProcedure();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        externalAct.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // classCode
      final Node classCode = attributes.getNamedItem("classCode");
      if (classCode != null) {
        externalAct.setClassCode(classCode.getNodeValue());
      }
      
      // moodCode
      final Node moodCode = attributes.getNamedItem("moodCode");
      if (moodCode != null) {
        externalAct.setMoodCode(moodCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> externalAct.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> externalAct.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> externalAct.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|id" -> externalAct.addId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|code" -> externalAct.setCode(CdParser.fromXml(child));
          case "urn:hl7-org:v3|text" -> externalAct.setText(EdParser.fromXml(child));
          case "urn:hl7-org:sdtc|author" -> externalAct.addAuthor(AuthorParser.fromXml(child));
        }
      }
    }
    
    return ExternalProcedure.isEmpty(externalAct) ? null : externalAct;
  }
          
}
