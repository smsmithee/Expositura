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

import com.expositura.model.ccd.Author;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses the Author XML element to the java object or from the object to the xml
 * 
 * @author Sean Smith
 */
public class AuthorParser {
  
  public static Author fromXml(final Node node) {
    final Author author = new Author();
    
    // First get the attributes if any
    final NamedNodeMap attributes = node.getAttributes();
    if (attributes != null && attributes.getLength() > 0) {
      
      // nullFlavor
      final Node nullFlavor = attributes.getNamedItem("nullFlavor");
      if (nullFlavor != null) {
        author.setNullFlavor(nullFlavor.getNodeValue());
      }
      
      // typeCode
      final Node typeCode = attributes.getNamedItem("typeCode");
      if (typeCode != null) {
        author.setTypeCode(typeCode.getNodeValue());
      }
      
      // contextControlCode
      final Node contextControlCode = attributes.getNamedItem("contextControlCode");
      if (contextControlCode != null) {
        author.setContextControlCode(contextControlCode.getNodeValue());
      }
      
    }
    
    final NodeList nodeList = node.getChildNodes();
    
    for (int i = 0; i < nodeList.getLength(); i++) {
      final Node child = nodeList.item(i);
      
      // Ignore children with no attributes and no children, they are text such as newlines for formatted XML
      if (child.hasAttributes() || child.hasChildNodes()) {
        switch (child.getNamespaceURI() + "|" + child.getLocalName()) {
          case "urn:hl7-org:v3|realmCode" -> author.addRealmCode(CsParser.fromXml(child));
          case "urn:hl7-org:v3|typeId" -> author.setTypeId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|templateId" -> author.addTemplateId(IiParser.fromXml(child));
          case "urn:hl7-org:v3|functionCode" -> author.setFunctionCode(CeParser.fromXml(child));
          case "urn:hl7-org:v3|time" -> author.setTime(TsParser.fromXml(child));
          case "urn:hl7-org:v3|assignedAuthor" -> author.setAssignedAuthor(AssignedAuthorParser.fromXml(child));
        }
      }
    }
    
    return Author.isEmpty(author) ? null : author;
  }
          
}
